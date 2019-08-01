package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 20;
    private TwitterClient client;
    private  RecyclerView rvTweets;
    private TweetsAdapter adapter;
    private List<Tweet> tweets;
    private EndlessRecyclerViewScrollListener scrollListener;
    private Tweet tweet;
    private FloatingActionButton actionButton;

    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Home");
        String title = actionBar.getTitle().toString();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.twitt_logo_2);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        actionButton =  findViewById(R.id.btnFAB);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
                i.putExtra("tweet", Parcels.wrap(tweet));
                startActivity(i);
            }
        });


        client = TwitterApp.getRestClient(this);

        swipeContainer = findViewById(R.id.swipeContainer);
        // Configure the refreshing colors

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,

                android.R.color.holo_green_light,

                android.R.color.holo_orange_light,

                android.R.color.holo_red_light);

        //find the recycler view
        rvTweets = findViewById(R.id.rvTweets);
        //initialize a list of tweets and the adapter from the data source
        tweets = new ArrayList<>();
        adapter = new TweetsAdapter(this, tweets);
        // recycler view setup: layout manager and setting the adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);;
        rvTweets.setLayoutManager(linearLayoutManager);
        rvTweets.setAdapter(adapter);



        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {

            @Override

            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                client.getNextPageOfTweets(new JsonHttpResponseHandler(){

                    @Override

                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                        List<Tweet>tweetsToAdd = new ArrayList<>();

                        for (int i=0; i < response.length(); i++){

                            try {

                                JSONObject jsonTweetObject = response.getJSONObject(i);

                                tweet = Tweet.fromJson(jsonTweetObject);

                                tweetsToAdd.add(tweet);

                            } catch (JSONException e) {

                                e.printStackTrace();

                            }

                        }

                        adapter.clear();
                        adapter.addTweets(tweetsToAdd);
                        swipeContainer.setRefreshing(false);

                    }



                    @Override

                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {

                    }



                    @Override

                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                    }

                },tweet.uid);
            }

        };

        rvTweets.addOnScrollListener(scrollListener);
        rvTweets.setAdapter(adapter);
        popularHomeTimeline();
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("TwitterClient", "content has been refreshed");
                popularHomeTimeline();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.compose){
            //tapped on compose icon
            //navigate to a new activity
            Intent i = new Intent(this, ComposeActivity.class);
            i.putExtra("tweet", Parcels.wrap(tweet));
            startActivityForResult(i, REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // REQUEST_CODE is defined above
        if (requestCode == REQUEST_CODE  && resultCode == RESULT_OK) {
            // Pull info out of the data intent(tweet object)
            Tweet tweet = Parcels.unwrap(data.getParcelableExtra("tweet"));
            //Update the recycler view with this tweet
            tweets.add(0, tweet);
            adapter.notifyItemInserted(0);
            rvTweets.smoothScrollToPosition(0);
        }
    }

    private void popularHomeTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
              //  Log.d("Twitter", response.toString());
                // iterate through the list of tweets
                List<Tweet> tweetsToAdd = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonTweetObject = response.getJSONObject(i);
                        // convert each JsonObject into a tweet object
                        Tweet tweet = Tweet.fromJson(jsonTweetObject);
                        // add the tweet into a data source
                        tweetsToAdd.add(tweet);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                // clear the existing data
                adapter.clear();
                //shw the data we just received
                adapter.addTweets(tweetsToAdd);
                //now we call the setRefreshing (false) to signal refresh has finished
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("Twitter", responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Twitter", errorResponse.toString());
            }
        });
    }
}
