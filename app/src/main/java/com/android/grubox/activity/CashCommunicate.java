package com.android.grubox.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.grubox.R;

import java.io.IOException;
import java.math.BigInteger;

import static java.lang.Thread.sleep;

public class CashCommunicate extends SerialPortActivity {

    EditText editText;
    static byte RESTARTCASHMACHINE[] = {0x01,0x01,0x02,0x03};
    static byte QUERYAMOUNT[] = {0x01,0x01,0x02,0x03};
    static byte SYNCAMOUNT[] = {0x01,0x01,0x02,0x03};
    static byte CLEARAMOUNTONBOARD[] = {0x01,0x01,0x02,0x03};
    static byte REFUNDAMOUNT[] = {0x01,0x01,0x02,0x03};
    byte RESPONSE[];
    int RESPONSESIZE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_communicate);

//       // openCash();
//
//        editText=(EditText)findViewById(R.id.edit_cashcommunicate);
//
////        Handler handler = new Handler();
////        handler.postDelayed(new Runnable() {
////            @Override
////            public void run() {
////                checkCash();
////            }
////        }, 3000);
//
//        findViewById(R.id.check_cash).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String s=editText.getText().toString();
//                byte[] b = new BigInteger(s,16).toByteArray();
//
//              //  System.out.print(String.format("%x", Byte.toUnsignedInt(b[1])));
//                Intent intent=new Intent(CashCommunicate.this, GrucardActivity.class);
////        intent.putExtra("byte",new byte[]{0x01,0x01,0x02,0x03});
////        intent.putExtra("byte",new byte[]{0x01,0x09,0x03,0x01,0x01,0x00,0x00,0x01,0x27,0x0f,0x00,0x22});
//                intent.putExtra("byte",b);
//                startActivity(intent);
//            }
//        });

        //ping machine for cash insertion (USE QUERY AMOUNT API)
        //with restart in 15 seconds..
        //to be done 4 times after which fall back to previous cart activity giving appropriate failure message.

        //if inserts cash
//        check cash with sync api, if equal or greater then, disable machine, go to vending, after appropriate logging
//        if not then immediately restart/prepare machine for next acceptance.
//        continue
        int balance = 0;
        int sumtotal = 100;
        while(balance < sumtotal){
            long createdMillis = System.currentTimeMillis();
            int secondselapsed =0;
            boolean billinserted = false;
            boolean timeout = false;
            int loop = 0;
            boolean inserted = false;

            do{
                try {
                    long nowMillis = System.currentTimeMillis();
                    secondselapsed = (int)((nowMillis - createdMillis) / 1000);

                    if(secondselapsed > 15*loop){
                        mOutputStream.write(RESTARTCASHMACHINE);
                        Thread.sleep(50);
                        loop= loop+1;
                        if(loop > 3){
                            timeout = true;
                        }
                    }
                    mOutputStream.write(QUERYAMOUNT);

                    Log.v(getClass().getSimpleName(),mOutputStream.toString());
                    for(int i=0;i<QUERYAMOUNT.length;i++) {
                        Log.v(getClass().getSimpleName(),String.format("%02x", QUERYAMOUNT[i]));
                    }
                    Thread.sleep(50);
                    //MIGHT USE ASYNCTASK.....

                    if(RESPONSE[8]!= 0XFF){
                        inserted = true;
                        mOutputStream.write(QUERYAMOUNT);
                        Thread.sleep(50);

                        //user must have inserted some bill...
                        mOutputStream.write(RESTARTCASHMACHINE);
                        Thread.sleep(50);

                        mOutputStream.write(SYNCAMOUNT);
                        Thread.sleep(50);

                        balance = RESPONSE[8];

                    }
//Arrays.equals(aa, bb)
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                }
                long nowMillis = System.currentTimeMillis();
                secondselapsed = (int)((nowMillis - createdMillis) / 1000);
            }while(!billinserted  && secondselapsed < 15 && !timeout);

            if(timeout == true){
                break;
            }
        }
    }

    private void checkCash() {
        SharedPreferences sp = getSharedPreferences("grubox_port", MODE_PRIVATE);
        sp.edit().putInt("called",0).apply();
        Intent intent=new Intent(CashCommunicate.this, GrucardActivity.class);
//        intent.putExtra("byte",new byte[]{0x01,0x01,0x02,0x03});
//        intent.putExtra("byte",new byte[]{0x01,0x09,0x03,0x01,0x01,0x00,0x00,0x01,0x27,0x0f,0x00,0x22});
        intent.putExtra("byte",new byte[]{0x01,0x02,0x0c,0x00,0x0e});
        startActivity(intent);
    }

    private void openCash() {
        SharedPreferences sp = getSharedPreferences("grubox_port", MODE_PRIVATE);
        sp.edit().putInt("called",0).apply();
        Intent intent=new Intent(CashCommunicate.this, GrucardActivity.class);
//        intent.putExtra("byte",new byte[]{0x01,0x02,0x07,0x01,0x04});
        intent.putExtra("byte",new byte[]{0x01,0x02,0x1a,0x01,0x19});
        startActivity(intent);

    }

    @Override
    protected void onDataReceived(final byte[] buffer, final int size) {
        runOnUiThread(new Runnable() {
            public void run() {
//				if (mReception != null) {
                String s="";
                for(int i=0;i<size;i++) {
                    Log.v(getClass().getSimpleName(), String.format("%02x", buffer[i]));
//                    Log.v(getClass().getSimpleName(), String.valueOf(buffer[i]));
                    s=s+String.format("%02x", buffer[i])+" ";
                }

//                Toast.makeText(GrucardActivity.this,s,Toast.LENGTH_LONG).show();

//                for(int i=0;i<size;i++) {
//
//                    Log.v(getClass().getSimpleName(),Integer.toHexString(buffer[i]));
////                    Log.v(getClass().getSimpleName(), String.valueOf(buffer[i]));
//                }
                Log.v(getClass().getSimpleName(),size+"");
                RESPONSE = buffer;
                RESPONSESIZE = size;
                finish();
//				}
//				else {
//					Log.v(getClass().getSimpleName(),"No Response");
//				}

            }
        });
    }
}
