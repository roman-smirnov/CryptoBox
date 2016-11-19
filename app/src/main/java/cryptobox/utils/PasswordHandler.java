package cryptobox.utils;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import cryptobox.encryption.hash.HashManager;

/**
 * this class handles password storage and related operations
 */
public class PasswordHandler {

    //a static holder for the user's current input password
    private static String sPassword = "";

    // the key for writing/reading the password hash from sharedPrefs
    private static final String KEY_PASSWORD = "KEY_PASSWORD";


    /**
     * verify the input password hash matches the stored password's hash
     *
     * @param password the input password
     * @return true if they match, false otherwise
     */
    public static boolean verifyPassword(String password) {
        return getStoredPasswordHash().equals(getStringHash(password));
    }

    /**
     * check if the password was not set yet
     *
     * @return true if password was not set yet, false otherwise
     */
    public static boolean isStoredPasswordSet() {
        //if the password is not set - it is equal to an empty string
        return !getStoredPasswordHash().equals(HashManager.stringToHash(""));
    }

    /**
     * get the in memory password for the current session
     *
     * @return the current sessions password - will returns an empty string ("") if not set
     */
    public static String getSessionPassword() {
        return sPassword;
    }

    /**
     * set the in memory password for the current session
     *
     * @param password the input password
     */
    public static void setSessionPassword(@NonNull String password) {
        sPassword = password;
    }


    /**
     * get the hash of the  password in flash storage
     *
     * @return
     */
    private static String getStoredPasswordHash() {
        SharedPreferences sharedPreferences = MyApplication.getSharePreferences();
        return HashManager.stringToHash(sharedPreferences.getString(KEY_PASSWORD, ""));
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

    /**
     * set the password in flash storage , used to encrypt all notes
     *
     * @param password
     */
    public static void setStoredPassword(String password) {
        SharedPreferences sharedPreferences = MyApplication.getSharePreferences();
        sharedPreferences.edit().putString(KEY_PASSWORD, password).apply();
    }


}
