package roman.com.cryptobox.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import roman.com.cryptobox.R;
import roman.com.cryptobox.password.PassHolder;
import roman.com.cryptobox.password.PasswordManager;


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

    /**
     * a listener method for the login button click event
     * @param view the login button
     */
    public void onClickLoginButton(View view) {
        if (attemptLogin()) {
            gotToNotesActivity();
            // don't
            finish();
        } else {
            //login unsuccessful
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private boolean attemptLogin() {
        // Reset errors.
        mTextInputLayout.setError(null);

        // Store values at the time of the login attempt.
        String password = mPasswordView.getText().toString();

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mTextInputLayout.setError(getString(R.string.error_incorrect_password));
            mPasswordView.requestFocus();
            return false;
        }
        //set the password to decrypt all notes
        PassHolder.mPassword = password;
        return true;
    }

    /**
     * checks if the input password's hash is the same as the stored password's hash
     * @param password the input password
     * @return true if they're equal, false otherwise
     */
    private boolean isPasswordValid(String password) {
        String passwordHash = PasswordManager.getPasswordHash(password);
        return PasswordManager.getStoredPasswordHash().equals(passwordHash);
    }
}

