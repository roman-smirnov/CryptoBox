package cryptobox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cryptobox.R;
import cryptobox.contracts.SplashContract;
import cryptobox.presenters.SplashPresenter;

/**
 * Launcher activity
 * This activity is the first one to be opened when the app is launched
 * It exists solely to display the EchoPark logo while the app is loading
 */
public class SplashActivity extends AppCompatActivity implements SplashContract.View {

    private SplashContract.Presenter mPresenter;

    /* the logo is displayed through the theme-drawable (check out the manifest + stles + layout file )*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mPresenter = new SplashPresenter(this);
        mPresenter.start();
    }

    /**
     * open the LoginActivity and prevent from going back to splash screen
     */
    @Override
    public void showLoginActivity() {
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
        //launch the Explain activity
        Intent intent = new Intent(this, ExplainActivity.class);
        startActivity(intent);
        //kill the activity to prevent user from going back to the splash screen
        finish();
    }
}
