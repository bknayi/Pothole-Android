package com.potholedetection.admin;

public class PotholeData {

    String id,photoURL,lat,lon,timestamp,addr;

    PotholeData(String id,String photoURL,String lat,String lon,String timestamp,String addr){

        this.id = id;
        this.photoURL = photoURL;
        this.lat = lat;
        this.lon =lon;
        this.timestamp = timestamp;
        this.addr=addr;

    }

    String getID(){ return id; }

    String getURL(){ return photoURL; }

    String getLat(){ return lat; }

    String getLon(){ return lon; }

    String getTime(){ return timestamp; }

    String getaddr(){ return addr; }
}
