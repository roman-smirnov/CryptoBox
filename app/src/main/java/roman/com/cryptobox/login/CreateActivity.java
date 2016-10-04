package roman.com.cryptobox.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.apkfuns.logutils.LogUtils;
import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;


import roman.com.cryptobox.R;

public class CreateActivity extends AppCompatActivity {
    private Button mCreateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Zxcvbn zxcvbn = new Zxcvbn();
        Strength strength = zxcvbn.measure("12345");
        LogUtils.d(strength.getCrackTimesDisplay().getOfflineFastHashing1e10PerSecond());
    }
}
