package com.android.grubox.models;

import java.io.Serializable;

/**
 * Created by root on 2/2/17.
 */
public class TagModel implements Serializable{

    int type; // 0-price  1-brands 2-flavours
    String name;
    int lowrate,highrate;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLowrate() {
        return lowrate;
    }

    public void setLowrate(int lowrate) {
        this.lowrate = lowrate;
    }

    public int getHighrate() {
        return highrate;
    }

    public void setHighrate(int highrate) {
        this.highrate = highrate;
    }
}
