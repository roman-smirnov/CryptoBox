package roman.com.cryptobox.presenters;

import android.support.annotation.NonNull;
import android.view.View;

import java.util.Date;
import java.util.List;

import roman.com.cryptobox.contracts.EditorContract;
import roman.com.cryptobox.dataobjects.MockNote;
import roman.com.cryptobox.utils.MockNoteGenerator;

import static com.google.common.base.Preconditions.checkNotNull;
import static roman.com.cryptobox.contracts.EditorContract.DEFAULT_NOTE_BUNDLE_RETURN_VALUE;

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
        List<MockNote> mockNoteList = MockNoteGenerator.getInstance().getNotesList();
        MockNote note;
        if (noteId != DEFAULT_NOTE_BUNDLE_RETURN_VALUE) {
            note = mockNoteList.get(noteId);
            mView.showNote(note);
        } else {
            note = new MockNote("", new Date(System.currentTimeMillis()).toString(), MockNoteGenerator.getInstance().getNotesList().size());
            MockNoteGenerator.getInstance().setContentById(mockNoteList.size(), "");
            mView.showNote(note);
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
