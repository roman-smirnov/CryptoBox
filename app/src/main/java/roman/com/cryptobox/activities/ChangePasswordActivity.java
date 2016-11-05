package roman.com.cryptobox.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import roman.com.cryptobox.R;
import roman.com.cryptobox.contracts.ChangePasswordContract;
import roman.com.cryptobox.presenters.ChangePasswordPresenter;

/**
 * Created by roman on 11/5/16.
 */

public class ChangePasswordActivity extends AppCompatActivity implements ChangePasswordContract.View {

    private static final int MAX_PROGRESS_BAR = 10;

    private ChangePasswordContract.Presenter mPresenter;

    private TextInputLayout mCurrentPasswordTextInputLayout;
    private EditText mCurrentPasswordEditText;

    private TextInputLayout mNewPasswordTextInputLayout;
    private EditText mNewPasswordEditText;

    private TextView mPasswordStrengthTextView;
    private ProgressBar mPasswordStrengthProgressbar;

    private Button mOkButton;
    
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
//        TODO move this to String resources

        mPresenter = new ChangePasswordPresenter(this);

        //set the toolbar for this activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_change_password_toolbar);
        setSupportActionBar(toolbar);

//        getSupportActionBar().setTitle("Change Password");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCurrentPasswordTextInputLayout = (TextInputLayout) findViewById(R.id.activity_change_password_current_password_textinputlayout);
        mNewPasswordTextInputLayout = (TextInputLayout) findViewById(R.id.activity_change_password_new_password_textinputlayout);

        mCurrentPasswordEditText = (EditText) findViewById(R.id.activity_change_password_current_password_edittext);
        mNewPasswordEditText = (EditText) findViewById(R.id.activity_change_password_new_password_edittext);

        mPasswordStrengthTextView = (TextView) findViewById(R.id.activity_change_password_password_strength_textview);

        mPasswordStrengthProgressbar = (ProgressBar) findViewById(R.id.activity_change_password_password_strength_progressbar);
        mPasswordStrengthProgressbar.setMax(MAX_PROGRESS_BAR);
        mPasswordStrengthTextView.setText(getResources().getString(R.string.time_to_crack, " "));

        mOkButton = (Button) findViewById(R.id.activity_change_password_ok_button);

        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.userClickedOk();
            }
        });
    }

    @Override
    public void showCurrentPasswordBad() {

    }

    @Override
    public void hideCurrentPasswordBad() {

    }

    @Override
    public void showInputCurrentPassword() {

    }

    @Override
    public void hideInputCurrentPassword() {

    }

    @Override
    public void showInputNewPassword() {

    }

    @Override
    public void hideInputNewPassword() {

    }

    @Override
    public void showInputRepeatNewPassword() {

    }

    @Override
    public void hideInputRepeatNewPassword() {

    }

    @Override
    public void showPassWordChanged() {

    }

    @Override
    public void showPasswordStrength(int passwordStrength, String passwordStrengthDescription) {
        mPasswordStrengthProgressbar.setProgress(passwordStrength);
        mPasswordStrengthTextView.setText(passwordStrengthDescription);
        mPasswordStrengthTextView.setVisibility(View.VISIBLE);
        mPasswordStrengthProgressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePasswordStrength() {
        mPasswordStrengthTextView.setVisibility(View.GONE);
        mPasswordStrengthProgressbar.setVisibility(View.GONE);
    }

    @Override
    public void showNotesActivity() {
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        mPresenter.userClickedBack();
    }


}
