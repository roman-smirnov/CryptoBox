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


    // encapsulate functionality for password that sores in RAM during user session with the app.
    public static class SessionPassword{

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
    }

    // encapsulate functionality for password that sores in SP.
    public static class StoredPassword{

        /**
         * verify the input password hash matches the stored password's hash
         *
         * @param password the input password
         * @return true if they match, false otherwise
         */
        public static boolean verifyPassword(String password) {
            return getStoredPasswordHash().equals(HashManager.stringToHash(password));
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
         * get the hash of the  password in flash storage
         *
         * @return
         */
        private static String getStoredPasswordHash() {
            SharedPreferences sharedPreferences = MyApplication.getSharePreferences();
            //return HashManager.stringToHash(sharedPreferences.getString(KEY_PASSWORD, ""));

            return sharedPreferences.getString(KEY_PASSWORD, "");
        }

        /**
         * set the password in flash storage , used to encrypt all notes
         *
         * @param password
         */
        public static void setStoredPassword(String password) {
            String hashedPassword = HashManager.stringToHash(password);

            SharedPreferences sharedPreferences = MyApplication.getSharePreferences();
            sharedPreferences.edit().putString(KEY_PASSWORD, hashedPassword).apply();
        }
//    TODO implement password change functionality and password set against the db

    }

}
