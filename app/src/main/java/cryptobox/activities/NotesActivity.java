package cryptobox.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.apkfuns.logutils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import cryptobox.R;
import cryptobox.adapters.NotesAdapter;
import cryptobox.contracts.NotesContract;
import cryptobox.database.DataLoader;
import cryptobox.dataobjects.Note;
import cryptobox.listeners.RecyclerTouchListener;
import cryptobox.presenters.NotesPresenter;
import cryptobox.utils.DividerItemDecoration;

import static com.google.common.base.Preconditions.checkNotNull;

public class NotesActivity extends AppCompatActivity implements NotesContract.View, RecyclerTouchListener.ClickListener {

    //save stuff onSaveInstanceState with this key
    private static final String KEY_SAVE_INSTANCE_STATE = "SAVE_INSTANCE_STATE";

    private RecyclerView mRecyclerView;
    private NotesAdapter mNotesAdapter;
    private FloatingActionButton mFloatingActionButton;
    private NotesContract.PresenterContract mPresenter;
    private MenuItem mMenuItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.notes_toolbar);
        setSupportActionBar(toolbar);

        mPresenter = new NotesPresenter(this, DataLoader.getInstance());

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

//        add the seperator decoration between recyclerview list items
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        //set the adapter with an empty list
        mNotesAdapter = new NotesAdapter(new ArrayList<Note>(0));
        mRecyclerView.setAdapter(mNotesAdapter);

        //touch events will be called on 'this'
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, this));

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.userClickedOnFab();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        //tell the presenter to load the initial data
        mPresenter.start();
    }

    //    TODO implement saving of checked notes on screen rotation etc
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        int[] noteIdArray = mPresenter.getCheckedNotes();
//        if (noteIdArray != null) {
//            outState.putIntArray(KEY_SAVE_INSTANCE_STATE, noteIdArray);
//        }
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_notes_menu, menu);
        mMenuItem = menu.findItem(R.id.activity_notes_delete_icon);
        hideTrashCan();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LogUtils.d(item.getItemId());
        switch (item.getItemId()) {
            //        user clicked the back button
            case android.R.id.home:
                LogUtils.d("back arrow clicked");
                onBackPressed();
                return true;
            //        user clicked the trashcan button
            case R.id.activity_notes_delete_icon:
                mPresenter.userClickedOnTrashCan();
                return true;
            //      user clicked on change password settings nption
            case R.id.activity_notes_settings_change_password:
                mPresenter.userClickedOnChangePassword();
                return true;
            // user clicked on the about settings option
            case R.id.activity_notes_settings_about:
                mPresenter.userClickedOnAbout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        mPresenter.userPressedBackButton();
    }

    /**
     * show a list of notes
     * @param noteList
     */
    @Override
    public void showNotes(@NonNull List<Note> noteList) {
        mNotesAdapter.replaceData(noteList);
    }

    /**
     * take the user to the editor activity to edit en existing note
     * @param note an existing note
     */
    @Override
    public void showNoteDetail(@NonNull Note note) {
        //launch the editor activity
        Intent intent = new Intent(this, EditorActivity.class);
        intent.putExtra(Note.NOTE_KEY_STRING, note.getId());
        startActivity(intent);
    }

    /**
     * a list item was clicked
     * @param view
     * @param position
     */
    @Override
    public void onClick(View view, int position) {
        mPresenter.userClickedOnNote(mNotesAdapter.getItem(position));
    }

    /**
     * a list item was long clicked
     * @param view
     * @param position
     */
    @Override
    public void onLongClick(View view, int position) {
        mPresenter.userLongClickedOnNote(mNotesAdapter.getItem(position));
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
    public void uncheckSelectedNotes(List<Note> noteDeleteList) {
        //de-mark all the to-be-deleted note list items
        for (Note note : noteDeleteList) {
            showNoteUnchecked(note);
        }
    }



    /**
     * show the trashcan
     */
    @Override
    public void showTrashCan() {
        mMenuItem.setVisible(true);
    }

    @Override
    public void hideTrashCan() {
        mMenuItem.setVisible(false);
    }

    @Override
    public void showBackArrow() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void hideBackArrow() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

    }

    @Override
    public void exitApp() {
        super.onBackPressed();
    }

    /**
     * show a delete confirmation dialog
     */
    @Override
    public void showConfirmDeleteDialog() {
        new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AppCompatAlertDialogStyle))
                .setTitle("Delete Items")
                .setMessage("Are you sure you want to delete the selected items?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.userClickedConfirmDelete();
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do  nothing
            }
        })
                .setIcon(R.drawable.cloud_logo)
                .show();
    }

    /**
     * show the settings/changePassword activity
     */
    @Override
    public void showSettings() {
        //launch the settings activity
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    /**
     * show the ABOUT screen
     */
    @Override
    public void showAbout() {
        //launch the about activity
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }


    /**
     * hide the placeholder explanations when there are notes to laod
     */
    @Override
    public void showPlaceholder() {
        //we need to get these to display or hide the placeholder / recyclerview
        View content = (View) findViewById(R.id.activity_notes_content_container);
        View placeholder = (View) findViewById(R.id.activity_notes_placeholder_container);

        //hide the recycler view and show the placeholder
        content.setVisibility(View.GONE);
        placeholder.setVisibility(View.VISIBLE);
    }

    /**
     * show a placeholder explanation when no notes are selected
     */
    @Override
    public void hidePlaceholder() {
        //we need to get these to display or hide the placeholder / recyclerview
        View content = (View) findViewById(R.id.activity_notes_content_container);
        View placeholder = (View) findViewById(R.id.activity_notes_placeholder_container);

        //show the recycler view and hide the placeholder
        content.setVisibility(View.VISIBLE);
        placeholder.setVisibility(View.GONE);
    }

    /**
     * show the fab again after exiting the multiple selection mode
     */
    @Override
    public void showFab() {
        mFloatingActionButton.setVisibility(View.VISIBLE);
    }

    /**
     * hide the floating action button when selecting multiple items for deletion
     */
    @Override
    public void hideFab() {
        mFloatingActionButton.setVisibility(View.GONE);
    }


    /**
     * make all the checkboxes visible
     */
    @Override
    public void showCheckBoxes() {
//         all recycled notes will have the checkbox enabled on bind view
        mNotesAdapter.showCheckBoxsOnBind();
    }

    /**
     * hide all the checkboxes, including the checked ones
     */
    @Override
    public void hideCheckBoxes() {
//         all recycled notes will have the checkbox disabled on bind view
        mNotesAdapter.hideCheckBoxsOnBind();
    }

    /**
     * uncheck a specific note
     *
     * @param note
     */
    @Override
    public void showNoteUnchecked(@NonNull Note note) {
        checkNotNull(note);
        mNotesAdapter.hideNoteChecked(mNotesAdapter.getPosition(note));
    }

    /**
     * check a specific note
     *
     * @param note
     */
    @Override
    public void showNoteChecked(@NonNull Note note) {
        checkNotNull(note);
        mNotesAdapter.showNoteChecked(mNotesAdapter.getPosition(note));
    }
}
