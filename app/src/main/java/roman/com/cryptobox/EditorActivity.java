package roman.com.cryptobox;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import roman.com.cryptobox.fileutils.MockNoteGenerator;

public class EditorActivity extends AppCompatActivity {

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        final Intent intent = getIntent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mEditText = (EditText) findViewById(R.id.edittext_note_content);
        mEditText.setText(MockNoteGenerator.getInstance().getNoteById(intent.getIntExtra(MockNoteGenerator.NOTE_ID_KEY_STRING, -1)));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                MockNoteGenerator.getInstance().setNoteById(intent.getIntExtra(MockNoteGenerator.NOTE_ID_KEY_STRING, -1), mEditText.getText().toString());
                finish();
            }
        });
    }
}
