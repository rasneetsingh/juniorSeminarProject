package com.example.instagram;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@ParseClassName("Post")



public class Post extends ParseObject{

    public static final String KEY_DESCRIPTION ="description";
    public static final String KEY_IMAGE ="image";
    public static final String KEY_USER = "user";
    public static final String KEY_LIKED_BY ="likedby";

    public Post(){}

    public String getDescription(){
        return getString(KEY_DESCRIPTION);

    }

    public void setDescription(String description){
        put(KEY_DESCRIPTION, description);

    }

    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);

    }

    public List<String> getlikedBy(){
        List<String> likedby = getList(KEY_LIKED_BY);
        if(likedby == null){
            likedby = new ArrayList<>();
        }
        return likedby;
    }


    public String likedCountDisplayText(){
        String likesText = String.valueOf(getlikedBy().size());
        likesText += getlikedBy().size() ==1 ? "like" : "likes";
         return likesText;
    }


    public void setImage(ParseFile parseFile){
        put(KEY_IMAGE, parseFile);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);

    }
    private void testToggle(){

    }

    public void setUser(ParseUser user){
        put(KEY_USER, user);
    }


    public void setlikedBy(List<String> likeBy) {
        put(KEY_LIKED_BY, getlikedBy());


    }

    public static String calculateTimeAgo(Date createdAt) {

        int SECOND_MILLIS = 1000;
        int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        int DAY_MILLIS = 24 * HOUR_MILLIS;

        try {
            createdAt.getTime();
            long time = createdAt.getTime();
            long now = System.currentTimeMillis();

            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "just now";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "a minute ago";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + " m";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "an hour ago";
            } else if (diff < 24 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + " h";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday";
            } else {
                return diff / DAY_MILLIS + " d";
            }
        } catch (Exception e) {
            Log.i("Error:", "getRelativeTimeAgo failed", e);
            e.printStackTrace();
        }
        return "";
    }
}
