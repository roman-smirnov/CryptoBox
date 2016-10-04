package roman.com.cryptobox.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import roman.com.cryptobox.R;
import roman.com.cryptobox.notes.NotesActivity;

public class ExplainActivity extends AppCompatActivity {

    private Button mCreateButton;
    private Button mSkipButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain);
        mCreateButton = (Button) findViewById(R.id.button_create);
        mSkipButton = (Button) findViewById(R.id.button_skip_create);

        mSkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotToNotesActivity();
            }
        });

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCreateActivity();
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

    /**
     * go to the CreateActivity - the one in which a virgin user selects a password
     */
    private void goToCreateActivity() {
        //launch the permissions activity
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
        return;
    }
}