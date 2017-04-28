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
import java.nio.ByteBuffer;
import java.util.Arrays;

import static java.lang.Thread.sleep;

public class CashCommunicate extends AppCompatActivity {

    EditText editText;
    static byte RESTARTCASHMACHINE[] = new byte[] {0x01,0x02,0x1A,0x01,0x19};
    static byte QUERYAMOUNT[] = new byte[] {0x01,0x01,0x02,0x03};
    static byte SYNCAMOUNT[] = new byte[] {0x01,0x02,0x0C,0x00,0x0E};
    static byte REFUNDAMOUNT[] = new byte[] {0x01,0x01,0x02,0x03};
    static byte ENABLEBANKNOTE[] = new byte[] {0x01,0x02,0x07,0x01,0x04};
    static byte DISABLEBANKNOTE[] = new byte[] {0x01,0x02,0x07,0x00,0x05};
    static byte CLEARAMOUNT[] = new byte[] {0x01,0x02,0x0C,0x01,0x0F};

    byte RESPONSE[];
    int RESPONSESIZE;
    String RESPONSETYPE;
    int balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_communicate);

//       // openCash();
//
        editText=(EditText)findViewById(R.id.edit_cashcommunicate);
//
////        Handler handler = new Handler();
////        handler.postDelayed(new Runnable() {
////            @Override
////            public void run() {
////                checkCash();
////            }
////        }, 3000);
//
        findViewById(R.id.check_cash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s=editText.getText().toString();
                byte[] b = new BigInteger(s,16).toByteArray();

              //  System.out.print(String.format("%x", Byte.toUnsignedInt(b[1])));
                Intent intent=new Intent(CashCommunicate.this, GrucardActivity.class);
//        intent.putExtra("byte",new byte[]{0x01,0x01,0x02,0x03});
//        intent.putExtra("byte",new byte[]{0x01,0x09,0x03,0x01,0x01,0x00,0x00,0x01,0x27,0x0f,0x00,0x22});
                intent.putExtra("BYTES",b);
                intent.putExtra("REQUESTTYPE","RUBBISH");
                startActivity(intent);
            }
        });

        //ping machine for cash insertion (USE QUERY AMOUNT API)
        //with restart in 15 seconds..
        //to be done 4 times after which fall back to previous cart activity giving appropriate failure message.

        //if inserts cash
