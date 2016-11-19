package cryptobox.contracts;

import android.support.annotation.NonNull;

import java.util.List;

import cryptobox.dataobjects.Note;


/**
 * a contract between the notes view and the notes presenter
 */
public interface NotesContract {
    interface View {
        void showNotes(@NonNull List<Note> noteList);

        void showNoteDetail(@NonNull Note note);

        void showAddNewNote();

        void uncheckSelectedNotes(List<Note> noteList);

        void showNoteUnchecked(Note note);

        void showNoteChecked(Note note);

        void showTrashCan();

        void hideTrashCan();

        void exitApp();

        void showConfirmDeleteDialog();

        void showSettings();
    }

    interface PresenterContract extends BasePresenterContract {

//        @Nullable int[] getCheckedNotes();

        void userPressedBackButton();

        void userClickedOnNote(@NonNull Note note);

        void userLongClickedOnNote(@NonNull Note note);

        void userClickedOnFab();

        void userClickedOnTrashCan();

        void userClickedConfirmDelete();

        void userClickedOnSettings();
    }
}
