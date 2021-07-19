package com.code.group3finalproject;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import com.code.group3finalproject.RSSClasses.RSSManagedClasses;
import com.code.group3finalproject.RSSClasses.RSSNewsFeed;
import com.code.group3finalproject.RSSClasses.RSSNewsObject;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class fetchRSSFeeds extends AsyncTask<Void, Void, Boolean> {
    private ArrayList<RSSNewsObject> recyclerObjects;
    private RSSFeedRecyclerViewAdapter recyclerAdapter;
    private RSSManagedClasses manager;

    public fetchRSSFeeds(RSSFeedRecyclerViewAdapter adapter, RSSManagedClasses manager){
        this.recyclerAdapter = adapter;
        this.manager = manager;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        this.recyclerObjects = new ArrayList<>();
        try{
            //Get the feeds that are selected
            ArrayList<RSSNewsFeed> selectedFeeds = manager.getSelectedFeeds();

            for(RSSNewsFeed feed: selectedFeeds){
                String urlString = feed.getRSSFeedURL();
                URL url = new URL(urlString);
                InputStream inputStream = url.openConnection().getInputStream();
                this.recyclerObjects.addAll(parseXMLFeed(inputStream, feed));
                return true;
            }
        }
        catch (IOException | XmlPullParserException e){
            Log.e("Error","Error",e);
        }

        //Sort the feed based on the publication date

        return false;
    }


    @Override
    protected void onPostExecute(Boolean success) {
        this.recyclerAdapter.setData(this.recyclerObjects);
    }

    public ArrayList<RSSNewsObject> parseXMLFeed(InputStream inputStream, RSSNewsFeed RSSFeed) throws XmlPullParserException, IOException {
        String title = null;
        String description = null;
        String hyperLink = null;
        String site = null;
        String publicationDate = null;
        String imageLink = null;
        Boolean isObject = false;
        RSSNewsObject RSSNewsObject = new RSSNewsObject();
        ArrayList<RSSNewsObject> answerArr = new ArrayList<>();

        String itemTag = RSSFeed.getItemTag();
        String titleTag = RSSFeed.getTitleTag();
        String descriptionTag = RSSFeed.getDescriptionTag();
        String hyperLinkTag = RSSFeed.getHyperLinkTag();
        String publicationDateTag = RSSFeed.getPublicationDateTag();
        String imageLinkTag = RSSFeed.getImageLinkTag();

        try{
            //Create the XML Parser
            XmlPullParser feedParser = Xml.newPullParser();
            feedParser.setInput(inputStream,null);

            //Advance into the main XML body
            feedParser.nextTag();
            while(feedParser.next() != XmlPullParser.END_DOCUMENT){
                int eventType = feedParser.getEventType();
                String tagName = feedParser.getName();

                if(tagName == null){
                    continue;
                }
                //Log.d("XML",tagName);
                if(eventType == XmlPullParser.END_TAG){
                    if(tagName.equalsIgnoreCase(itemTag)){
                        isObject = false;
                        title = null;
                        description = null;
                        hyperLink = null;
                        imageLink = null;
                        publicationDate = null;
                    }
                    continue;
                }

                if(eventType == XmlPullParser.START_TAG){
                    if(tagName.equalsIgnoreCase(itemTag)) {
                        isObject = true;
                        continue;
                    }
                }

                String currentTagText = "";

                if(feedParser.next() == XmlPullParser.TEXT){
                    currentTagText= feedParser.getText();
                    feedParser.nextTag();
                }

                if (tagName.equalsIgnoreCase(titleTag)){
                    title = currentTagText;
                }
                else if (tagName.equalsIgnoreCase(hyperLinkTag)){
                    hyperLink = currentTagText;
                }
                else if (tagName.equalsIgnoreCase(descriptionTag)){
                    description = currentTagText;
                }
                else if (tagName.equalsIgnoreCase(publicationDateTag)){
                    publicationDate = currentTagText;
                }

                if(checkIfObjectIsReady(RSSNewsObject,title,description,hyperLink,imageLink,publicationDate) && isObject){
                    RSSNewsObject.setNewsTitle(title);
                    RSSNewsObject.setNewsDescription(description);
                    RSSNewsObject.setArticleURL(hyperLink);
                    RSSNewsObject.setImageURL(imageLink);
                    RSSNewsObject.setPublicationDate(publicationDate);
                    title = null;
                    description = null;
                    hyperLink = null;
                    imageLink = null;
                    publicationDate = null;
                    answerArr.add(RSSNewsObject);
                    RSSNewsObject = new RSSNewsObject();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            inputStream.close();
        }
        return answerArr;
    }


    private Boolean checkIfObjectIsReady(RSSNewsObject object, String title, String description, String articleLink, String imageLink, String publicationDate){
        Boolean isReady = true;
        if (object.isTitleRequired()){
            isReady = (isReady & title != null);
        }
        if (object.isDescriptionRequired()){
            isReady = (isReady & description != null);
        }
        if (object.isArticleLinkRequired()){
            isReady = (isReady & articleLink != null);
        }
        if (object.isImageRequired()){
            isReady = (isReady & imageLink != null);
        }
        if (object.isDateRequired()){
            isReady = (isReady & publicationDate != null);
        }

        return isReady;
    }
}