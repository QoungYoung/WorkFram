package com.smile.taobaodemo.bean;

import java.util.Date;

import cn.bmob.v3.BmobObject;

public class SeedsInfo extends BmobObject {



    private String userId;
    private String url;
    private String seedname;
    private String seedStatus;
    private int seedTime;
    private int seedArea;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSeedName() {
        return seedname;
    }

    public void setSeedName(String seedName) {
        this.seedname = seedName;
    }

    public String getSeedStatus() {
        return seedStatus;
    }

    public void setSeedStatus(String seedStatus) {
        this.seedStatus = seedStatus;
    }

    public int getSeedTime() {
        return seedTime;
    }

    public void setSeedTime(int seedTime) {
        this.seedTime = seedTime;
    }

    public int getSeedArea() {
        return seedArea;
    }

    public void setSeedArea(int seedArea) {
        this.seedArea = seedArea;
    }
}
