package com.codepath.apps.restclienttemplate.models;

import com.codepath.apps.restclienttemplate.TimeFormatter;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Tweet {

    public Tweet(){

    }

    public String body;
    public long vid;
    public String createdAt;
    public User user;
    public String getFormattedTimestamp;
    public String retweetedCount;
    public  String likesCount;

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.vid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.getFormattedTimestamp = TimeFormatter.getTimeDifference(tweet.createdAt);
        tweet.retweetedCount = jsonObject.getString("retweet_count");
        tweet.likesCount = jsonObject.getString("favorite_count");

        return tweet;
    }


}
