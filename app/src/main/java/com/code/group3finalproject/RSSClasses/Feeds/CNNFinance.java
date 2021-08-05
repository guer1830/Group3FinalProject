package com.code.group3finalproject.RSSClasses.Feeds;

import android.os.Parcel;

import com.code.group3finalproject.RSSClasses.RSSNewsFeed;

import java.io.Serializable;

public class CNNFinance extends RSSNewsFeed implements Serializable {
    private String RSSFeedURL;
    private Boolean includeFeed;
    private String name;

    public CNNFinance(){
        super();
        this.RSSFeedURL = "https://www.cnbc.com/id/10000664/device/rss/rss.html";
        this.name = "CNN Finance";
        this.includeFeed = Boolean.TRUE;
    }

    public String getRSSFeedURL(){
        return this.RSSFeedURL;
    }

    public String getRSSFeedName(){
        return this.name;
    }

    public Boolean getIncludeFeed() {return this.includeFeed;}

    public String getItemTag() {
        return "item";
    }

    public String getDescriptionTag() {
        return "description";
    }

    public String getHyperLinkTag() {
        return "link";
    }

    public String getPublicationDateTag() {
        return "pubDate";
    }

    public String getImageLinkTag() {
        return "";
    }

    public String getTitleTag() {
        return "title";
    }

}
