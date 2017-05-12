package com.android.grubox.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.grubox.R;
import com.android.grubox.connectionutils.Connection;
import com.android.grubox.connectionutils.ConnectionPaytm;
import com.android.grubox.connectionutils.ConnectionPost;
import com.android.grubox.connectionutils.URL1;
import com.android.grubox.databaseutils.Utility;

import org.json.JSONException;
import org.json.JSONObject;

public class PayWithPaytm extends AppCompatActivity {

    ImageView imageView;
    JSONObject jsonObject1;
    PaywithPaytmStatusCheck paywithPaytmStatusCheck;
    String order_id;


    @Override
    protected void OnDestroy(){
        super.onDestroy();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_with_paytm);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        imageView=(ImageView)findViewById(R.id.imageview_qrcode);
        int pay=getIntent().getIntExtra("money",10);
        TextView text_showamount=(TextView)findViewById(R.id.text_showamout);

        text_showamount.setText(getString(R.string.Rs)+" "+pay);
        order_id="1"+System.currentTimeMillis();


        String request="{\n" +
                "\"request\": {\n" +
                "\"requestType\": \"QR_ORDER\",\n" +
                "\"merchantGuid\":\"df98085d-150e-42ef-8b07-39f168a5258a\",\n" +
                "\"merchantName\":\"Grubox\",\n" +
                "\"displayText\": \"Pay to Grubox\",\n" +
                "\"orderId\": \""+order_id+"\",\n" +
                "\"amount\": \""+pay+"\",\n" +
                "\"orderDetails\": \"order\",\n" +
                "\"industryType\":\"RETAIL\"\n" +

                "},\n" +
                "\"platformName\": \"PayTM\",\n" +
                "\"ipAddress\": \"192.168.40.11\",\n" +
                "\"operationType\": \"QR_CODE\"\n" +
                "}";

        try {
            new PaywithPaytmAsync(URL1.getQRCodeStock(),new JSONObject(request)).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }



//        findViewById(R.id.button_paytmpaymentdone).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferences sp = getSharedPreferences("grubox_port", MODE_PRIVATE);
//                sp.edit().putInt("called",0).apply();
//                Intent intent=new Intent(PayWithPaytm.this, PayWithCash.class);
//                startActivity(intent);
//                finish();
//            }
//        });


    }

    public class PaywithPaytmAsync extends AsyncTask<Void, Void, String>
    {
String url;
        JSONObject jsonObject;

        public PaywithPaytmAsync(String url, JSONObject jsonObject) {
            this.url=url;
            this.jsonObject=jsonObject;
        }

        @Override
        protected String doInBackground(Void... voids) {
//            ConnectionPaytm connectionPaytm=new ConnectionPaytm(url,jsonObject,PayWithPaytm.this);
//            return connectionPaytm.connectiontask();
            ConnectionPost connectionPost=new ConnectionPost(url,jsonObject,PayWithPaytm.this);
            return connectionPost.connectiontask();
        }

        @Override
        protected void onPostExecute(String s) {
            Log.v("Paytn",s);
            try {
                JSONObject jsonObject=new JSONObject(s);
                JSONObject response=jsonObject.getJSONObject("response");
                String QRCode=response.getString("path");

                byte[] decodedString = Base64.decode(QRCode, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imageView.setImageBitmap(decodedByte);

                jsonObject1=new JSONObject();

                jsonObject1.put("orderid",order_id);

                paywithPaytmStatusCheck=new PaywithPaytmStatusCheck("http://104.199.176.31/Project/api/Manager/GetPaymentStatus/",jsonObject1);
                paywithPaytmStatusCheck.execute();

                new CountDownTimer(60000, 10000) {

                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {
                        paywithPaytmStatusCheck.cancel(true);
                        Toast.makeText(getApplicationContext(),"SORRY! TIMED OUT!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }.start();


            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }



    public class PaywithPaytmStatusCheck extends AsyncTask<Void, Void, String>
    {
        String url;
        JSONObject jsonObject;

        public PaywithPaytmStatusCheck(String url, JSONObject jsonObject) {
            this.url=url;
            this.jsonObject=jsonObject;
        }

        @Override
        protected String doInBackground(Void... voids) {
//            ConnectionPaytm connectionPaytm=new ConnectionPaytm(url,jsonObject,PayWithPaytm.this);
//            return connectionPaytm.connectiontask();
            ConnectionPost connectionPost=new ConnectionPost(url,jsonObject,PayWithPaytm.this);
            return connectionPost.connectiontask();
        }

        @Override
        protected void onPostExecute(String s) {
            Log.v("Paytn",s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                String status = jsonObject.getString("status");

                if (isCancelled()){
                   return;
                }
                if(status.contentEquals("PENDING"))
                {
//                    final Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            new PaywithPaytmStatusCheck("http://192.168.0.103:8000/story/api/GetPaymentStatus/",jsonObject1).execute();
//                        }
//                    }, 1000);
                    try {
                        Thread.sleep(1000);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    new PaywithPaytmStatusCheck("http://104.199.176.31/Project/api/Manager/GetPaymentStatus/",jsonObject1).execute();

                }
                else if (status.contentEquals("FAILURE"))
                {
                    try {
                        Thread.sleep(1000);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
//                    final Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            new PaywithPaytmStatusCheck("http://192.168.0.103:8000/story/api/GetPaymentStatus/",jsonObject1).execute();
//                        }
//                    }, 1000);

                    new PaywithPaytmStatusCheck("http://104.199.176.31/Project/api/Manager/GetPaymentStatus/",jsonObject1).execute();
                }
                else if (status.contentEquals("SUCCESS"))
                {
                    SharedPreferences sp = getSharedPreferences("grubox_port", MODE_PRIVATE);
                    sp.edit().putInt("called",0).apply();
                    Intent intent=new Intent(PayWithPaytm.this, PayWithCash.class);
                    startActivity(intent);
                    finish();
                    paywithPaytmStatusCheck.cancel(true);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }
}
