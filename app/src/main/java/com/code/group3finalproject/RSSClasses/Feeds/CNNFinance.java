package com.code.group3finalproject.RSSClasses.Feeds;

import android.os.Parcel;

import com.code.group3finalproject.RSSClasses.RSSNewsFeed;

public class CNNFinance extends RSSNewsFeed {
    // "https://www.cnbc.com/id/10000664/device/rss/rss.html"
    private String RSSFeedURL;
    private String includeFeed;

    public CNNFinance(){
        super();
        this.RSSFeedURL = "https://www.cnbc.com/id/10000664/device/rss/rss.html";
    }

    protected CNNFinance(Parcel in) {
        super(in);
    }

    public String getRSSFeedURL(){
        return this.RSSFeedURL;
    }

}
