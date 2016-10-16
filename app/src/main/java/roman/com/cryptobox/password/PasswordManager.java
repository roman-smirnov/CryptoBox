package roman.com.cryptobox.password;

import android.content.SharedPreferences;

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
    public void setPassword(String password) {
        SharedPreferences sharedPreferences = MyApplication.getSharePreferences();
        sharedPreferences.edit().putString(PASSWORD, password).apply();
    }

    public boolean verifyPassword(String password) {
        SharedPreferences sharedPreferences = MyApplication.getSharePreferences();
        return password.equals(sharedPreferences.getString(PASSWORD, ""));
    }

    /**
     * get the pass from flash storage
     *
     * @return
     */
    private String getPassword() {
        SharedPreferences sharedPreferences = MyApplication.getSharePreferences();
        return sharedPreferences.getString(PASSWORD, "");
    }


}
