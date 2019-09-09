package com.smile.taobaodemo.bean;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
public class AreaBean {

    private ImageView imageView;
    private String title;
    private String type;
    private String info;
    private String price;
    private Bitmap bitmap;

    public AreaBean(String url, String title, String type, String info, String price) {
        bitmap = null;
        this.title = title;
        this.type = type;
        this.info = info;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
