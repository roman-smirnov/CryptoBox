package roman.com.cryptobox.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import roman.com.cryptobox.R;
import roman.com.cryptobox.dataobjects.MockNote;

import static com.google.common.base.Preconditions.checkNotNull;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {
    private List<MockNote> mNotesList;


    public class NoteViewHolder extends RecyclerView.ViewHolder {
        public TextView title, content;

        public NoteViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.note_title);
            content = (TextView) view.findViewById(R.id.note_content);
        }
    }

    public NotesAdapter(List<MockNote> notesList) {
        this.mNotesList = notesList;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_notes_item_row, parent, false);

        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        MockNote note = mNotesList.get(position);
        holder.title.setText(note.getTitle());
        holder.content.setText(note.getLastModified());
    }

    @Override
    public int getItemCount() {
        return mNotesList.size();
    }


    /**
     * get the note at specified position in the list
     *
     * @param position
     * @return
     */
    public MockNote getItem(int position) {
        return mNotesList.get(position);
    }

    /**
     * replace the list in the adapter and call notifydatasetchanged
     *
     * @param notesList
     */
    public void replaceData(@NonNull List<MockNote> notesList) {
        mNotesList = checkNotNull(notesList);
        notifyDataSetChanged();
    }
}
