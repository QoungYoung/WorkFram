package com.smile.taobaodemo.model.entity;
public class TraceBase {
    /** 时间 */
    private String acceptTime;
    /** 描述 */
    private String acceptStation;

    public TraceBase() {
    }

    public TraceBase(String acceptTime, String acceptStation) {
        this.acceptTime = acceptTime;
        this.acceptStation = acceptStation;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getAcceptStation() {
        return acceptStation;
    }

    public void setAcceptStation(String acceptStation) {
        this.acceptStation = acceptStation;
    }
}
