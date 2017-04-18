package com.android.grubox.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.android.grubox.R;
import com.android.grubox.databaseutils.Utility;
import com.android.grubox.databaseutils.VendingDatabase;
import com.android.grubox.models.ProductResponse;

import java.io.IOException;
import java.sql.SQLException;

public class GrucardActivity extends SerialPortActivity {



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
        try {
//            VendingDatabase vendingDatabase=new VendingDatabase(GrucardActivity.this);
//            vendingDatabase.open();
//            ProductResponse productResponse=vendingDatabase.getCartData().get(0);
//            Log.v("REsponse",productResponse.getColumn()+"-"+productResponse.getRow());
//            byte[] mbuffer= Utility.getBytesOfProduct(vendingDatabase.getRowandCol(productResponse.getId()));
//            vendingDatabase.close();

            //byte[] mbuffer=new byte[]{0x01,0x09,0x03,0x01,0x09,0x00,0x00,0x00,0x00,0x00,0x01,0x03};
            byte[] mbuffer=new byte[]{0x01,0x03,0x01,0x01,0x01,0x02};
            mOutputStream.write(mbuffer);
//          mOutputStream.write(name.getBytes());
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
                finish();
//				}
//				else {
//					Log.v(getClass().getSimpleName(),"No Response");
//				}

            }
        });
    }
}
