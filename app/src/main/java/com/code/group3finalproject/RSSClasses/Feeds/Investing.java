

package com.code.group3finalproject.RSSClasses.Feeds;

import com.code.group3finalproject.RSSClasses.RSSNewsFeed;

import java.io.Serializable;

public class Investing extends RSSNewsFeed implements Serializable {

    private String RSSFeedURL;
    private Boolean includeFeed;
    private String name;

    public Investing(){
        super();
        this.RSSFeedURL = "https://www.investing.com/rss/news.rss";
        this.name = "Investing";
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