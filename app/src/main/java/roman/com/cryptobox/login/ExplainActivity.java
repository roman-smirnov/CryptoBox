package roman.com.cryptobox.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import roman.com.cryptobox.R;

/**
 * This activity is the first one to be opened when the app is launched
 * It exists solely to display the EchoPark logo while the app is loading
 */
public class ExplainActivity extends AppCompatActivity {

    /* the logo is displayed through the theme-drawable (check out the manifest + stles + layout file )*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain);
        // once app is loaded, load the permissions activity
    }
}