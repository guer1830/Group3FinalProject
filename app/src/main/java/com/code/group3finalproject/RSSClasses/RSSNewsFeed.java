package com.code.group3finalproject.RSSClasses;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class RSSNewsFeed implements Serializable {

    private String RSSFeedURL;
    private Boolean includeFeed;
    private String name;


    /**
     * Constructor
     */
    public RSSNewsFeed() {
        this.RSSFeedURL = "";
        this.includeFeed = Boolean.TRUE;
        this.name = "";
    }

    /**
     *
     * @return
     * return this objects RRSFeedURL as a String
     */
    public String getRSSFeedURL(){
        return this.RSSFeedURL;
    }

    /**
     *
     * @return
     * return this objects inlusion flag as a boolean
     */
    public Boolean isIncluded(){
        return this.includeFeed;
    }

    /**
     *
     * @return
     * return this objects name as a String
     */
    public String getRSSFeedName(){
        return this.name;
    }

    /**
     *
     * @return
     * return this objects inclusion flag as a boolean
     */
    public Boolean getIncludeFeed() {return this.includeFeed;}

    /**
     * This function toggles the inclusion flag
     */
    public void toggleInclusion(){
        if (this.includeFeed){
            this.includeFeed = Boolean.FALSE;
        }
        else{
            this.includeFeed = Boolean.TRUE;
        }
    }

    /**
     *
     * @return
     * Returns a String that represents the item tag
     */
    public String getItemTag() {
        return "";
    }

    /**
     *
     * @return
     * Returns a String that represents the description tag
     */
    public String getDescriptionTag() {
        return "";
    }

    /**
     *
     * @return
     * Returns a String that represents the hyper link tag
     */
    public String getHyperLinkTag() {
        return "";
    }

    /**
     *
     * @return
     * Returns a String that represents the publication date tag
     */
    public String getPublicationDateTag() {
        return "";
    }

    /**
     *
     * @return
     * Returns a String that represents the image link tag
     */
    public String getImageLinkTag() {
        return "";
    }

    /**
     *
     * @return
     * Returns a String that represents the title tag
     */
    public String getTitleTag() {
        return "";
    }
}
