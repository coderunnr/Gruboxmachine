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


/**
 * Created by mrsinghania on 1/3/16.
 */
public class Connection extends AsyncTask<Void, Void, String> {

    static final String COOKIES_HEADER = "Set-Cookie";
    static java.net.CookieManager msCookieManager = new java.net.CookieManager();
    private static HttpURLConnection httpConn;
    private static JSONObject param;
    private static String url;
    private static String TAG = "MyApp - Connection";
    private static String result;
    private static Context context;


    public Connection(String url, JSONObject param, Context context) {
        Connection.url = url;
        Connection.param = param;
        Connection.context = context;
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
//            httpConn.setDoOutput(true);
//            httpConn.setDoInput(true);
//            httpConn.setConnectTimeout(1000 * 60 * 5);
//            // con.setRequestProperty("Content-Type",
//            // "application/x-www-form-urlencoded");
//            httpConn.setRequestProperty("Content-Type", "application/json");
//            httpConn.setRequestProperty("Vary", "Accept");
//            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestMethod("GET");

//            OutputStreamWriter wr = new OutputStreamWriter(httpConn.getOutputStream());
//            wr.write(param.toString());
//            wr.flush();

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
