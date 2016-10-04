package roman.com.cryptobox.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;


import roman.com.cryptobox.R;

public class CreateActivity extends AppCompatActivity {
    private Button mCreateButton;
    private EditText mPasswordEditText;
    private TextView mPasswordStrengthTextView;
    private ProgressBar mPasswordStrengthProgressbar;
    private Zxcvbn mZxcvbn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        mZxcvbn = new Zxcvbn();
        mCreateButton = (Button) findViewById(R.id.button_create_password);
        mPasswordEditText = (EditText) findViewById(R.id.edittext_password_input1);
        mPasswordStrengthTextView = (TextView) findViewById(R.id.textview_password_strength);
        mPasswordStrengthProgressbar = (ProgressBar) findViewById(R.id.progressbar_password_strength);

        mPasswordStrengthTextView.setText(getResources().getString(R.string.password_strength, " "));

        mPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Strength strength = mZxcvbn.measure(mPasswordEditText.getText().toString());
                mPasswordStrengthTextView.setText(getResources().getString(R.string.password_strength, strength.getCrackTimesDisplay().getOfflineFastHashing1e10PerSecond()));
            }
        });

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CreateActivity.this, "account created", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
