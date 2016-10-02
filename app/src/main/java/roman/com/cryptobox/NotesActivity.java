package roman.com.cryptobox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Note> mNoteList = new ArrayList<>();
    private NotesAdapter mNotesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.notes_toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mNotesAdapter = new NotesAdapter(mNoteList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mNotesAdapter);

        fillNotesList();
    }

    private void fillNotesList() {
        for (int i = 0; i < 50; i++) {
            Note temp = new Note("title" + i, "blablabla" + i);
            mNoteList.add(temp);
        }
        mNotesAdapter.notifyDataSetChanged();
    }

}
