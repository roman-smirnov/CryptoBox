package roman.com.cryptobox.notes;

import android.content.Context;
import android.content.SharedPreferences;

import roman.com.cryptobox.mainapplication.MyApplication;

/**
 * Created by roman on 10/8/16.
 */

public class PasswordManager {
    private Context mContext;

    public PasswordManager() {
        mContext = MyApplication.getContext();
    }

    public void setPassword(String password) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("cryptobox", Context.MODE_PRIVATE);

    }
}
