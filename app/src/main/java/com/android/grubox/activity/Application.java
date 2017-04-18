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

import android.content.SharedPreferences;

import com.android.grubox.SerialPort;
import com.android.grubox.SerialPortFinder;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;



public class Application extends android.app.Application {

	public SerialPortFinder mSerialPortFinder = new SerialPortFinder();
	private SerialPort mSerialPort = null;

	public SerialPort getSerialPort() throws SecurityException, IOException, InvalidParameterException {
		if (mSerialPort == null) {
			/* Read serial port parameters */
			SharedPreferences sp = getSharedPreferences("grubox_port", MODE_PRIVATE);
			int called=sp.getInt("called",0);
//			String path = sp.getString("DEVICE", "/dev/ttyS0");
//			int baudrate = Integer.decode(sp.getString("BAUDRATE", "9600"));
			String path;
			int baudrate=9600;
			if(called==0)
			{
				path ="/dev/ttyS0";
			}
			else
			{
				path ="/dev/ttyS1";

			}

			/* Check parameters */
			if ( (path.length() == 0) || (baudrate == -1)) {
				throw new InvalidParameterException();
			}

			/* Open the serial port */
			mSerialPort = new SerialPort(new File(path), baudrate, 0);
		}
		return mSerialPort;
	}

	public void closeSerialPort() {
		if (mSerialPort != null) {
			mSerialPort.close();
			mSerialPort = null;
		}
	}
}
