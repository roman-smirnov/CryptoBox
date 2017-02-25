package cryptobox.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import cryptobox.modules.SplashModule;

/**
 * Created by roman on 10/8/16.
 */

public class MyApplication extends Application {
    //storing the app context, it's ok
    private static Context sContext;
    private static final String MAIN_PACKAGE_NAME = "roman.com.cryptobox";

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.sContext = getApplicationContext();

        /**
         * Here we need to setup all the Modules we create for dagger.
         */
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .splashModule(new SplashModule())
                .build();
    }

    public static Context getContext() {
        return MyApplication.sContext;
    }

    public static SharedPreferences getSharedPreferences() {
        return sContext.getSharedPreferences(MAIN_PACKAGE_NAME, MODE_PRIVATE);
    }

    public ApplicationComponent getComponent() {
        return component;
    }


}
