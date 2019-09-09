package com.smile.taobaodemo.ui.activity;

import cn.bmob.v3.BmobObject;

public class Data extends BmobObject
{
    private String background;//背景地址
    private String mc;//种子名称
    private String cl;//产量
    private String zq;//周期
    private String jg;//价格

    public void setBackground(String background)
    {
        this.background = background;
    }

    public String getBackground()
    {
        return background;
    }

    public void setMc(String mc)
    {
        this.mc = mc;
    }

    public String getMc()
    {
        return mc;
    }

    public void setCl(String cl)
    {
        this.cl = cl;
    }

    public String getCl()
    {
        return cl;
    }

    public void setZq(String zq)
    {
        this.zq = zq;
    }

    public String getZq()
    {
        return zq;
    }

    public void setJg(String jg)
    {
        this.jg = jg;
    }

    public String getJg()
    {
        return jg;
    }}
