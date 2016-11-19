package cryptobox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import cryptobox.R;
import cryptobox.contracts.CreateContract;
import cryptobox.presenters.CreatePresenter;
import cryptobox.utils.PasswordHandler;

public class CreateActivity extends AppCompatActivity implements CreateContract.View, TextWatcher {


    private CreateContract.Presenter mPresenter;
    //maximum score for progress bar
    private final static int MAX_PROGRESS_BAR = 10;
    private Button mButton;
    private TextInputLayout mPasswordTextInputLayout;
    private EditText mPasswordEditText;
    private TextView mPasswordStrengthTextView;
    private ProgressBar mPasswordStrengthProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        mPresenter = new CreatePresenter(this);

        mButton = (Button) findViewById(R.id.activity_create_button);
        Toolbar toolbar = (Toolbar) findViewById(R.id.create_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Password Set Up");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mPasswordTextInputLayout = (TextInputLayout) findViewById(R.id.activity_create_textinputlayout);
        mPasswordEditText = (EditText) findViewById(R.id.activity_create_edittext);
        mPasswordStrengthTextView = (TextView) findViewById(R.id.activity_create_textview);
        mPasswordStrengthProgressbar = (ProgressBar) findViewById(R.id.activity_create_progressbar);
        mPasswordStrengthProgressbar.setMax(MAX_PROGRESS_BAR);
//        mPasswordStrengthTextView.setText(getResources().getString(R.string.password_strength, " "));

        mPasswordEditText.addTextChangedListener(this);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = mPasswordEditText.getText().toString();
                if (!password.isEmpty()) {
                    PasswordHandler.setStoredPassword(password);
                    PasswordHandler.setSessionPassword((password));
                    gotToNotesActivity();
                }
                //????
            }
        });

        mPresenter.start();
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

    @Override
    public void showInputNewPassword() {
        getSupportActionBar().setTitle(getString(R.string.new_password_title));
        mPasswordTextInputLayout.setHint(getString(R.string.new_password_hint));
    }

    @Override
    public void showInputRepeatNewPassword() {
        getSupportActionBar().setTitle(getString(R.string.repeat_password_title));
        mPasswordTextInputLayout.setHint(getString(R.string.repeat_password_hint));
    }

    @Override
    public void showPasswordStrength(int passwordStrength, @NonNull String passwordStrengthDescription) {
        mPasswordStrengthProgressbar.setProgress(passwordStrength);
        mPasswordStrengthTextView.setText(getString(R.string.password_strength, passwordStrengthDescription));
    }

    @Override
    public void hidePasswordStrength() {
        mPasswordStrengthProgressbar.setVisibility(View.GONE);
        mPasswordStrengthTextView.setVisibility(View.GONE);
    }

    @Override
    public void showNotesActivity() {
        Intent intent = new Intent(this, NotesActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //do nothing
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //do nothing
    }

    @Override
    public void afterTextChanged(Editable s) {
        mPresenter.passwordChanged(mPasswordEditText.getText().toString());
    }
}
