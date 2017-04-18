package com.android.grubox.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.grubox.R;
import com.android.grubox.databaseutils.Utility;

import java.io.IOException;

public class TestMachine extends SerialPortActivity {

    int pos;
    int no=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_machine);

//		setTitle("Loopback test");
        //	mReception = (EditText) findViewById(R.id.EditTextReception);

        //final EditText Emission = (EditText) findViewById(R.id.EditTextEmission);
//		Emission.setOnEditorActionListener(new OnEditorActionListener() {
//			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//				int i;
//				CharSequence t = v.getText();
//				char[] text = new char[t.length()];
//				for (i=0; i<t.length(); i++) {
//					text[i] = t.charAt(i);
//				}
//				try {
//					mOutputStream.write(new String(text).getBytes());
//					mOutputStream.write('\n');
//					Log.v("TryingHard",mOutputStream.toString());
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				return false;
//			}
//		});
        try {
//            VendingDatabase vendingDatabase=new VendingDatabase(GrucardActivity.this);
//            vendingDatabase.open();
//            ProductResponse productResponse=vendingDatabase.getCartData().get(0);
//            Log.v("REsponse",productResponse.getColumn()+"-"+productResponse.getRow());
//            byte[] mbuffer= Utility.getBytesOfProduct(vendingDatabase.getRowandCol(productResponse.getId()));
//            vendingDatabase.close();
        //    String name="Kanishk";
            pos=getIntent().getIntExtra("pos",11);
            byte[] mbuffer= Utility.getBytesOfProduct(pos);
            //	byte[] mbuffer=new byte[]{0x01,0x01,0x00,0x01};
//            mOutputStream.write(mbuffer);
            mOutputStream.write(mbuffer);
            Log.v(getClass().getSimpleName(),mOutputStream.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    @Override
    protected void onDataReceived(final byte[] buffer, final int size) {
        runOnUiThread(new Runnable() {
            public void run() {
//				if (mReception != null) {
                Log.v(getClass().getSimpleName(),buffer.toString());
                Log.v(getClass().getSimpleName(),size+"");
                Log.v("ngcgj","hjvu");
                Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(pos<69) {
                                    Intent intent = new Intent(TestMachine.this, TestMachine.class);
                                    if(pos%10==0)
                                        ++pos;
                                    no++;
                                    if(no>8) {
                                        ++pos;
                                        no=0;
                                    }
                                    intent.putExtra("pos", pos);
                                    finish();
                                    startActivity(intent);
                                }
                                else
                                {
                                    finish();
                                }
                            }
                        }, 14000);

//				}
//				else {
//					Log.v(getClass().getSimpleName(),"No Response");
//				}

            }
        });
    }
}
