package com.android.grubox.models;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.android.grubox.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

/**
 * Created by root on 28/1/17.
 */
public class ProductResponse implements Serializable{
    int id;
    int row_id;
    Bitmap image = null;
    String b_name,f_name,c_name;
    int mrp;
    String catTag,percepTag;
    int quantity;
    int row;
    int column;
    int quantity_cart=1;
//    Bitmap image = null;

    public int getRow_id() {
        return row_id;
    }

    public void setRow_id(int row_id) {
        this.row_id = row_id;
    }

    public Bitmap getImage() {
//        if(image == null) {
//            int images[] = new int[]{R.drawable.haldirams, R.drawable.pudinachips, R.drawable.reallitchi, R.drawable.coca_cola, R.drawable.marschoc, R.drawable.ooooooo};
//            Random random = new Random();
//            return images[random.nextInt(6)];
//        }
//        else{
       return image;
//        }
    }

    public void getandsetBitmapFromURL(Context context, String src) {
        if (src == null){
            image = BitmapFactory.decodeResource(context.getResources(), R.drawable.haldirams);
        }
        else {
            try {
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                image = BitmapFactory.decodeStream(input);
//            return myBitmap;
            } catch (IOException e) {
                // Log exception
//            return null;
            }
        }
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getQuantity_cart() {
        return quantity_cart;
    }

    public void setQuantity_cart(int quantity_cart) {
        this.quantity_cart = quantity_cart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getB_name() {
        return b_name;
    }

    public void setB_name(String b_name) {
        this.b_name = b_name;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public int getMrp() {
        return mrp;
    }

    public void setMrp(int mrp) {
        this.mrp = mrp;
    }

    public String getCatTag() {
        return catTag;
    }

    public void setCatTag(String catTag) {
        this.catTag = catTag;
    }

    public String getPercepTag() {
        return percepTag;
    }

    public void setPercepTag(String percepTag) {
        this.percepTag = percepTag;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
