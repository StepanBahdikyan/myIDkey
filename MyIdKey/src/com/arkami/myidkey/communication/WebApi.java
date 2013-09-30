package com.arkami.myidkey.communication;

import android.os.Looper;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sbahdikyan
 * Date: 13-9-24
 * Time: 14:34
 * To change this template use File | Settings | File Templates.
 */
public class WebApi {
    private final static String testUrl =  "http://mikapi01.snapbridge.com/mik/login?username=guest&password=guest";
    private final static String keycardsUrl = "http://mikapi01.snapbridge.com/mik/guest/keycards?cert=b861657521d52a9ae0d7fabe88a5b607";
    /**
     *
     * @param username
     * @param password
     */
    public static void sendAuthenticationRequest(final String username, final String password) {
        Thread t = new Thread() {

            public void run() {
                Looper.prepare(); //For Preparing Message Pool for the child Thread
                HttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
                HttpResponse response;
                JSONObject json = new JSONObject();

                try {
                    HttpGet get = new HttpGet(testUrl);
                    response = client.execute(get);
                    int status = response.getStatusLine().getStatusCode();
                    if (status == 200) {
                        String result = EntityUtils.toString(response.getEntity());
                        Log.w("response", result);

                        json = new JSONObject(result);

                        JSONObject userObject = json.getJSONObject("user");
                        String certificate = json.getString("cert");
                        String usernameFromJson = userObject.getString("username");
                        getUserKeyCards(usernameFromJson,certificate);
                    }
                } catch(Exception e) {
                    e.printStackTrace();
//                    createDialog("Error", "Cannot Estabilish Connection");
                }

                Looper.loop(); //Loop in the message queue
            }
        };

        t.start();

    }

    /**
     *
     * @param username
     * @param certificate
     */
    public static void getUserKeyCards(final String username, final String certificate) {
        Thread t = new Thread() {

            public void run() {
                Looper.prepare(); //For Preparing Message Pool for the child Thread
                HttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
                HttpResponse response;
                JSONObject json = new JSONObject();

                try {
                    HttpGet get = new HttpGet(keycardsUrl);
                    response = client.execute(get);
                    int status = response.getStatusLine().getStatusCode();
                    if (status == 200) {
                        String result = EntityUtils.toString(response.getEntity());
                        Log.w("response", result);

//                        json = new JSONObject(result);
                        JSONArray keycards = new JSONArray(result);

                        Log.w("keycards", result);
                        Log.w("keycards number", keycards.length()+"");
                        Log.w("keycard 0 name ", keycards.getJSONObject(0).getString("keycard_name")) ;
//                        JSONObject userObject = json.getJSONObject("user");
//                        String certificate = json.getString("cert");
//                        String usernameFromJson = userObject.getString("username");

                    }
                } catch(Exception e) {
                    e.printStackTrace();
//                    createDialog("Error", "Cannot Estabilish Connection");
                }

                Looper.loop(); //Loop in the message queue
            }
        };

        t.start();

    }

    /**
     * Prints result from stream
     * @param stream
     */
    private static void printResult(InputStream stream){

        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.w("print error:", e.getMessage());
        }
        Log.w("print result:", builder.toString());
    }
}
