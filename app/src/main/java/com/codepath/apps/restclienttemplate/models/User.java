package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class User {

    public User() {
    }

    public String name;
    public  long uid;
    public String screenName;
    public String profileImageURL;
    public String FollowingCount;
    public String FollowersCount;
    public String Descriptions;
    public String backgroundImage;

    public static User fromJson(JSONObject jsonObject) throws JSONException {
        User user = new User();
        user.name = jsonObject.getString("name");
        user.uid = jsonObject.getLong("id");
        user.screenName = jsonObject.getString("screen_name");
        user.profileImageURL = jsonObject.getString("profile_image_url");
        user.FollowingCount = jsonObject.getString("friends_count");
        user.FollowersCount = jsonObject.getString("followers_count");
        user.Descriptions = jsonObject.getString("description");
        user.backgroundImage = jsonObject.getString("profile_banner_url");

        return user;
    }
}
