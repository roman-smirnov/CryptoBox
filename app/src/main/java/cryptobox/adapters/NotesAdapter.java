package cryptobox.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;

import java.util.List;

import cryptobox.R;
import cryptobox.dataobjects.Note;
import cryptobox.utils.MyApplication;

import static com.google.common.base.Preconditions.checkNotNull;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    //this one holds the states of all the views -> checked or unchecked TODO get this shit out of here and into some state object
    private SparseBooleanArray mSparseBooleanArray = new SparseBooleanArray();
    private List<Note> mNotesList;
    private boolean mIsShownOnBind = false;


    public NotesAdapter(List<Note> notesList) {
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

        //unset the item background color
        holder.itemView.setBackgroundColor(Color.TRANSPARENT);

        //get the checkbox inside the view and set it to checked
        CheckBox checkBox = (CheckBox) holder.itemView.findViewById(R.id.activity_notes_item_row_checkbox);


        // logic for the showing hiding the checkboxes
        if (mIsShownOnBind) {
            checkBox.setVisibility(View.VISIBLE);

            //check against the sparse boolean array to see if the checkbox needs to be checked or not, and the color needs changed
            if (mSparseBooleanArray.get(position)) {
                checkBox.setChecked(true);
                holder.itemView.setBackground(MyApplication.getContext().getDrawable(R.drawable.bg_edit_text));
            } else {
                checkBox.setChecked(false);
            }
        } else {
            checkBox.setChecked(false);
            checkBox.setVisibility(View.GONE);
        }

        Note note = mNotesList.get(position);
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
    public Note getItem(int position) {
        return mNotesList.get(position);
    }

    /**
     * replace the list in the adapter and call notifydatasetchanged
     *
     * @param notesList
     */
    public void replaceData(@NonNull List<Note> notesList) {
        mNotesList = checkNotNull(notesList);
        notifyDataSetChanged();
    }

    /**
     * the the position of a specific note in the list
     *
     * @param note
     * @return
     */
    public int getPosition(@NonNull Note note) {
        return mNotesList.indexOf(note);
    }


    /**
     * make a note always appear checked , even after onBindView is called
     *
     * @param position
     */
    public void showNoteChecked(int position) {
        mSparseBooleanArray.put(position, true);
        notifyItemChanged(position);
    }

    /**
     * make a note always appear unchecked, even after onBindView is called
     *
     * @param position
     */
    public void hideNoteChecked(int position) {
        mSparseBooleanArray.put(position, false);
        notifyItemChanged(position);
    }


    /**
     * all notes from now on will have their checkboxes visible
     */
    public void showCheckBoxsOnBind() {
        mIsShownOnBind = true;
        //        force all observers to reload and rebind the views - thus redrawing all checkbox states
        notifyDataSetChanged();
    }

    /**
     * all notes from now on will have their checkboxes gone and unchecked on redraw
     */
    public void hideCheckBoxsOnBind() {
        mIsShownOnBind = false;
        //        force all observers to reload and rebind the views - thus redrawing all checkbox states
        for (int i = 0; i < mSparseBooleanArray.size(); i++) {
            // get the object by the key.
            mSparseBooleanArray.put(i, false);
        }
        notifyDataSetChanged();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView content;

        public NoteViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.note_title);
            content = (TextView) view.findViewById(R.id.note_content);
        }
    }
}
