package cryptobox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.testfairy.TestFairy;

import cryptobox.R;
import cryptobox.contracts.LoginContract;
import cryptobox.presenters.LoginPresenter;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoginContract.View {


    //the presenter(logic module)
    private LoginContract.Presenter mPresenter;

    // UI references.
    private EditText mPasswordEditText;
    private TextInputLayout mTextInputLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mPresenter = new LoginPresenter(this);

        //get the views
        mPasswordEditText = (EditText) findViewById(R.id.edittext_password_login);
        mTextInputLayout = (TextInputLayout) findViewById(R.id.textinputlayout_password);
    }


    /**
     * a listener method for the login button click event
     *
     * @param view the login button
     */
    public void onClickLoginButton(View view) {
        //call the verification logic on the presenter
        mPresenter.loginButtonClicked(mPasswordEditText.getText().toString());
    }

    /**
     * go to NotesActivity - the ones with the list view of all notes
     */
    @Override
    public void showNotesActivity() {
        //launch the notes activity
        TestFairy.addEvent("Logged in successfully.");
        Intent intent = new Intent(this, NotesActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * show user an error demonstration that the password was incorrect
     */
    @Override
    public void showPasswordBad() {
        mTextInputLayout.setError(getString(R.string.error_incorrect_password));
        mPasswordEditText.requestFocus();
    }

}

