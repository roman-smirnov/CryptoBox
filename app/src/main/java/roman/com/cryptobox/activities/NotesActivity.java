package roman.com.cryptobox.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import roman.com.cryptobox.R;
import roman.com.cryptobox.adapters.NotesAdapter;
import roman.com.cryptobox.contracts.NotesContract;
import roman.com.cryptobox.dataobjects.MockNote;
import roman.com.cryptobox.listeners.RecyclerTouchListener;
import roman.com.cryptobox.presenters.NotesPresenter;
import roman.com.cryptobox.utils.DividerItemDecoration;

public class NotesActivity extends AppCompatActivity implements NotesContract.View, RecyclerTouchListener.ClickListener {

    private RecyclerView mRecyclerView;
    private NotesAdapter mNotesAdapter;
    private FloatingActionButton mFloatingActionButton;
    private NotesContract.Presenter mPresenter;
    private MenuItem mMenuItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.notes_toolbar);
        setSupportActionBar(toolbar);

        mPresenter = new NotesPresenter(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

//        add the seperator decoration between recyclerview list items
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        //set the adapter with an empty list
        mNotesAdapter = new NotesAdapter(new ArrayList<MockNote>(0));
        mRecyclerView.setAdapter(mNotesAdapter);

        //touch events will be called on 'this'
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, this));

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.addNewNote();
            }
        });

        mPresenter.loadNotes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_notes_menu, menu);
        mMenuItem = menu.findItem(R.id.activity_notes_delete_icon);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO delete the selected items when trashcan is selected

        return true;
    }

    @Override
    public void onBackPressed() {
        mPresenter.clearNoteDeleteList();
    }

    /**
     * show a list of notes
     *
     * @param noteList
     */
    @Override
    public void showNotes(@NonNull List<MockNote> noteList) {
        mNotesAdapter.replaceData(noteList);
    }

    /**
     * take the user to the editor activity to edit en existing note
     * @param note an existing note
     */
    @Override
    public void showNoteDetail(@NonNull MockNote note) {
        //launch the editor activity
        Intent intent = new Intent(this, EditorActivity.class);
        intent.putExtra(MockNote.NOTE_KEY_STRING, note.getId());
        startActivity(intent);
    }

    /**
     * a list item was clicked
     * @param view
     * @param position
     */
    @Override
    public void onClick(View view, int position) {
        mPresenter.openNoteDetails(mNotesAdapter.getItem(position));
    }

    /**
     * a list item was long clicked
     * @param view
     * @param position
     */
    @Override
    public void onLongClick(View view, int position) {
        view.setBackground(getDrawable(R.drawable.bg_edit_text));
        mPresenter.addOrRemoveToNoteDeleteList(mNotesAdapter.getItem(position));
    }

    /**
     * take the user to the editor activity to create a new note
     */
    @Override
    public void showAddNewNote() {
        //launch the editor activity
        Intent intent = new Intent(this, EditorActivity.class);
        startActivity(intent);
    }


    /**
     * uncheck all the notes that were selected for delete
     */
    @Override
    public void uncheckSelectedNotes(List<MockNote> noteDeleteList) {
        mMenuItem.setVisible(false);
        //de-mark all the to-be-deleted note list items
        for (MockNote note : noteDeleteList) {
            uncheckNote(note);
        }
    }

    @Override
    public void uncheckNote(MockNote note) {
        View view = mRecyclerView.getLayoutManager().findViewByPosition(mNotesAdapter.getPosition(note));
        view.setBackgroundColor(Color.TRANSPARENT);
    }

//    private void (){
//        //hide the trashcan icon
//    }
}
