package cryptobox.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import cryptobox.R;
import cryptobox.fragments.ChangePasswordFragment;
import cryptobox.listeners.PopBackStackListner;


public class ChangePasswordActivity extends AppCompatActivity implements PopBackStackListner {

    private static final String KEY_CHANGE_PASSWORD_FRAGMENT = "CHANGE_PASSWORD_FRAGMENT";
    private Fragment mFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        //set the toolbar for this activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_change_password_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get the fragment if it already exists
        mFragment = (ChangePasswordFragment) getSupportFragmentManager().findFragmentByTag(KEY_CHANGE_PASSWORD_FRAGMENT);
        // add the fragment if not yet added
        if (mFragment == null) {
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
            fragmentTransaction.replace(R.id.activity_change_password_fragment_container, changePasswordFragment, KEY_CHANGE_PASSWORD_FRAGMENT);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        ((ChangePasswordFragment) mFragment).onBackPressed();
    }

    @Override
    public void popBackStack() {
        super.onBackPressed();
    }
}
