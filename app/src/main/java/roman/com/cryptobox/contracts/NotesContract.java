package roman.com.cryptobox.contracts;

import android.support.annotation.NonNull;

import java.util.List;

import roman.com.cryptobox.dataobjects.MockNote;


/**
 * a contract between the notes view and the notes presenter
 */
public interface NotesContract {
    interface View {
        void showNotes(@NonNull List<MockNote> noteList);

        void showNoteDetail(@NonNull MockNote note);

        void showAddNewNote();

        void uncheckSelectedNotes(List<MockNote> noteList);

        void showNoteUnchecked(MockNote note);

        void showNoteChecked(MockNote note);

        void showTrashCan();

        void hideTrashCan();

        void exitApp();

        void showConfirmDeleteDialog();

        void showSettings();
    }

    interface PresenterContract extends BasePresenterContract {

//        @Nullable int[] getCheckedNotes();

        void userPressedBackButton();

        void userClickedOnNote(@NonNull MockNote note);

        void userLongClickedOnNote(@NonNull MockNote note);

        void userClickedOnFab();

        void userClickedOnTrashCan();

        void userClickedConfirmDelete();

        void userClickedOnSettings();
    }
}
