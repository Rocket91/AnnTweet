package com.codepath.apps.restclienttemplate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class ProfileActivity extends AppCompatActivity {
    public ImageView ivProfileBackgroundImage;
    public ImageView ivProfileImage3;
    public Button btnFollow;
    public TextView tvName3;
    public TextView tvScreenname3;
    public TextView tvDescription;
    public TextView tvFollowingCount;
    public TextView tvFollowersCount;

    Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Profile");

        tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        ivProfileBackgroundImage = findViewById(R.id.ivProfileBackgroundImage);
        ivProfileImage3 = findViewById(R.id.ivProfileImage3);
        btnFollow = findViewById(R.id.btnFollow);
        tvName3 = findViewById(R.id.tvName3);
        tvScreenname3 = findViewById(R.id.tvScreenName3);
        tvDescription = findViewById(R.id.tvDescription);
        tvFollowingCount = findViewById(R.id.tvFollowingCount);
        tvFollowersCount = findViewById(R.id.tvFollowersCount);


        GlideApp.with(this).load(tweet.user.profileImageURL)
                .transform(new CircleCrop())
                .into(ivProfileImage3);

        tvName3.setText(tweet.user.name);
        tvScreenname3.setText("@"+tweet.user.screenName);
        tvDescription.setText(tweet.user.Descriptions);
        tvFollowingCount.setText(tweet.user.FollowingCount + " Following");
        tvFollowersCount.setText(tweet.user.FollowersCount +" Followers");

        GlideApp.with(this).load(tweet.user.backgroundImage)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(ivProfileBackgroundImage);


    }
}
