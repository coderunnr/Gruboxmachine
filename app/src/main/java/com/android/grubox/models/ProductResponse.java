package com.android.grubox.models;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.android.grubox.R;
import com.android.grubox.activity.MainActivity;
import com.android.grubox.activity.ProductListing;
import com.android.grubox.databaseutils.VendingDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
import java.util.regex.Pattern;

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
       return this.image;
//        }
    }

    public void getandsetBitmapFromURL(Context context, String src) {
        if (src == null || src.equalsIgnoreCase("null")){
            image = BitmapFactory.decodeResource(context.getResources(), R.drawable.haldirams);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 0, stream);
            image = Bitmap.createScaledBitmap(image,100,100,true);
//            Log.d("images; ", stream.toByteArray());
        }
        else {
//            try {
//                URL url = new URL(src);
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setDoInput(true);
//                connection.connect();
//                Log.d("mylog:  ", src);
//                InputStream input = connection.getInputStream();
//                Bitmap bm = BitmapFactory.decodeStream(input);
//                image = Bitmap.createScaledBitmap(bm,50,50,true);
//            return myBitmap;
                String patternString = "media";
                Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
                String[] myStrings = pattern.split(src);
                Log.d("trying to fetch img: ", myStrings[0] + "Project/media" + myStrings[1]);

                new FetchImage().execute(myStrings[0] + "Project/media" + myStrings[1]);
                Log.d("Image ", "is fetched");
//            } catch (Exception e) {
//                // Log exception
//
//                Log.d("Error! : ", e.getMessage());
////            return null;
//            }
        }


    }
    class FetchImage extends AsyncTask<String,Void,Bitmap>
    {



        @Override
        protected Bitmap doInBackground(String... src) {
//                VendingDatabase vendingDatabase=new VendingDatabase(MainActivity.this);
            try {
                URL url = new URL(src[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
//                Log.d("mylog:  ", src[0]);
                InputStream input = connection.getInputStream();
                Bitmap bm = BitmapFactory.decodeStream(input);
                return bm;

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bm) {
//                Intent intent=new Intent(MainActivity.this,ProductListing.class);
//                startActivity(intent);
            image = Bitmap.createScaledBitmap(bm,300,300,true);

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
