package roman.com.cryptobox;

import android.content.SharedPreferences;

/**
 * Created by roman on 10/3/16.
 */
public class PassHolder {
    private static String mPassword;
    private static PassHolder ourInstance = new PassHolder();

    SharedPreferences mSharedPreferences;


    public static PassHolder getInstance() {
        return ourInstance;
    }

    private PassHolder() {
        mPassword = "avishai_is_king";
    }


}
