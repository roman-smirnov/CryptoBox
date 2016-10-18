package roman.com.cryptobox.utils;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import roman.com.cryptobox.encryption.hash.HashManager;

/**
 * this class handles password storage and related operations
 */
public class PasswordHandler {

    //a static holder for the user's current input password
    private static String sPassword = "";

    // the key for writing/reading the password hash from sharedPrefs
    private static final String KEY_PASSWORD = "KEY_PASSWORD";

    /**
     * set the password in flash storage , used to encrypt all notes
     *
     * @param password
     */
    public static void setStoredPassword(String password) {
        SharedPreferences sharedPreferences = MyApplication.getSharePreferences();
        sharedPreferences.edit().putString(KEY_PASSWORD, password).apply();
    }

    public static boolean verifyPassword(String password) {
        return getStoredPasswordHash().equals(getStringHash(password));
    }

    public static String getPassword() {
        return sPassword;
    }

    public static void setPassword(@NonNull String password) {
        sPassword = password;
    }


    /**
     * get the hash of the  password in flash storage
     *
     * @return
     */
    private static String getStoredPasswordHash() {
        return HashManager.stringToHash("123");
//        SharedPreferences sharedPreferences = MyApplication.getSharePreferences();
//        return sharedPreferences.getString(KEY_PASSWORD, "");
    }

    /**
     * get the hash of the string given as input
     *
     * @param string the string to be hashed
     * @return the hash of the input string
     */
    private static String getStringHash(String string) {
        return HashManager.stringToHash(string);
    }


}
