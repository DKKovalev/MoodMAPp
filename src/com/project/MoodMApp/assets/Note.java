package com.project.MoodMApp.assets;

import android.graphics.Bitmap;

/**
 * Created by PsichO on 03.04.14.
 */
public class Note {
    String mood;
    String comment;
    String date;

    String image;

    Double lat;
    Double lng;

    int ID;

    public  Note(){

    }

    public Note (int ID, String mood, String comment, String image,String date, Double lat, Double lng){
        this.ID = ID;
        this.mood = mood;
        this.comment = comment;
        this.image = image;
        this.date = date;
        this.lat = lat;
        this.lng = lng;
    }

    public Note (String mood, String date, String image, Double lat, Double lng){
        this.mood = mood;
        this.image = image;
        this.date = date;
        this.lat = lat;
        this.lng = lng;
    }

    public Note (String mood, String comment, String image, String date, Double lat, Double lng){
        this.mood = mood;
        this.comment = comment;
        this.image = image;
        this.date = date;
        this.lat = lat;
        this.lng = lng;
    }

    public Note (int ID, String mood, String comment, String image, String date){
        this.ID = ID;
        this.mood = mood;
        this.comment = comment;
        this.image = image;
        this.date = date;
    }

    public String getMood(){
        return this.mood;
    }

    public void setMood(String mood){
        this.mood = mood;
    }

    public String getComment(){
        return this.comment;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public String getDate(){
        return this.date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public Double getLat(){
        return this.lat;
    }

    public void setLat(Double lat){
        this.lat = lat;
    }

    public Double getLng(){
        return this.lng;
    }

    public void setLng(Double lng){
        this.lng = lng;
    }

    public long getID(){
        return this.ID;
    }

    public void setID(int ID){
        this.ID = ID;
    }

    public String getImage(){
        return this.image;
    }

    public void setImage(String image){
        this.image = image;
    }
}
