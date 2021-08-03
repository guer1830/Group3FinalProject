package com.code.group3finalproject;

public class guideObject {

    private String Title;
    private String Description;
    private String resource;

    public guideObject(String Title, String Description, String resource){
        this.Title = Title;
        this.Description = Description;
        this.resource = resource;
    }

    public String getTitle(){
        return this.Title;
    }

    public String getDetails(){return this.Description;}

    public String getDrawable(){return this.resource;}
}
