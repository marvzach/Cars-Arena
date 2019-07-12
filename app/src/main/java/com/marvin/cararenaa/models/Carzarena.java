package com.marvin.cararenaa.models;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Carzarena {
    String mMake;
    String mPhone;
    String mWebsite;
    String mPrice;
    String mPhoto_links;



    String mBody_type;
    String mVehicle_type;
    String mEngine;
    String mMade_in;


    String mLatitude;
    String mLongitude;



    public String pushId;

    String index;

    public Carzarena() {

    }

    public Carzarena(String make, String model, String website,
                     String year, String photo_links,
                     String body_type, String vehicle_type, String engine,
                     String made_in,
                     String latitude, String longitude) {
        this.mMake = make;
        this.mPhone = model;
        this.mWebsite = website;
        this.mPrice = year;
        this.mPhoto_links = photo_links;

        this.mBody_type = body_type;
        this.mVehicle_type = vehicle_type;
        this.mEngine = engine;
        this.mMade_in = made_in;


        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.index = "not_specified";


    }


    public String getMake() {
        return mMake;
    }

    public String getPhone() {
        return mPhone;
    }

    public String getWebsite() {
        return  mWebsite;
    }

    public String getPrice() {
        return mPrice;
    }

    public String getPhoto_links(){
        return mPhoto_links;
    }

    public String getBody_type(){
        return mBody_type;
    }

    public String getEngine(){
        return mEngine;
    }

    public String getMade_in(){
        return mMade_in;
    }
    public String getVehicle_type(){
        return mVehicle_type;
    }



    public String getLatitude() {
        return mLatitude;
    }

    public String getLongitude() {
        return mLongitude;
    }
    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}










