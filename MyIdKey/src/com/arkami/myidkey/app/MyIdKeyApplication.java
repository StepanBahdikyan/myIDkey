package com.arkami.myidkey.app;

import android.app.Application;
import android.util.Log;
import com.arkami.myidkey.communication.WebApi;
import com.testflightapp.lib.TestFlight;

/**
 * Created with IntelliJ IDEA.
 * User: sbahdikyan
 * Date: 13-9-23
 * Time: 17:57
 * To change this template use File | Settings | File Templates.
 */
public class MyIdKeyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Initialize TestFlight with your app token.
        TestFlight.takeOff(this, "ea423625-a4fb-4e5e-ae24-228f5c241f4e");
        Log.w("testflight: ",TestFlight.getDeviceInfo().toString());
        WebApi.sendAuthenticationRequest("guest", "guest");
    }
}
