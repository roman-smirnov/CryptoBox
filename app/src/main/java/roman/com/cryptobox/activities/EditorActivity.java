package roman.com.cryptobox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import roman.com.cryptobox.R;
import roman.com.cryptobox.dataobjects.MockNote;

public class EditorActivity extends AppCompatActivity {

    private EditText mDateEditText;
    private EditText mTitleEditText;
    private EditText mContentEditText;
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

        //get the views
        mDateEditText = (EditText) findViewById(R.id.activity_editor_note_date);
        mTitleEditText = (EditText) findViewById(R.id.activity_editor_note_title);
        mContentEditText = (EditText) findViewById(R.id.activity_editor_note_content);
        mDateEditText.setText(mNote.getLastModified());

        //set the text to the views
        mDateEditText.setText(mNote.getLastModified());
        mTitleEditText.setText(mNote.getTitle());
        mContentEditText.setText(mNote.getContent());

    }

    @Override
    protected void onStop() {
        super.onStop();

        //save the changes
        mNote.setLastModified(mDateEditText.getText().toString());
        mNote.setTitle(mTitleEditText.getText().toString());
        mNote.setContent(mContentEditText.getText().toString());
    }
}
