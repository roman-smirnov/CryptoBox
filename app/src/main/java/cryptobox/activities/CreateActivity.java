package cryptobox.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import cryptobox.R;
import cryptobox.fragments.CreateFragment;

public class CreateActivity extends AppCompatActivity {
    private static final String KEY_CREATE_FRAGMENT = "CREATE_FRAGMENT";
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.create_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Password Set Up");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // get the fragment if it already exists
        mFragment = (CreateFragment) getSupportFragmentManager().findFragmentByTag(KEY_CREATE_FRAGMENT);
        // add the fragment if not yet added
        if (mFragment == null) {
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            CreateFragment createFragment = new CreateFragment();
            fragmentTransaction.replace(R.id.activity_create_fragment_container, createFragment, KEY_CREATE_FRAGMENT);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (mFragment != null) {
            ((CreateFragment) mFragment).onBackPressed();
        }
    }
}
