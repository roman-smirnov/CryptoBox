package cryptobox.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by roman on 10/8/16.
 */

public class MyApplication extends Application {
    //storing the app context, it's ok
    private static Context sContext;
    private static final String MAIN_PACKAGE_NAME = "roman.com.cryptobox";

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.sContext = getApplicationContext();
    }

    public static Context getContext() {
        return MyApplication.sContext;
    }

    public static SharedPreferences getSharePreferences() {
        return sContext.getSharedPreferences(MAIN_PACKAGE_NAME, MODE_PRIVATE);
    }


}
