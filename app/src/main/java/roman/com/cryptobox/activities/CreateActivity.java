package roman.com.cryptobox.activities;

import android.content.Intent;
import android.os.Bundle;
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

import com.apkfuns.logutils.LogUtils;
import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;

import roman.com.cryptobox.R;

public class CreateActivity extends AppCompatActivity {

    //maximum score from Zxcvbn library
    private final static int MAX_PROGRESS_BAR = 4;
    private Button mButton;
    private TextInputLayout mTextInputLayout1;
    private TextInputLayout mTextInputLayout2;
    private EditText mPasswordEditText1;
    private EditText mPasswordEditText2;
    private TextView mPasswordStrengthTextView;
    private ProgressBar mPasswordStrengthProgressbar;
    //password analyzer library
    private Zxcvbn mZxcvbn;
    private boolean mIsPasswordSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        mZxcvbn = new Zxcvbn();
        mButton = (Button) findViewById(R.id.button_create_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.create_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Password Set Up");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mTextInputLayout1 = (TextInputLayout) findViewById(R.id.textinputlayout_password_input1);
        mTextInputLayout2 = (TextInputLayout) findViewById(R.id.textinputlayout_password_input2);
        mPasswordEditText1 = (EditText) findViewById(R.id.edittext_password_input1);
        mPasswordEditText2 = (EditText) findViewById(R.id.edittext_password_input2);
        mPasswordStrengthTextView = (TextView) findViewById(R.id.textview_password_strength);
        mPasswordStrengthProgressbar = (ProgressBar) findViewById(R.id.progressbar_password_strength);
        mPasswordStrengthProgressbar.setMax(MAX_PROGRESS_BAR);
        mPasswordStrengthTextView.setText(getResources().getString(R.string.password_strength, " "));

        mPasswordEditText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Strength strength = mZxcvbn.measure(mPasswordEditText1.getText().toString());
                mPasswordStrengthTextView.setText(getResources().getString(R.string.password_strength, strength.getCrackTimesDisplay().getOfflineFastHashing1e10PerSecond()));
                LogUtils.d(strength.getScore());
                mPasswordStrengthProgressbar.setProgress(strength.getScore());
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsPasswordSet) {
                    gotToNotesActivity();
                }
                mButton.setText("Create");
                getSupportActionBar().setTitle("Confirm Password");
                mTextInputLayout1.setVisibility(TextInputLayout.GONE);
                mTextInputLayout2.setVisibility(TextInputLayout.VISIBLE);
                mPasswordStrengthTextView.setVisibility(TextView.GONE);
                mPasswordStrengthProgressbar.setVisibility(ProgressBar.GONE);
                mTextInputLayout2.requestFocus();

                mIsPasswordSet = true;
            }
        });
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