//        check cash with sync api, if equal or greater then, disable machine, go to vending, after appropriate logging
//        if not then immediately restart/prepare machine for next acceptance.
//        continue
//        int balance = 0;
//        int sumtotal = 20;
//
//        try {
//            Intent intent=new Intent(CashCommunicate.this,GrucardActivity.class);
//            intent.putExtra("BUFFER", ENABLEBANKNOTE);
//            startActivityForResult(intent, 2);
////            mOutputStream.write(ENABLEBANKNOTE);
////            Thread.sleep(50);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        while(balance < sumtotal){
//            long createdMillis = System.currentTimeMillis();
//            int secondselapsed =0;
//            boolean billinserted = false;
//            boolean timeout = false;
//            int loop = 0;
////            boolean inserted = false;
//
//            do{
//                try {
//                    long nowMillis = System.currentTimeMillis();
//                    secondselapsed = (int)((nowMillis - createdMillis) / 1000);
//
//                    if(secondselapsed > 15*loop){
//                        Intent intent=new Intent(CashCommunicate.this,GrucardActivity.class);
//                        intent.putExtra("BUFFER", RESTARTCASHMACHINE);
//                        startActivityForResult(intent, 2);
//                        int i=0;
//                        while(i<1000000){
//                            i++;
//                        }
////                        mOutputStream.write(RESTARTCASHMACHINE);
////                        Thread.sleep(50);
//                        loop= loop+1;
//                        //ask user to wait?
//                        //if yes, reset loop number to zero
//                        if(loop > 3){
//                            timeout = true;
//                        }
//                    }
//
//                    Intent intent=new Intent(CashCommunicate.this,GrucardActivity.class);
//                    intent.putExtra("BUFFER", QUERYAMOUNT);
//                    startActivityForResult(intent, 2);
//                    Log.v(getClass().getSimpleName(), "After GruCard Activity returned results!! ");
////                    mOutputStream.write(QUERYAMOUNT);
//                    int j=0;
//                    while(j<1000000){
//                        j++;
//                    }
//                    Log.v(getClass().getSimpleName(),QUERYAMOUNT.toString());
//                    for(int i=0;i<QUERYAMOUNT.length;i++) {
//                        Log.v(getClass().getSimpleName(),String.format("%02x", QUERYAMOUNT[i]));
//                    }
//                    Thread.sleep(50);
//                    for(int i=0;i<RESPONSE.length;i++) {
//                        Log.v(getClass().getSimpleName(),String.format("%02x", RESPONSE[i]));
//                    }
//                    //MIGHT USE ASYNCTASK.....
//
//                    if(RESPONSE[8] != 0XFF){
//                        billinserted = true;
//                        intent=new Intent(CashCommunicate.this,GrucardActivity.class);
//                        intent.putExtra("BUFFER", QUERYAMOUNT);
//                        startActivityForResult(intent, 2);
////                        mOutputStream.write(QUERYAMOUNT);
////                        Thread.sleep(50);
//
//                        //user must have inserted some bill...
//                        intent=new Intent(CashCommunicate.this,GrucardActivity.class);
//                        intent.putExtra("BUFFER", RESTARTCASHMACHINE);
//                        startActivityForResult(intent, 2);
////                        mOutputStream.write(RESTARTCASHMACHINE);
////                        Thread.sleep(50);
//
//                        intent=new Intent(CashCommunicate.this,GrucardActivity.class);
//                        intent.putExtra("BUFFER", SYNCAMOUNT);
//                        startActivityForResult(intent, 2);
////                        mOutputStream.write(SYNCAMOUNT);
////                        Thread.sleep(50);
//
//                        for(int i=0;i<RESPONSE.length;i++) {
//                            Log.v(getClass().getSimpleName(),String.format("%02x", RESPONSE[i]));
//                        }
//                        balance = RESPONSE[8];
//
//                    }
////Arrays.equals(aa, bb)
//                } catch (Exception e){
//                    e.printStackTrace();
//                }
//
//            }while(!billinserted  && !timeout);
//
//            if(timeout == true){
//                break;
//            }
//        }


        Intent intent=new Intent(CashCommunicate.this,GrucardActivity.class);
        intent.putExtra("BYTES", CLEARAMOUNT);
        intent.putExtra("REQUESTTYPE", "CLEARAMOUNT");
        startActivityForResult(intent, 2);


        Log.v(getClass().getSimpleName(), "timeout: ");
