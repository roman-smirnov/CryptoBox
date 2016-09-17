package roman.com.cryptobox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Launcher activity
 * This activity is the first one to be opened when the app is launched
 * It exists solely to display the EchoPark logo while the app is loading
 */
public class SplashActivity extends AppCompatActivity {

    /* the logo is displayed through the theme-drawable (check out the manifest + stles + layout file )*/
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // once app is loaded, load the permissions activity
        goToNextActivity();
    }

    /**
     * this one will open the next activity and prevent from going back to splash screen
     */
    private void goToNextActivity(){
        //launch the permissions activity
        Intent intent = new Intent(this,PermissionsActivity.class);
        startActivity(intent);
        //kill the activity to prevent user from going back to the splash screen
        finish();
        return;
    }

}
