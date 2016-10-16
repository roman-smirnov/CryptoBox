package roman.com.cryptobox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import roman.com.cryptobox.R;
import roman.com.cryptobox.dataobjects.MockNote;

public class EditorActivity extends AppCompatActivity {

    private EditText mEditText;
    private MockNote mNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        //get the note from the intent
        Intent intent = getIntent();
        mNote = intent.getExtras().getParcelable(MockNote.NOTE_KEY_STRING);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mEditText = (EditText) findViewById(R.id.edittext_note_content);
        mEditText.setText(mNote.getContent());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_editor);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                mNote.setContent(mEditText.getText().toString());
                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mNote.setContent(mEditText.getText().toString());
    }
}