//        Log.v(getClass().getSimpleName(), "balance: " + balance);
//        Log.v(getClass().getSimpleName(), "sumtotal: " + sumtotal);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            Log.v(getClass().getSimpleName(), "Got data: ");
            RESPONSE=data.getByteArrayExtra("BYTES");
            RESPONSESIZE=data.getIntExtra("SIZE", 0);
            RESPONSETYPE=data.getStringExtra("RESPONSETYPE");
        }

        if((RESPONSETYPE).equals("CLEARAMOUNT")){
            Log.v(getClass().getSimpleName(), "CLEARAMOUNT Response received! ");

            try {
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
            Intent intent=new Intent(CashCommunicate.this,GrucardActivity.class);
            intent.putExtra("BYTES", DISABLEBANKNOTE);
            intent.putExtra("REQUESTTYPE", "DISABLEBANKNOTE");
            startActivityForResult(intent, 2);
        }
        else if((RESPONSETYPE).equals("DISABLEBANKNOTE")){
            Log.v(getClass().getSimpleName(), "DISABLEBANKNOTE Response received! ");

            try {
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
            Intent intent=new Intent(CashCommunicate.this,GrucardActivity.class);
            intent.putExtra("BYTES", RESTARTCASHMACHINE);
            intent.putExtra("REQUESTTYPE", "EARLYRESTART");
            startActivityForResult(intent, 2);
        }
        else if((RESPONSETYPE).equals("EARLYRESTART")){
            Log.v(getClass().getSimpleName(), "EARLYRESTART Response received! ");

            try {
                Thread.sleep(5000);
            }catch (Exception e){
                e.printStackTrace();
            }
            Intent intent=new Intent(CashCommunicate.this,GrucardActivity.class);
            intent.putExtra("BYTES", ENABLEBANKNOTE);
            intent.putExtra("REQUESTTYPE", "ENABLEBANKNOTE");
            startActivityForResult(intent, 2);
        }
        else if((RESPONSETYPE).equals("ENABLEBANKNOTE")){
            Log.v(getClass().getSimpleName(), "ENABLEBANKNOTE Response received! ");

            try {
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
            Intent intent=new Intent(CashCommunicate.this,GrucardActivity.class);
            intent.putExtra("BYTES", QUERYAMOUNT);
            intent.putExtra("REQUESTTYPE", "QUERYAMOUNT");
            startActivityForResult(intent, 2);
        }
        else if((RESPONSETYPE).equals("RESTARTCASHMACHINE")){
            Log.v(getClass().getSimpleName(), "RESTARTCASHMACHINE Response received! ");

            try {
                Thread.sleep(5000);
            }catch (Exception e){
                e.printStackTrace();
            }
            Intent intent=new Intent(CashCommunicate.this,GrucardActivity.class);
            intent.putExtra("BYTES", SYNCAMOUNT);
            intent.putExtra("REQUESTTYPE", "SYNCAMOUNT");
            startActivityForResult(intent, 2);
        }
        else if((RESPONSETYPE).equals("SYNCAMOUNT")){
//            balance = 88;
//            balance = new BigInteger(&RESPONSE[3]).intValue();
//            Ints.fromByteArray(RESPONSE[3]);
            balance = ByteBuffer.wrap(Arrays.copyOfRange(RESPONSE, 3, 7)).getInt();
            Log.v(getClass().getSimpleName(), "SYNCAMOUNT Response received! ");
            Log.v(getClass().getSimpleName(), "balance = " + balance);
            if (balance<3000){
                try {
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                Intent intent = new Intent(CashCommunicate.this, GrucardActivity.class);
                intent.putExtra("BYTES", QUERYAMOUNT);
                intent.putExtra("REQUESTTYPE", "QUERYAMOUNT");
                startActivityForResult(intent, 2);

            }
            else {
//                Intent intent = new Intent(CashCommunicate.this, GrucardActivity.class);
//                intent.putExtra("BYTES", SYNCAMOUNT);
//                intent.putExtra("REQUESTTYPE", "SYNCAMOUNT");
//                startActivityForResult(intent, 2);
                try {
                    Thread.sleep(100);
                }catch (Exception e){
                    e.printStackTrace();
                }
                Intent intent=new Intent(CashCommunicate.this,GrucardActivity.class);
                intent.putExtra("BYTES", DISABLEBANKNOTE);
                intent.putExtra("REQUESTTYPE", "FINALLYDISABLEBANKNOTE");
                startActivityForResult(intent, 2);
                Log.v(getClass().getSimpleName(), "Required Amount received! ");
            }
        }
        else if((RESPONSETYPE).equals("FINALLYDISABLEBANKNOTE")){
            try {
                Thread.sleep(100);
            }catch (Exception e){
                e.printStackTrace();
            }
            Log.v(getClass().getSimpleName(), "Finally disabled banknote! ");
        }
        else if((RESPONSETYPE).equals("QUERYAMOUNT")){

            Log.v(getClass().getSimpleName(), "QUERYAMOUNT Response received! ");

            if(RESPONSE[6] != (byte)0xFF) {
                try {
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }

                Intent intent = new Intent(CashCommunicate.this, GrucardActivity.class);
                intent.putExtra("BYTES", RESTARTCASHMACHINE);
                intent.putExtra("REQUESTTYPE", "RESTARTCASHMACHINE");
                startActivityForResult(intent, 2);
            }
            else{
                try {
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                Intent intent = new Intent(CashCommunicate.this, GrucardActivity.class);
                intent.putExtra("BYTES", QUERYAMOUNT);
                intent.putExtra("REQUESTTYPE", "QUERYAMOUNT");
                startActivityForResult(intent, 2);
            }
        }
        else{
            Log.v(getClass().getSimpleName(), "ABSURD Response type received! ");
        }

    }
}
