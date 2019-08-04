package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {

    TextView tvName2;
    ImageView ivProfileImage2;
    TextView tvScreenName2;
    TextView tvTime2;
    TextView tvCreatedAt2;
    TextView tvRetweetedCount;
    TextView tvLikesCount;
    TextView tvBody2;
    ImageView ivEmbeddedImage;

    Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Detailed View");

        tvName2 = findViewById(R.id.tvName2);
        ivProfileImage2 = findViewById(R.id.ivProfileImage2);
        tvScreenName2 = findViewById(R.id.tvScreenName2);
        tvTime2 = findViewById(R.id.tvTime2);
        tvCreatedAt2 = findViewById(R.id.tvCreadteAt2);
        tvRetweetedCount = findViewById(R.id.tvRetweetedCount);
        tvLikesCount = findViewById(R.id.tvLikesCount);
        tvBody2 = findViewById(R.id.tvBody2);
        ivEmbeddedImage = findViewById(R.id.ivEmbeddedImage);


        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        tvName2.setText(tweet.user.name);
        tvScreenName2.setText("@" +tweet.user.screenName);
        tvTime2.setText(tweet.getFormattedTimestamp);
        tvCreatedAt2.setText(tweet.createdAt);
        tvBody2.setText(tweet.body);
        GlideApp.with(this).load(tweet.user.profileImageURL)
                .transform(new CircleCrop())
                .into(ivProfileImage2);



        tvRetweetedCount.setText(tweet.retweetedCount+ " Retweets");
        tvLikesCount.setText(tweet.likesCount+ " Likes");

        ivProfileImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailActivity.this, ProfileActivity.class);
                i.putExtra("tweet", Parcels.wrap(tweet));
                startActivity(i);
            }
        });
    }

}
