package roman.com.cryptobox.password;

import android.content.SharedPreferences;

import roman.com.cryptobox.encryption.hash.HashManager;
import roman.com.cryptobox.mainapplication.MyApplication;

/**
 * Created by roman on 10/8/16.
 */
public class PasswordManager {

    private static final String PASSWORD = "PASSWORD";

    /**
     * set the pass to flash storage
     *
     * @param password
     */
    public static void setPassword(String password) {
        SharedPreferences sharedPreferences = MyApplication.getSharePreferences();
        sharedPreferences.edit().putString(PASSWORD, password).apply();
    }

    //TODO replace the other two methods with a call to this one
    public static boolean verifyPassword(String password) {
        SharedPreferences sharedPreferences = MyApplication.getSharePreferences();
        return password.equals(sharedPreferences.getString(PASSWORD, ""));
    }

    /**
     * get the pass from flash storage
     *
     * @return
     */
    public static String getStoredPasswordHash() {
        return HashManager.stringToHash("123");
//        SharedPreferences sharedPreferences = MyApplication.getSharePreferences();
//        return sharedPreferences.getString(PASSWORD, "");
    }

    public static String getPasswordHash(String password) {
        return HashManager.stringToHash(password);
    }


}
