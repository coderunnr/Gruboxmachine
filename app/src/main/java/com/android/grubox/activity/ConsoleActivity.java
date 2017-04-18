/*
 * Copyright 2009 Cedric Priscal
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package com.android.grubox.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.grubox.R;
import com.android.grubox.databaseutils.Utility;
import com.android.grubox.databaseutils.VendingDatabase;
import com.android.grubox.models.ProductResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

public class ConsoleActivity extends SerialPortActivity {

	CountDownTimer timer;
	boolean isSecond=false;
	ConsoleActivity consoleActivity;
	ProductResponse productResponse;

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.v("REstart","REstart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.v("Resume","Resume");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.console_new);
		consoleActivity=this;
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
//		timer=new Timer(true);
//		timer.schedule(new TimerTask() {
//			@Override
//			public void run() {
//
//				Log.v("sdgas","waiting");
//setResult(80);
//				finish();
//				return;
//			}
//		},10000);


		timer=new CountDownTimer(10000, 5000) {

			public void onTick(long millisUntilFinished) {

			}

			public void onFinish() {
				callAgain();
				return;
			}
		}.start();


		callAgain();






	}

	public void callAgain()
	{
		try {
			VendingDatabase vendingDatabase=new VendingDatabase(ConsoleActivity.this);
			vendingDatabase.open();
			productResponse=(ProductResponse) getIntent().getSerializableExtra("response");
			Log.v("REsponse",productResponse.getColumn()+"-"+productResponse.getRow());
			byte[] mbuffer=Utility.getBytesOfProduct(vendingDatabase.getRowandCol(productResponse.getId()));
			vendingDatabase.close();
			//byte[] mbuffer=new byte[]{0x01,0x09,0x03,0x01,0x09,0x00,0x00,0x00,0x00,0x00,0x01,0x03};
			//	byte[] mbuffer=new byte[]{0x01,0x01,0x00,0x01};
			mOutputStream.write(mbuffer);
			Log.v(getClass().getSimpleName(),mOutputStream.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
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
					setResult(99);
					timer.cancel();
					finish();

	//				timer.cancel();
//					setResult(99);
//					finish();
//					return;
//				}
//				else {
//					Log.v(getClass().getSimpleName(),"No Response");
//				}

			}
		});
	}

}
