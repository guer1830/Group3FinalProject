package com.code.group3finalproject.RSSClasses;


import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

//This is my base RSS object class
//The idea is that each feed will have a unique XML structure and this class will
//Allow the XML Pull Parser to parse different tags that are specific to the indivdual
//RSS feeds.
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

    //Set the variables
    public void setNewsTitle(String input){
        newsTitle = input;
    }

    public void setNewsDescription(String input){
        newsDescription = input;
    }

    public void setArticleURL(String input){
        articleURL = input;
    }

    public void setImageURL(String input){
        imageURL = input;
    }

    public void setPublicationDate(String input){
        publicationDate = input;
    }

    //Get the variables
    public String getNewsTitle(){
        return newsTitle;
    }

    public String getNewsDescription(){
        return newsDescription;
    }

    public String getArticleURL(){
        return articleURL;
    }

    public String getImageURL(){
        return imageURL;
    }

    public String getPublicationDate(){
        return publicationDate;
    }

    //These functions are used to check if the tag should be checked for this feed
    public Boolean isTitleRequired(){
        return true;
    }

    public Boolean isDescriptionRequired(){
        return true;
    }

    public Boolean isImageRequired(){
        return false;
    }

    public Boolean isArticleLinkRequired(){
        return true;
    }

    public Boolean isDateRequired(){
        return true;
    }

    //These functions return the assosciated tag string
    public String getTitleTag(){
        return "title";
    }

    public String getDescriptionTag(){
        return "description";
    }

    public String getArticleURLTag(){
        return "link";
    }

    public String getImageURLTag(){
        return "image";
    }

    public String getDateTag(){
        return "pubDate";
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
