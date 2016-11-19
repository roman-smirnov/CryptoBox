package cryptobox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cryptobox.R;

public class ExplainActivity extends AppCompatActivity {

    private Button mCreateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain);
        mCreateButton = (Button) findViewById(R.id.button_create);
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCreateActivity();
            }
        });
    }

    /**
     * go to the CreateActivity - the one in which a virgin user selects a password
     */
    private void goToCreateActivity() {
        //launch the permissions activity
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
        finish();
    }
}