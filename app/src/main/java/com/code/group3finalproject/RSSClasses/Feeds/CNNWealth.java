package com.code.group3finalproject.RSSClasses.Feeds;

import android.os.Parcel;

import com.code.group3finalproject.RSSClasses.RSSNewsFeed;

public class CNNWealth extends RSSNewsFeed {
    // "https://www.cnbc.com/id/10001054/device/rss/rss.html"

    private String RSSFeedURL;
    private String includeFeed;

    public CNNWealth(){
        super();
        this.RSSFeedURL = "https://www.cnbc.com/id/10001054/device/rss/rss.html";
    }

    protected CNNWealth(Parcel in) {
        super(in);
    }

    public String getRSSFeedURL(){
        return this.RSSFeedURL;
    }

}
