package roman.com.cryptobox.presenters;

import android.support.annotation.NonNull;
import android.view.View;

import roman.com.cryptobox.contracts.EditorContract;
import roman.com.cryptobox.database.DataManager;
import roman.com.cryptobox.dataobjects.Note;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * logic class handling user input and directing model and view changes
 */
public class EditorPresenter implements EditorContract.Presenter {

    private boolean mIsInEditingMode = false;
    private EditorContract.View mView;

    public EditorPresenter(@NonNull EditorContract.View view) {
        mView = checkNotNull(view);
    }

    @Override
    public void openNote(int noteId) {
        if(noteId != EditorContract.DEFAULT_NOTE_BUNDLE_RETURN_VALUE){
            Note n = DataManager.getInstance().getNoteById(noteId);
            mView.showNote(n);
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
}
