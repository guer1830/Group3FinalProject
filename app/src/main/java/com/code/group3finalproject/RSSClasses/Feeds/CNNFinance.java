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

    /**
     *
     * @return
     * Returns this feeds RSSFeedURL as a String
     */
    public String getRSSFeedURL(){
        return this.RSSFeedURL;
    }

    /**
     *
     * @return
     * Returns this feeds name as a String
     */
    public String getRSSFeedName(){
        return this.name;
    }

    /**
     *
     * @return
     * Returns this feeds includeFeed as a String
     */
    public Boolean getIncludeFeed() {return this.includeFeed;}

    /**
     *
     * @return
     * Returns this feeds item tag as a String
     */
    public String getItemTag() {
        return "item";
    }

    /**
     *
     * @return
     * Returns this feeds description tag as a String
     */
    public String getDescriptionTag() {
        return "description";
    }

    /**
     *
     * @return
     * Returns this feeds link tag as a String
     */
    public String getHyperLinkTag() {
        return "link";
    }

    /**
     *
     * @return
     * Returns this feeds pubDate tag as a String
     */
    public String getPublicationDateTag() {
        return "pubDate";
    }

    /**
     *
     * @return
     * Returns this feeds image tag as a String
     */
    public String getImageLinkTag() {
        return "";
    }

    /**
     *
     * @return
     * Returns this feeds title tag as a String
     */
    public String getTitleTag() {
        return "title";
    }

}
