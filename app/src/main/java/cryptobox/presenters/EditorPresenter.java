package cryptobox.presenters;

import android.support.annotation.NonNull;
import android.view.View;

import java.util.Date;

import cryptobox.contracts.DataModel;
import cryptobox.contracts.EditorContract;
import cryptobox.database.DataManager;
import cryptobox.dataobjects.Note;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * logic class handling user input and directing model and view changes
 */
public class EditorPresenter implements EditorContract.Presenter {

    private boolean mIsInEditingMode = false;
    private EditorContract.View mView;
    private DataModel mModel;
    private boolean mIsNewNote = false;
    private Note mNote = null;

    public EditorPresenter(@NonNull EditorContract.View view, @NonNull DataModel model) {
        mView = checkNotNull(view);
        mModel = checkNotNull(model);
    }

    @Override
    public void openNote(int noteId) {
        if (noteId != EditorContract.DEFAULT_NOTE_BUNDLE_RETURN_VALUE) {
            Note note = mModel.getNoteById(noteId);
            mView.showNote(note);
            mNote = note;
        }else{
            mIsNewNote = true;
        }
    }

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

    @Override
    public void saveNote(@NonNull String title, @NonNull String content) {
        if(!title.isEmpty()){
            if (mIsNewNote) {
                mModel.addNote(
                        title,
                        new Date(System.currentTimeMillis()).toString(),
                        content);
            }else {
                mNote.setTitle(title);
                mNote.setContent(content);
                mModel.updateNote(mNote);
            }
        }
    }

}
