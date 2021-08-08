package com.code.group3finalproject.RSSClasses;


import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

/**
 * //This is my base RSS object class
 * //The idea is that each feed will have a unique XML structure and this class will
 * //Allow the XML Pull Parser to parse different tags that are specific to the indivdual
 * //RSS feeds.
 */

public class RSSNewsObject implements Parcelable {

    private String newsTitle;
    private String newsDescription;
    private String articleURL;
    private String imageURL;
    private String publicationDate;

    public RSSNewsObject(){
        newsDescription = "";
        newsTitle = "";
        articleURL = "";
        imageURL = "";
        publicationDate = "";
    }

    protected RSSNewsObject(Parcel in) {
        newsTitle = in.readString();
        newsDescription = in.readString();
        articleURL = in.readString();
        imageURL = in.readString();
        publicationDate = in.readString();
    }

    public static final Creator<RSSNewsObject> CREATOR = new Creator<RSSNewsObject>() {
        @Override
        public RSSNewsObject createFromParcel(Parcel in) {
            return new RSSNewsObject(in);
        }

        @Override
        public RSSNewsObject[] newArray(int size) {
            return new RSSNewsObject[size];
        }
    };

    /**
     *
     * @param input
     * Sets the newsTitle variables to the inputted String
     */
    public void setNewsTitle(String input){
        newsTitle = input;
    }
    /**
     *
     * @param input
     * Sets the newsDescription variables to the inputted String
     */
    public void setNewsDescription(String input){
        newsDescription = input;
    }
    /**
     *
     * @param input
     * Sets the articleURL variables to the inputted String
     */
    public void setArticleURL(String input){
        articleURL = input;
    }
    /**
     *
     * @param input
     * Sets the imageURL variables to the inputted String
     */
    public void setImageURL(String input){
        imageURL = input;
    }
    /**
     *
     * @param input
     * Sets the publicationDate variables to the inputted String
     */
    public void setPublicationDate(String input){
        publicationDate = input;
    }

    //Get the variables

    /**
     *
     * @return
     * Returns the newsTitle String
     */
    public String getNewsTitle(){
        return newsTitle;
    }
    /**
     *
     * @return
     * Returns the newsDescription String
     */
    public String getNewsDescription(){
        return newsDescription;
    }
    /**
     *
     * @return
     * Returns the articleURL String
     */
    public String getArticleURL(){
        return articleURL;
    }
    /**
     *
     * @return
     * Returns the imageURL String
     */
    public String getImageURL(){
        return imageURL;
    }
    /**
     *
     * @return
     * Returns the publicationDate String
     */
    public String getPublicationDate(){
        return publicationDate;
    }

    //These functions are used to check if the tag should be checked for this feed

    /**
     *
     * @return
     * Returns a boolean inddicating if this field is required
     */
    public Boolean isTitleRequired(){
        return true;
    }
    /**
     *
     * @return
     * Returns a boolean inddicating if this field is required
     */
    public Boolean isDescriptionRequired(){
        return true;
    }
    /**
     *
     * @return
     * Returns a boolean inddicating if this field is required
     */
    public Boolean isImageRequired(){
        return false;
    }
    /**
     *
     * @return
     * Returns a boolean inddicating if this field is required
     */
    public Boolean isArticleLinkRequired(){
        return true;
    }
    /**
     *
     * @return
     * Returns a boolean inddicating if this field is required
     */
    public Boolean isDateRequired(){
        return true;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(newsTitle);
        dest.writeString(newsDescription);
        dest.writeString(articleURL);
        dest.writeString(imageURL);
        dest.writeString(publicationDate);
    }
}
