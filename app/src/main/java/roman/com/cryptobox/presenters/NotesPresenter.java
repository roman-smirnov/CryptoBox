package roman.com.cryptobox.presenters;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import roman.com.cryptobox.contracts.NotesContract;
import roman.com.cryptobox.dataobjects.MockNote;
import roman.com.cryptobox.utils.MockNoteGenerator;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * this class is the logic module, handling user input and directing model and view changes
 */
public class NotesPresenter implements NotesContract.Presenter {

    private NotesContract.View mView;

    private List<MockNote> mNoteDeleteList;

    public NotesPresenter(NotesContract.View view) {
        mView = checkNotNull(view);
        mNoteDeleteList = new ArrayList<>(0);
    }

    /**
     * load a list of notes from the model
     */
    @Override
    public void loadNotes() {
        mView.showNotes(MockNoteGenerator.getInstance().getNotesList());
    }

    /**
     * user clicked on add a new note
     */
    @Override
    public void addNewNote() {
        mView.showAddNewNote();
    }

    /**
     * user clicked show details of an existing note
     *
     * @param note
     */
    @Override
    public void openNoteDetails(@NonNull MockNote note) {
        mView.showNoteDetail(note);
    }

    /**
     * add a note to the To-Be-Deleted list of notes
     *
     * @param note
     */
    @Override
    public void addOrRemoveToNoteDeleteList(@NonNull MockNote note) {
        checkNotNull(note);
        if (mNoteDeleteList.contains(note)) {
            mNoteDeleteList.remove(note);
            mView.uncheckNote(note);
        }
        mNoteDeleteList.add(note);
    }

    /**
     * delete all in the To-Be-Deleted list of notes from the db
     */
    @Override
    public void deleteAllInNoteDeleteList() {
        //TODO implmenet the delete
    }

    /**
     * remove all the note from the To-Be-Deleted list of notes
     */
    @Override
    public void clearNoteDeleteList() {
        mView.uncheckSelectedNotes(mNoteDeleteList);
        mNoteDeleteList.clear();
    }
}
