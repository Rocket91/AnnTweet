package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.util.List;


public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.viewHolder> {

    private Context context;
    private List<Tweet> tweets;

    // pass in context the list of tweet

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    // for each row, inflate the layout.
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new viewHolder(view);
    }

    // bind values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, int position) {
        final Tweet tweet = tweets.get(position);
        holder.tvBody.setText(tweet.body);
        holder.tvName.setText(tweet.user.name);
        holder.tvScreenName.setText("@" + tweet.user.screenName);
        GlideApp.with(context).load(tweet.user.profileImageURL)
                .transform(new CircleCrop())
                .into(holder.ivProfileImage);

        holder.tvTime.setText(tweet.getFormattedTimestamp);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("tweet", Parcels.wrap(tweet));
                context.startActivity(i);

                holder.ivProfileImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context, ProfileActivity.class);
                        i.putExtra("tweet", Parcels.wrap(tweet));
                        context.startActivity(i);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    // clean all the items with the recycler view
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // add a list of item
    public void addTweets(List<Tweet> tweetList) {
        tweets.addAll(tweetList);
        notifyDataSetChanged();
    }

    // define the view holder
    public class viewHolder extends RecyclerView.ViewHolder {

        public ImageView ivProfileImage;
        public TextView tvName;
        public TextView tvBody;
        public TextView tvScreenName;
        public TextView tvTime;
        RelativeLayout container;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvName = itemView.findViewById(R.id.tvName);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvTime = itemView.findViewById(R.id.tvTime);
            container = itemView.findViewById(R.id.container);
        }
    }


}
