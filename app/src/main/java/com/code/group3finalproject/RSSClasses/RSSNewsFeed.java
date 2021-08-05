package com.code.group3finalproject.RSSClasses;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class RSSNewsFeed implements Serializable {

    private String RSSFeedURL;
    private Boolean includeFeed;
    private String name;


    public RSSNewsFeed() {
        this.RSSFeedURL = "";
        this.includeFeed = Boolean.TRUE;
        this.name = "";
    }

    public String getRSSFeedURL(){
        return this.RSSFeedURL;
    }

    public Boolean isIncluded(){
        return this.includeFeed;
    }

    public String getRSSFeedName(){
        return this.name;
    }

    public Boolean getIncludeFeed() {return this.includeFeed;}

    public void toggleInclusion(){
        if (this.includeFeed){
            this.includeFeed = Boolean.FALSE;
        }
        else{
            this.includeFeed = Boolean.TRUE;
        }
    }

    public String getItemTag() {
        return "";
    }

    public String getDescriptionTag() {
        return "";
    }

    public String getHyperLinkTag() {
        return "";
    }

    public String getPublicationDateTag() {
        return "";
    }

    public String getImageLinkTag() {
        return "";
    }

    public String getTitleTag() {
        return "";
    }
}
