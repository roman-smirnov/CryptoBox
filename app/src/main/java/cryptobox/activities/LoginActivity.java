package cryptobox.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import cryptobox.R;
import cryptobox.fragments.LoginFragment;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String KEY_LOGIN_FRAGMENT = "LOGIN_FRAGMENT";
    private Fragment mFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // get the fragment if it already exists
        mFragment = getSupportFragmentManager().findFragmentByTag(KEY_LOGIN_FRAGMENT);
        // add the fragment if not yet added
        if (mFragment == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            mFragment = new LoginFragment();
            fragmentTransaction.replace(R.id.activity_login_fragment_container, mFragment, KEY_LOGIN_FRAGMENT);
            fragmentTransaction.commit();
        }

    }


}

