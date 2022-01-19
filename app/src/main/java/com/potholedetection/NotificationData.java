package com.potholedetection;

public class NotificationData {

    String timestamp,status,id,ad;

    public NotificationData(String t,String s,String id,String ad){
        timestamp = t;
        status = s;
        this.id = id;
        this.ad = ad;
    }

    String getTT(){
        return timestamp;
    }

    String getStatus(){
        return status;
    }

    String getID(){
        return id;
    }

    String getAdd() {
        return ad;
    }
}
