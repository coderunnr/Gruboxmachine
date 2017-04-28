package com.android.grubox.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.grubox.R;
import com.android.grubox.databaseutils.Utility;
import com.android.grubox.databaseutils.VendingDatabase;
import com.android.grubox.models.ProductResponse;

import java.io.IOException;
import java.sql.SQLException;

public class GrucardActivity extends SerialPortActivity {

    ProductResponse productResponse;

    String requesttype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grucard);

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
        Log.v(getClass().getSimpleName(), "In GruCard Activity before try block!! ");
        try {
//            VendingDatabase vendingDatabase=new VendingDatabase(GrucardActivity.this);
//            vendingDatabase.open();
//            ProductResponse productResponse=vendingDatabase.getCartData().get(0);
//            Log.v("REsponse",productResponse.getColumn()+"-"+productResponse.getRow());
//            byte[] mbuffer= Utility.getBytesOfProduct(vendingDatabase.getRowandCol(productResponse.getId()));
//            vendingDatabase.close();

//            byte[] mbuffer=new byte[]{ 0x01, 0x09, 0x03, 0x01, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x0B };
//            byte[] mbuffer=new byte[]{0x01,0x02,0x07,0x01,0x04};
            Log.v(getClass().getSimpleName(), "In GruCard Activity!! ");
            byte[] mbuffer=getIntent().getByteArrayExtra("BYTES");
            requesttype=getIntent().getStringExtra("REQUESTTYPE");

//            if(requesttype == "ENABLEBANKNOTE"){
//
//            }
            mOutputStream.write(mbuffer);
//          mOutputStream.write(name.getBytes());
            Log.v(getClass().getSimpleName(),mOutputStream.toString());
            for(int i=0;i<mbuffer.length;i++) {
                Log.v(getClass().getSimpleName(),String.format("%02x", mbuffer[i]));
            }
//            callAgain();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

                Toast.makeText(GrucardActivity.this,s,Toast.LENGTH_LONG).show();

//                for(int i=0;i<size;i++) {
//
//                    Log.v(getClass().getSimpleName(),Integer.toHexString(buffer[i]));
////                    Log.v(getClass().getSimpleName(), String.valueOf(buffer[i]));
//                }
                Log.v(getClass().getSimpleName(),size+"");
                Intent intent=new Intent();
                intent.putExtra("BYTES",buffer);
                intent.putExtra("SIZE",size);
                intent.putExtra("RESPONSETYPE", requesttype);
                setResult(2,intent);
                finish();
//				}
//				else {
//					Log.v(getClass().getSimpleName(),"No Response");
//				}

            }
        });
    }
}
