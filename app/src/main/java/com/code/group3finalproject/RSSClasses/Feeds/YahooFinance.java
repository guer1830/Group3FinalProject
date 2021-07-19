
package com.code.group3finalproject.RSSClasses.Feeds;

import android.os.Parcel;

import com.code.group3finalproject.RSSClasses.RSSNewsFeed;

import java.io.Serializable;

public class YahooFinance extends RSSNewsFeed implements Serializable {

    private String RSSFeedURL;
    private Boolean includeFeed;
    private String name;

    public YahooFinance(){
        super();
        this.RSSFeedURL = "https://finance.yahoo.com/news/rssindex";
        this.name = "Yahoo Finance";
        this.includeFeed = Boolean.TRUE;
    }


    public String getRSSFeedURL(){
        return this.RSSFeedURL;
    }

    public String getRSSFeedName(){
        return this.name;
    }

    public Boolean getIncludeFeed() {return this.includeFeed;}

}
