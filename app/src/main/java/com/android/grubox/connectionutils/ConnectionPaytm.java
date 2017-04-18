package com.android.grubox.connectionutils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Security;
import java.util.TreeMap;


/**
 * Created by mrsinghania on 1/3/16.
 */
public class ConnectionPaytm

        extends AsyncTask<Void, Void, String> {

    static final String COOKIES_HEADER = "Set-Cookie";
    static java.net.CookieManager msCookieManager = new java.net.CookieManager();
    private HttpURLConnection httpConn;
    private JSONObject param;
    private String url;
    private String TAG = "MyApp - Connection";
    private String result;
    private Context context;


    public ConnectionPaytm(String url, JSONObject param, Context context) {
        this.url = url;
        this.param = param;
        this.context = context;
    }

    public String connectiontask() {
        try {
            Log.v(TAG, "Execution Start");

            Log.v(TAG, param.toString());

            Log.v(TAG, url);
            URL object = new URL(url);

            httpConn = (HttpURLConnection) object.openConnection();
//            httpConn.setRequestProperty("Connection", "Keep-Alive");
//           // httpConn.setRequestProperty("Accept-Encoding", "gzip");
////            if (msCookieManager.getCookieStore().getCookies().size() > 0) {
////                httpConn.setRequestProperty("Cookie", TextUtils.join(";", msCookieManager.getCookieStore().getCookies()));
////            } else if (SavedPreferences.getLoggedin(context) == 1) {
////                httpConn.setRequestProperty("Cookie", "JSESSIONID=" + SavedPreferences.getSessionid(context));
////
////            }
//
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
//            httpConn.setConnectTimeout(1000 * 60 * 5);

            String request="{\n" +
                    "\"request\": {\n" +
                    "\"requestType\": \"QR_ORDER\",\n" +
                    "\"merchantGuid\":\"e300efeb-4063-460d-a704-a8bb55e11238\",\n" +
                    "\"merchantName\":\"merchantName\",\n" +
                    "\"displayText\": \"displayText\",\n" +
                    "\"orderId\": \"41086999890\",\n" +
                    "\"amount\": \"1\",\n" +
                    "\"orderDetails\": \"order\",\n" +
                    "\"industryType\":\"RETAIL\"\n" +

                    "},\n" +
                    "\"platformName\": \"PayTM\",\n" +
                    "\"ipAddress\": \"192.168.40.11\",\n" +
                    "\"operationType\": \"QR_CODE\"\n" +
                    "}";

          //  com.paytm.merchant.CheckSumServiceHelper checkSumServiceHelper = com.paytm.merchant.CheckSumServiceHelper.getCheckSumServiceHelper();

//            TreeMap<String,String> parameters = new TreeMap<String,String>();
//            String merchantKey = "hSbRTqnrOMEgYsPN"; //Key provided by Paytm
//            parameters.put("requestType", "QR_ORDER");
//            parameters.put("mid", "Grubox97139556679195");
//            parameters.put("merchantGuid", "df98085d-150e-42ef-8b07-39f168a5258a");
//            parameters.put("merchantName", "Grubox");// Merchant ID (MID) provided by Paytm
//            parameters.put("order_id", "1"); // Merchant’s order id
//            parameters.put("amount", "1"); // Customer ID registered with merchant
//            parameters.put("orderDetails", "dummy order");
//            parameters.put("channelId", "QRCODE");
//            parameters.put("industryType", "Retail"); //Provided by Paytm
//Note: Above mentioned parameters are not complete list of parameters. Please refer integration document for additional parameters which need to be passed.
          //  Security.addProvider(new BouncyCastleProvider());
       //     String checkSum = checkSumServiceHelper.genrateCheckSum(merchantKey, parameters);

            ConnectionPost connectionPost=new ConnectionPost(URL1.BASE_URL_CHECKSUM,new JSONObject(request),context);
            String checkSum=connectionPost.connectiontask();


            JSONObject checkSumjson=new JSONObject(checkSum);
            checkSum=checkSumjson.getString("checksum");
             httpConn.setRequestProperty("checksumhash",
             checkSum);
            httpConn.setRequestProperty("Content-Type", "application/json");
            httpConn.setRequestProperty("mid", "AJoDFR27509495259538");
            httpConn.setRequestProperty("merchantGuid", "e300efeb-4063-460d-a704-a8bb55e11238");
            httpConn.setRequestMethod("POST");

            OutputStreamWriter wr = new OutputStreamWriter(httpConn.getOutputStream());
            wr.write(new JSONObject(request).toString());
            wr.flush();

            // display what returns the POST request

            StringBuilder sb = new StringBuilder();
            int HttpResult = httpConn.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {

                BufferedReader br = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();

//            InputStream inputstream = httpConn.getInputStream();
//            String compresstype = httpConn.getHeaderField("Content-Encoding");
//            if (compresstype != null && compresstype.equalsIgnoreCase("gzip")) {
//                inputstream = new GZIPInputStream(inputstream);
//            }
//
//            BufferedReader in = new BufferedReader(new InputStreamReader(inputstream, "utf-8"));
//
//            String line;
//            while ((line = in.readLine()) != null) {
//                // String line;
//                sb.append(line);
//            }
//            in.close();

            Log.v(TAG, sb.toString());
//                JSONObject ob =new JSONObject(sb.toString());

        }
    else {
                Log.v(TAG, httpConn.getResponseMessage()+HttpResult);
                if(HttpResult==500||HttpResult==404)
                {
                    return "500or404";
                }
            }


//            Map<String, List<String>> headerFields = httpConn.getHeaderFields();
//                      List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);
//                    if (cookiesHeader != null) {
//                      for (String cookie : cookiesHeader) {
//                        msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
//                          if("JSESSIONID".equals(HttpCookie.parse(cookie).get(0).getName())&&(SavedPreferences.getLoggedin(context)==0)){
//                            String value= HttpCookie.parse(cookie).get(0).getValue();
//                              SavedPreferences.setSessionId(value,context);
//                          }
//                  }
//            }

    return sb.toString();


            //return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }


    @Override
    protected String doInBackground(Void... params) {
        return connectiontask();
    }

    @Override
    protected void onPostExecute(final String success) {
        result = success;
    }

}
