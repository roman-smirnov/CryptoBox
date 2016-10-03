package roman.com.cryptobox.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import roman.com.cryptobox.fileutils.FileHandler;
import roman.com.cryptobox.notes.NotesActivity;
import roman.com.cryptobox.R;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{

    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;
    private final static String RUN_NUMBER = "RUN_NUMBER";
    private Button mSkipButton;


    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.

        mSkipButton = (Button) findViewById(R.id.button_skip);
        mSkipButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextActivity();
            }
        });
        mUsernameView = (EditText) findViewById(R.id.user_name);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        handleDbInit();
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String userName = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address
        if (TextUtils.isEmpty(userName)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        } else if (!isUsernameValid(userName)) {
            mUsernameView.setError(getString(R.string.error_invalid_email));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            if (password.equals(mSharedPreferences.getString(userName,null))){
                Toast.makeText(LoginActivity.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                goToNextActivity();

            }else{
                Toast.makeText(LoginActivity.this, "WRONG LOGIN CREDENTIALS", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void handleDbInit(){
        mSharedPreferences = getSharedPreferences("cryptobox", Context.MODE_PRIVATE);
        if(isFirstRun()){
            initNotesList();
        }
        incrementRunCounter();
    }

    private void initNotesList() {
        FileHandler noteHandler = new FileHandler(this);
        for (int i = 0; i < 50; i++) {
            noteHandler.writeNote("title" + i, "blablabla" + i);
        }
    }

    private boolean isFirstRun() {
        if(mSharedPreferences.getInt(RUN_NUMBER,0)==0){
            return true;
        }
        return false;
    }

    private void incrementRunCounter(){
        mSharedPreferences.edit().putInt(RUN_NUMBER, mSharedPreferences.getInt(RUN_NUMBER,0)+1).commit();
    }

    private boolean isUsernameValid(String userName) {
        //TODO: Replace this with your own logic
        return userName.length()>1 && userName.length() < 16;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 1;
    }

    private void goToNextActivity(){
        //launch the permissions activity
        Intent intent = new Intent(this, NotesActivity.class);
        startActivity(intent);
        //kill the activity to prevent user from going back to the splash screen
        finish();
        return;
    }
}

