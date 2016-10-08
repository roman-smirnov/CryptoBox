package roman.com.cryptobox;

import android.content.SharedPreferences;

/**
 * Created by roman on 10/3/16.
 *
 * Edited by Avishai
 * Need to refactor this class to make it work with user's password and not with hardcoded one.
 */
public class PassHolder {

    private static String mPassword;
    //private static PassHolder ourInstance = new PassHolder();


    private PassHolder()
    {
        mPassword = "avishai_is_king";
    }

    public static String getInstance(){
        return mPassword;
    }
}
