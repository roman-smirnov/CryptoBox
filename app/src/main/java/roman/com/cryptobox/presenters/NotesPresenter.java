package roman.com.cryptobox.presenters;

import android.support.annotation.NonNull;

import roman.com.cryptobox.contracts.NotesContract;
import roman.com.cryptobox.dataobjects.MockNote;
import roman.com.cryptobox.utils.MockNoteGenerator;

/**
 * this class is the logic module, handling user input and directing model and view changes
 */
public class NotesPresenter implements NotesContract.Presenter {

    private NotesContract.View mView;

    public NotesPresenter(NotesContract.View view) {
        mView = view;
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
}
