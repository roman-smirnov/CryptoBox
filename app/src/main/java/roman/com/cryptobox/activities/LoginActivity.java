package roman.com.cryptobox.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import roman.com.cryptobox.R;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    // UI references.
    private EditText mPasswordView;
    private Button mLoginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//         Set up the login form.

        mLoginButton = (Button) findViewById(R.id.button_login);
        mLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (attemptLogin()) {
                    gotToNotesActivity();
                }
            }
        });

        mPasswordView = (EditText) findViewById(R.id.edittext_password_login);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private boolean attemptLogin() {
        // Reset errors.
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String password = mPasswordView.getText().toString();

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            mPasswordView.requestFocus();
            return false;
        }
        return true;
    }


    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 1;
    }

    /**
     * go to NotesActivity - the ones with the list view of all notes
     */
    private void gotToNotesActivity() {
        //launch the permissions activity
        Intent intent = new Intent(this, NotesActivity.class);
        startActivity(intent);
        return;
    }
}

