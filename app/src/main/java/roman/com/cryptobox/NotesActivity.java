package roman.com.cryptobox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import roman.com.cryptobox.filehandling.FileManager;

public class NotesActivity extends AppCompatActivity {

    private EditText mTitleEditText;
    private EditText mContentEditText;
    private Button mWriteButton;

    private FileManager mFileManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        mTitleEditText = (EditText) findViewById(R.id.title_edit_text);
        mContentEditText = (EditText) findViewById(R.id.content_edit_text);
        mWriteButton = (Button) findViewById(R.id.write_button);

        mFileManager = new FileManager();

        mWriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFileManager.getRsaKeyPair();
                Toast.makeText(NotesActivity.this, "write_button was pressed", Toast.LENGTH_SHORT).show();

                String fileName = mTitleEditText.getText().toString();
                String fileContent = mContentEditText.getText().toString();
                mFileManager.writeUnencryptedFile(fileName,fileContent);
            }
        });


    }
}
