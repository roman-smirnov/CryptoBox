package cryptobox.presenters;

import android.support.annotation.NonNull;
import android.view.View;

import java.util.Date;

import cryptobox.contracts.DataModel;
import cryptobox.contracts.EditorContract;
import cryptobox.dataobjects.Note;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * logic class handling user input and directing model and view changes
 */
public class EditorPresenter implements EditorContract.Presenter {

    private boolean mIsInEditingMode = false;
    private EditorContract.View mView;
    private DataModel mModel;
    //is it a new note or was an exiting note opened?
    private boolean mIsNewNote = false;
    // The note currently being edited
    private Note mNote = null;
    // signify that the current note was already saved to the db
    private boolean mIsNoteSaved = false;

    public EditorPresenter(@NonNull EditorContract.View view, @NonNull DataModel model) {
        mView = checkNotNull(view);
        mModel = checkNotNull(model);
    }

    @Override
    public void start() {
        /*
        if coming back from onStop -
        the note is already saved and we don't have the id from the db so we can't update
        so just quit back to the notes activity
        */
        if (mIsNoteSaved) {
            mView.closeEditorView();
        }
    }

    @Override
    public void openNote(int noteId) {
        if (noteId != EditorContract.DEFAULT_NOTE_BUNDLE_RETURN_VALUE) {
            Note note = mModel.getNoteById(noteId);
            mView.showNote(note);
            mNote = note;
        } else {
            mIsNewNote = true;
        }
    }

    /**
     * make the views editable or not
     *
     * @param trigger
     */
    @Override
    public void toggleEditState(View trigger) {
        if (mIsInEditingMode) {
            mView.makeViewsUneditable();
            mIsInEditingMode = false;
        } else if (!mIsInEditingMode && trigger != null) {
            mView.makeViewsEditable();
            mIsInEditingMode = true;
            mView.focusOnView(trigger);
        } else {
            mView.makeViewsEditable();
            mIsInEditingMode = true;
        }
    }

    /**
     * save the note to the db
     *
     * @param title
     * @param content
     */
    @Override
    public void saveNote(@NonNull String title, @NonNull String content) {
        if (mIsNoteSaved) {
            return;
        } else {
            mIsNoteSaved = true;
        }
        // if there's not content and no title, no need to save the new note
        if (mIsNewNote && title.trim().isEmpty() && content.trim().isEmpty()) {
            return;
        } else if (mIsNewNote) {
            mModel.addNote(
                    title,
                    new Date(System.currentTimeMillis()).toString(),
                    content);
//            it's an existing note
        } else {
            mNote.setTitle(title);
            mNote.setContent(content);
            mNote.setLastModified(new Date(System.currentTimeMillis()).toString());
            mModel.updateNote(mNote);
        }

        mView.showSavedMessage();
    }


    /**
     * called when the user clicked the garbage icon
     */
    @Override
    public void deleteNoteSelected() {
        mView.showDeleteConfirmation();
    }

    /**
     * delete the note that's currently being edited - if it exists
     */
    @Override
    public void deleteNoteConfirmed() {
        if (mNote != null) {
            mModel.deleteNote(mNote);
            //ask the activity to close itself
            mView.closeEditorView();
        }
    }

}
