package cryptobox.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.apkfuns.logutils.LogUtils;

import cryptobox.R;
import cryptobox.fragments.EditorFragment;
import cryptobox.listeners.PopBackStackListner;

/**
 * a view activity that allows the user to create and edit notes
 */
public class EditorActivity extends AppCompatActivity implements PopBackStackListner {

    private static final String KEY_EDITOR_FRAGMENT = "EDITOR_FRAGMENT";
    private Fragment mFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_editor_toolbar);
        setSupportActionBar(toolbar);
        //add the back arrow to the actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get the fragment if it already exists
        mFragment = (EditorFragment) getSupportFragmentManager().findFragmentByTag(KEY_EDITOR_FRAGMENT);
        // add the fragment if not yet added
        if (mFragment == null) {
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            mFragment = new EditorFragment();
            fragmentTransaction.replace(R.id.activity_editor_fragment_container, mFragment, KEY_EDITOR_FRAGMENT);
            fragmentTransaction.commit();
        }
    }
    @Override
    public void onBackPressed() {
        if (mFragment != null) {
            ((EditorFragment) mFragment).onBackPressed();
        }
    }

    @Override
    public void popBackStack() {
        LogUtils.d("finish editor activity");
        finish();
    }
}

