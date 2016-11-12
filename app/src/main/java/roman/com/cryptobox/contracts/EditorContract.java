package roman.com.cryptobox.contracts;

import android.support.annotation.NonNull;

import roman.com.cryptobox.dataobjects.Note;


/**
 * this interface is a contract between the EditorActivity (the view) and it's logic module - EditorPresenter (the presenter)
 */
public interface EditorContract {
    int DEFAULT_NOTE_BUNDLE_RETURN_VALUE = -1;

    interface View {
        void showNote(Note note);

        void makeViewsEditable();

        void makeViewsUneditable();

        void focusOnView(@NonNull android.view.View view);
    }

    interface Presenter {
        void openNote(int noteId);

        void toggleEditState(android.view.View trigger);
    }
}
