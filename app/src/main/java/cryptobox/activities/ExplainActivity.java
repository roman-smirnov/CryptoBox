package cryptobox.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import cryptobox.R;
import cryptobox.fragments.ExplainFragment;

public class ExplainActivity extends AppCompatActivity {

    private static final String KEY_EXPLAIN_FRAGMENT = "EXPLAIN_FRAGMENT";
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain);

        // get the fragment if it already exists
        mFragment = (ExplainFragment) getSupportFragmentManager().findFragmentByTag(KEY_EXPLAIN_FRAGMENT);
        // add the fragment if not yet added
        if (mFragment == null) {
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            mFragment = new ExplainFragment();
            fragmentTransaction.replace(R.id.activity_explain_fragment_container, mFragment, KEY_EXPLAIN_FRAGMENT);
            fragmentTransaction.commit();
        }
    }


}