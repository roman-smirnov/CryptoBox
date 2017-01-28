package cryptobox.activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import cryptobox.R;
import cryptobox.fragments.AboutFragment;

public class AboutActivity extends AppCompatActivity {
    private static final String KEY_ABOUT_FRAGMENT = "ABOUT_FRAGMENT";
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.about_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get the fragment if it already exists
        mFragment = (AboutFragment) getSupportFragmentManager().findFragmentByTag(KEY_ABOUT_FRAGMENT);
        // add the fragment if not yet added
        if (mFragment == null) {
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            AboutFragment aboutFragment = new AboutFragment();
            fragmentTransaction.replace(R.id.activity_about_fragment_container, aboutFragment, KEY_ABOUT_FRAGMENT);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go to parent activity.
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
