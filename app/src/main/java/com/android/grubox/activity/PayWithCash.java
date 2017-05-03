package com.android.grubox.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.grubox.R;
import com.android.grubox.connectionutils.ConnectionPost;
import com.android.grubox.connectionutils.URL1;
import com.android.grubox.databaseutils.Utility;
import com.android.grubox.databaseutils.VendingDatabase;
import com.android.grubox.models.ProductResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PayWithCash extends AppCompatActivity {

    List<ProductResponse> productResponses;
    List<ProductResponse> UnsuccessfulproductResponses;
    ProductResponse productResponse;
    int position;
    int REQUEST_CODE = 100;
    int RESULT_CODE = 99;
    int MAXITEMS = 3;
    int amount = 0;
    JSONObject obj = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_with_cash);

        productResponses = new ArrayList<>();
        try {
            VendingDatabase vendingDatabase = new VendingDatabase(PayWithCash.this);
            vendingDatabase.open();
            productResponses = vendingDatabase.getCartData();
            //    Log.v("REsponse",productResponse.getColumn()+"-"+productResponse.getRow());
            //      byte[] mbuffer= Utility.getBytesOfProduct(vendingDatabase.getRowandCol(productResponse.getId()));
            vendingDatabase.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        makelistacctoquantity();

        callVending();

    }

    private void makelistacctoquantity() {
        List<ProductResponse> productResponseList = new ArrayList<>();

        for (int i = 0; i < productResponses.size(); i++) {
            int quantity = productResponses.get(i).getQuantity_cart();
            for (int j = 0; j < quantity; j++) {
                productResponseList.add(productResponses.get(i));
            }
        }
        productResponses = productResponseList;
    }

    private void callVending() {
        if (position < productResponses.size()) {


            if (position > 0) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(PayWithCash.this, ConsoleActivity.class);
                        intent.putExtra("response", productResponses.get(position));
                        startActivityForResult(intent, REQUEST_CODE);
                        ++position;
                    }
                }, 4500);
            } else {
                Intent intent = new Intent(PayWithCash.this, ConsoleActivity.class);
                intent.putExtra("response", productResponses.get(position));
                startActivityForResult(intent, REQUEST_CODE);
                ++position;
            }

        } else {

            try {

                for(;position < MAXITEMS; position++){
                    try {
                        obj.put("Stock_ID" + Integer.toString(position), "NULL");
                        obj.put("Vend" + Integer.toString(position), 0);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                obj.put("TotalAmount", amount);
                obj.put("timestamp", new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
//                obj.put("RefundStatus", "No");
                obj.put("Machine_ID", "myMachine_ID");
            }
            catch (Exception e){
                e.printStackTrace();
            }

            String request=obj.toString();
            Log.v(getClass().getSimpleName(), "Sending unsuccessful vend details: " + request);

            try {
                new PostUnsuccessfulVends (URL1.getUnsuccessfulVendurl(),new JSONObject(request)).execute();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Toast.makeText(PayWithCash.this, "Done", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 2) {
            byte[] RESPONSE = data.getByteArrayExtra("BYTES");
            Integer RESPONSESIZE = data.getIntExtra("SIZE", 0);
            String RESPONSETYPE = data.getStringExtra("RESPONSETYPE");

            Log.d(getClass().getSimpleName(), "resultCode 2 received! vending status received");
            if((RESPONSE[1] == (byte)0x09) && (RESPONSE[2] == (byte)0x05) && (RESPONSE[3] == (byte)0x03)) {

                Log.v(getClass().getSimpleName(), "Vending Unsuccessful recorded! ");
                Log.v(getClass().getSimpleName(), "Recording unsuccessful vend! with position: " + Integer.toString(position-1));
                for(int i=0;i<RESPONSESIZE;i++) {
                    Log.v(getClass().getSimpleName(), String.format("%02x", RESPONSE[i]));
//                    Log.v(getClass().getSimpleName(), String.valueOf(buffer[i]));
//                s=s+String.format("%02x", buffer[i])+" ";
                }
//                Log.v(getClass().getSimpleName(), "size of UnsuccessfulproductResponses list: " + Integer.toString(UnsuccessfulproductResponses.size()));
                if(RESPONSE[4] == (byte)0x01){
                    try {
                        obj.put("Stock_ID" + Integer.toString(position - 1), productResponses.get(position - 1).getId());
                        obj.put("Vend" + Integer.toString(position - 1), 0);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
//                    obj.put("Qty", new Integer(UnsuccessfulproductResponses.get(i).getQuantity_cart()));
                    amount += productResponses.get(position-1).getMrp();
//                    UnsuccessfulproductResponses.add(productResponses.get(position-1));
//                    Log.v(getClass().getSimpleName(), "Recording unsuccessful vend! with position: " + Integer.toString(position-1));
                    Log.v(getClass().getSimpleName(), "adding UNSUCCESSFUL vend product in json: ");
                }
                else if(RESPONSE[4] == (byte)0x00){
                    try {
                        obj.put("Stock_ID" + Integer.toString(position - 1), productResponses.get(position - 1).getId());
                        obj.put("Vend" + Integer.toString(position - 1), 1);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
//
                    amount += productResponses.get(position-1).getMrp();
                    Log.v(getClass().getSimpleName(), "adding successful vend product in json: ");
//                    Log.v(getClass().getSimpleName(), "size of UnsuccessfulproductResponses list: " + Integer.toString(UnsuccessfulproductResponses.size()));
                }
//                Intent intent = new Intent(PayWithCash.this, GrucardActivity.class);
//                intent.putExtra("BYTES", RESTARTCASHMACHIN);
//                intent.putExtra("REQUESTTYPE", "RESTARTCASHMACHINE");
//                startActivityForResult(intent, 2);
                callVending();
            }
            else{
                try {
                    Thread.sleep(2000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                Intent intent=new Intent(PayWithCash.this,GrucardActivity.class);
                intent.putExtra("BYTES", new byte[] {0x01, 0x01, 0x05, 0x04});
                intent.putExtra("REQUESTTYPE", "CHECKVENDINGSTATUS");
                startActivityForResult(intent, 2);
            }
        }
        else if (resultCode == 99) {
            //  REQUEST_CODE=100;
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
            Intent intent=new Intent(PayWithCash.this,GrucardActivity.class);
            intent.putExtra("BYTES", new byte[] {0x01, 0x01, 0x05, 0x04});
            intent.putExtra("REQUESTTYPE", "CHECKVENDINGSTATUS");
            startActivityForResult(intent, 2);


        } else if (resultCode == 80) {
            //current  position fail
//            REQUEST_CODE=97;
            --position;
            callVending();
        }
    }


    public class PostUnsuccessfulVends extends AsyncTask<Void, Void, String>
    {
        String url;
        JSONObject jsonObject;

        public PostUnsuccessfulVends(String url, JSONObject jsonObject) {
            this.url=url;
            this.jsonObject=jsonObject;
        }

        @Override
        protected String doInBackground(Void... voids) {
//            ConnectionPaytm connectionPaytm=new ConnectionPaytm(url,jsonObject,PayWithPaytm.this);
//            return connectionPaytm.connectiontask();
            ConnectionPost connectionPost=new ConnectionPost(url,jsonObject,PayWithCash.this);
            return connectionPost.connectiontask();
        }

        @Override
        protected void onPostExecute(String s) {
            Log.v("Posted!  ",s);

            super.onPostExecute(s);
        }
    }
}
