package com.code.group3finalproject.RSSClasses.Feeds;

import android.os.Parcel;

import com.code.group3finalproject.RSSClasses.RSSNewsFeed;

public class CNNBusiness extends RSSNewsFeed {

    private String RSSFeedURL;
    private String includeFeed;

    public CNNBusiness(){
        super();
        this.RSSFeedURL = "https://www.cnbc.com/id/10001147/device/rss/rss.html";
    }

    protected CNNBusiness(Parcel in) {
        super(in);
    }

    public String getRSSFeedURL(){
        return this.RSSFeedURL;
    }

}
