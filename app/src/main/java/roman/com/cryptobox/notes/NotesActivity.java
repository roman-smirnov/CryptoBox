package roman.com.cryptobox.notes;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import roman.com.cryptobox.EditorActivity;
import roman.com.cryptobox.R;
import roman.com.cryptobox.fileutils.MockNoteGenerator;
import roman.com.cryptobox.notes.recyclerview.DividerItemDecoration;
import roman.com.cryptobox.notes.recyclerview.Note;
import roman.com.cryptobox.notes.recyclerview.NotesAdapter;
import roman.com.cryptobox.notes.recyclerview.RecyclerTouchListener;

public class NotesActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Note> mNoteList;
    private NotesAdapter mNotesAdapter;
    private FloatingActionButton mFloatingActionButton;

    private MockNoteGenerator mMockNoteGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.notes_toolbar);
        setSupportActionBar(toolbar);

        mMockNoteGenerator = MockNoteGenerator.getInstance();
        mNoteList = mMockNoteGenerator.getNotesList();

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                goToEditorActivity();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mNotesAdapter = new NotesAdapter(mNoteList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        add the seperator decoration between recyclerview list items
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(mNotesAdapter);


        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Note note = mNoteList.get(position);
                goToEditorActivity(note.getId());
                Toast.makeText(getApplicationContext(), note.getTitle() + " is clicked!" + " and it's id is " + note.getId()
                        , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Note note = mNoteList.get(position);
                Toast.makeText(getApplicationContext(), note.getTitle() + " is long clicked!", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    /**
     * create new note
     */
    private void goToEditorActivity() {
        //launch the editor activity
        Intent intent = new Intent(this, EditorActivity.class);
        startActivity(intent);
        return;
    }

    /**
     * edit an existing note
     * @param id
     */
    private void goToEditorActivity(int id) {
        //launch the editor activity
        Intent intent = new Intent(this, EditorActivity.class);
        intent.putExtra(MockNoteGenerator.NOTE_ID_KEY_STRING, id);
        startActivity(intent);
        return;
    }
}
