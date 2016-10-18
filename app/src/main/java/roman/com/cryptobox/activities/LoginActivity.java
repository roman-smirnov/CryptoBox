package roman.com.cryptobox.activities;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import roman.com.cryptobox.R;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    // UI references.
    private EditText mPasswordView;
    private TextInputLayout mTextInputLayout;
    private Button mLoginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginButton = (Button) findViewById(R.id.button_login);
        mPasswordView = (EditText) findViewById(R.id.edittext_password_login);
        mTextInputLayout = (TextInputLayout) findViewById(R.id.textinputlayout_password);

//        ApiTester tester = new ApiTester();
//        tester.runScripts();
    }

    /**
     * go to NotesActivity - the ones with the list view of all notes
     */
    private void gotToNotesActivity() {
        //launch the permissions activity
        Intent intent = new Intent(this, NotesActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * a listener method for the login button click event
     * @param view the login button
     */
    public void onClickLoginButton(View view) {
        if (attemptLogin()) {
            gotToNotesActivity();
            // don't
        } else {
            //login unsuccessful
        }
    }


}

