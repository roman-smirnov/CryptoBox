package cryptobox.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.apkfuns.logutils.LogUtils;

import cryptobox.R;
import cryptobox.contracts.NotesActivityContract;
import cryptobox.dataobjects.Note;
import cryptobox.fragments.EditorFragment;
import cryptobox.fragments.NotesFragment;
import cryptobox.listeners.PopBackStackListner;
import cryptobox.presenters.NotesActivityPresenter;

public class NotesActivity extends AppCompatActivity implements NotesActivityContract.View, PopBackStackListner {

    private static final String KEY_NOTES_FRAGMENT = "NOTES_FRAGMENT";
    private static final String KEY_EDITOR_FRAGMENT = "EDITOR_FRAGMENT";
    private Fragment mNotesFragment;
    private Fragment mEditorFragment;
    private NotesActivityContract.Presenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        boolean hasTwoPanes = getResources().getBoolean(R.bool.has_two_panes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_notes_toolbar);
        setSupportActionBar(toolbar);

        mPresenter = new NotesActivityPresenter(this);
        mPresenter.start(hasTwoPanes);
    }

    @Override
    public void onBackPressed() {
        if (mNotesFragment != null) {
            ((NotesFragment) mNotesFragment).onBackPressed();
        }
    }

    @Override
    public void popBackStack() {
        LogUtils.d("popBackStack");
        super.onBackPressed();
    }

    @Override
    public void loadTwoPaneFragments() {
        LogUtils.d("loadTwoPaneFragments");
        // get the fragments if they already exist
        mNotesFragment = (NotesFragment) getSupportFragmentManager().findFragmentByTag(KEY_NOTES_FRAGMENT);
        mEditorFragment = (EditorFragment) getSupportFragmentManager().findFragmentByTag(KEY_EDITOR_FRAGMENT);

        // add the first fragment if not yet added
        if (mNotesFragment == null) {
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            mNotesFragment = new NotesFragment();
            fragmentTransaction.replace(R.id.activity_notes_first_fragment_container, mNotesFragment, KEY_NOTES_FRAGMENT);
            fragmentTransaction.commit();
        }
    }

    //      if it's a one pane view (i.e phone screen)
    @Override
    public void loadSingleFragment() {
        LogUtils.d("loadSingleFragment()");
        // get the fragment if it already exists
        mNotesFragment = (NotesFragment) getSupportFragmentManager().findFragmentByTag(KEY_NOTES_FRAGMENT);
        // add the fragment if not yet added
        if (mNotesFragment == null) {
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            mNotesFragment = new NotesFragment();
            fragmentTransaction.replace(R.id.activity_notes_fragment_container, mNotesFragment, KEY_NOTES_FRAGMENT);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void showNewNote() {
        if (mEditorFragment != null) {
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.remove(mEditorFragment);
            fragmentTransaction.commit();
        }
        Intent intent = new Intent(this, EditorActivity.class);
        startActivity(intent);
    }

    @Override
    public void showNoteDetail(@NonNull Note note) {
        //if we're in portrait - just open the editor activity with wanted fragment
        if (checkIfPortrait()) {
            Intent intent = new Intent(this, EditorActivity.class);
            intent.putExtra(Note.NOTE_KEY_STRING, note.getId());
            startActivity(intent);
            return;
        }

        //add the note info to the current activity intent
        getIntent().putExtra(Note.NOTE_KEY_STRING, note.getId());

        //now add the note with a fragment if we're in landscape
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        mEditorFragment = new EditorFragment();
        fragmentTransaction.replace(R.id.activity_notes_second_fragment_container, mEditorFragment, KEY_EDITOR_FRAGMENT);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private boolean checkIfPortrait() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

}
