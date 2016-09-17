package roman.com.cryptobox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import roman.com.cryptobox.database.DatabaseHandler;
import roman.com.cryptobox.database.User;
import roman.com.cryptobox.filehandling.FileManager;

public class NotesActivity extends AppCompatActivity {

    private EditText mTitleEditText;
    private EditText mContentEditText;
    private Button mWriteButton;
    private Button mReadButton;

    private FileManager mFileManager;

    private DatabaseHandler mDatabaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        mTitleEditText = (EditText) findViewById(R.id.title_edit_text);
        mContentEditText = (EditText) findViewById(R.id.content_edit_text);
        mWriteButton = (Button) findViewById(R.id.write_button);
        mReadButton = (Button) findViewById(R.id.read_button);

        mFileManager = new FileManager();

        mDatabaseHandler = new DatabaseHandler(this);

        mWriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /// roman was here
                Toast.makeText(NotesActivity.this, "write_button was pressed", Toast.LENGTH_SHORT).show();

                mDatabaseHandler.addUser(new User("Roman@123.com", "12345678"));
                mDatabaseHandler.addUser(new User("Lippner@123.com", "853453252352523"));
            }
        });

        mReadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for( User user : mDatabaseHandler.getAllUsers()){
                    mContentEditText.append(user.getUserName()+" "+user.getPassword());
                    mContentEditText.append("\r\n");

                }
            }
        });




    }
}
