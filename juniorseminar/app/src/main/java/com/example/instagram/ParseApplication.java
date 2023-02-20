package com.example.instagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;


public class ParseApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        //register your parse models
        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(Comment.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("2LZiNp0BqCP4tT5yd3TH1PQcNflLrkJvay28B6fj")
                .clientKey("KOME5dMVcqLN95iY2Pe0VAzxdvVCv1ZIFgj1CwsC")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
