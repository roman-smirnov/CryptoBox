package cryptobox.contracts;

import android.support.annotation.NonNull;

import cryptobox.dataobjects.Note;


/**
 * this interface is a contract between the EditorActivity (the view) and it's logic module - EditorFragmentPresenter (the presenter)
 */
public interface EditorContract {
    int DEFAULT_NOTE_BUNDLE_RETURN_VALUE = -1;

    interface View {
        void showNote(Note note);

        void makeViewsEditable();

        void makeViewsUneditable();

        void focusOnView(@NonNull android.view.View view);

        void closeEditorView();

        void showDeleteConfirmation();

        void showEmptyTitleMessage();

        void showSavedMessage();
    }

    interface Presenter extends BasePresenterContract {

        void openNote(int noteId);

        void toggleEditState(android.view.View trigger);

        void saveNote(@NonNull String title, @NonNull String content);

        void deleteNoteSelected();

        void deleteNoteConfirmed();

    }
}
