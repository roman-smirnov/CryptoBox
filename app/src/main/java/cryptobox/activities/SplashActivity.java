package cryptobox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cryptobox.R;
import cryptobox.contracts.SplashContract;
import cryptobox.models.SplashModel;
import cryptobox.presenters.SplashPresenter;
import cryptobox.utils.MyApplication;

import com.apkfuns.logutils.LogUtils;
import com.testfairy.TestFairy;

import javax.inject.Inject;

/**
 * Launcher activity
 * This activity is the first one to be opened when the app is launched
 * It exists solely to display the CryptoBox logo while the app is loading
 */
public class SplashActivity extends AppCompatActivity implements SplashContract.View {

    @Inject
    SplashContract.Presenter mPresenter;

    /* the logo is displayed through the theme-drawable (check out the manifest + styles + layout file )*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TestFairy.begin(this, "1c9397bfafb2e3dd448ebf232049b937c7af0eb9");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TestFairy.addEvent("Start Session.");

        ((MyApplication) getApplication()).getComponent().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.setView(this);
        mPresenter.start();
    }

    /**
     * open the LoginActivity and prevent from going back to splash screen
     */
    @Override
    public void showLoginActivity() {
        LogUtils.d("showLoginActivity()");
        //launch the login activity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        //kill the activity to prevent user from going back to the splash screen
        finish();
    }

    /**
     * open the ExplainActivity and prevent from going back to splash screen
     */
    @Override
    public void showExplainActivity() {
        LogUtils.d("showExplainActivity()");
        //launch the Explain activity
        Intent intent = new Intent(this, ExplainActivity.class);
        startActivity(intent);
        //kill the activity to prevent user from going back to the splash screen
        finish();
    }
}
