package com.smile.taobaodemo.bean;

import cn.bmob.v3.BmobObject;

public class PersonAreaInfo extends BmobObject {

    private String userId;
    private String areaName;
    private int countArea;
    private int areaNo;
    private int usedArea;
    private int freedArea;
    private int freeDay;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getCountArea() {
        return countArea;
    }

    public void setCountArea(int countArea) {
        this.countArea = countArea;
    }

    public int getAreaNo() {
        return areaNo;
    }

    public void setAreaNo(int areaNo) {
        this.areaNo = areaNo;
    }

    public int getUsedArea() {
        return usedArea;
    }

    public void setUsedArea(int usedArea) {
        this.usedArea = usedArea;
    }

    public int getFreedArea() {
        return freedArea;
    }

    public void setFreedArea(int freedArea) {
        this.freedArea = freedArea;
    }

    public int getFreeDay() {
        return freeDay;
    }

    public void setFreeDay(int freeDay) {
        this.freeDay = freeDay;
    }
}
