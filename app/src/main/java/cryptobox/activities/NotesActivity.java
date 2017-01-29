package cryptobox.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import cryptobox.R;
import cryptobox.fragments.EditorFragment;
import cryptobox.fragments.NotesFragment;
import cryptobox.listeners.PopBackStackListner;

public class NotesActivity extends AppCompatActivity implements PopBackStackListner {

    private static final String KEY_NOTES_FRAGMENT = "NOTES_FRAGMENT";
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.notes_toolbar);
        setSupportActionBar(toolbar);

        // get the fragment if it already exists
        mFragment = (NotesFragment) getSupportFragmentManager().findFragmentByTag(KEY_NOTES_FRAGMENT);
        // add the fragment if not yet added
        if (mFragment == null) {
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            mFragment = new NotesFragment();
            fragmentTransaction.replace(R.id.activity_notes_fragment_container, mFragment, KEY_NOTES_FRAGMENT);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (mFragment != null) {
            ((NotesFragment) mFragment).onBackPressed();
        }
    }

    @Override
    public void popBackStack() {
        super.onBackPressed();
    }
}
