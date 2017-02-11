package cryptobox.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.apkfuns.logutils.LogUtils;

import cryptobox.R;
import cryptobox.contracts.ExplainContract;
import cryptobox.fragments.CreateFragment;
import cryptobox.fragments.ExplainFragment;
import cryptobox.presenters.ExplainPresenter;

public class ExplainActivity extends AppCompatActivity implements ExplainContract.View {

    private static final String KEY_EXPLAIN_FRAGMENT = "EXPLAIN_FRAGMENT";
    private static final String KEY_CREATE_FRAGMENT = "CREATE_FRAGMENT";
    private ExplainContract.Presenter mPresenter;
    private Fragment mExplainFragment;
    private Fragment mCreateFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain);
        boolean hasTwoPanes = getResources().getBoolean(R.bool.has_two_panes);
        mPresenter = new ExplainPresenter(this);
        mPresenter.start(hasTwoPanes);

    }

    @Override
    public void loadTwoPaneFragments() {
        LogUtils.d("loadTwoPaneFragments");
        // get the fragments if they already exist
        mExplainFragment = (ExplainFragment) getSupportFragmentManager().findFragmentByTag(KEY_EXPLAIN_FRAGMENT);
        mCreateFragment = (CreateFragment) getSupportFragmentManager().findFragmentByTag(KEY_CREATE_FRAGMENT);

        // add the first fragment if not yet added
        if (mExplainFragment == null) {
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            mExplainFragment = new ExplainFragment();
            fragmentTransaction.replace(R.id.activity_explain_first_fragment_container, mExplainFragment, KEY_EXPLAIN_FRAGMENT);
            fragmentTransaction.commit();
        }
        // add the second fragment if not yet added
        if (mCreateFragment == null) {
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            mCreateFragment = new CreateFragment();
            fragmentTransaction.replace(R.id.activity_explain_second_fragment_container, mCreateFragment, KEY_CREATE_FRAGMENT);
            fragmentTransaction.commit();
        }
    }

    //      if it's a one pane view (i.e phone screen)
    @Override
    public void loadSingleFragment() {
        LogUtils.d("loadSingleFragment()");
        // get the fragment if it already exists
        mExplainFragment = (ExplainFragment) getSupportFragmentManager().findFragmentByTag(KEY_EXPLAIN_FRAGMENT);
        // add the fragment if not yet added
        if (mExplainFragment == null) {
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            mExplainFragment = new ExplainFragment();
            fragmentTransaction.replace(R.id.activity_explain_fragment_container, mExplainFragment, KEY_EXPLAIN_FRAGMENT);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_explain_two_pane_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Password Set Up");
        }
    }
}