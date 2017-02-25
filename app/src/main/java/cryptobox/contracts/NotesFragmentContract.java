package cryptobox.contracts;

import android.support.annotation.NonNull;

import java.util.List;

import cryptobox.dataobjects.Note;


/**
 * a contract between the notes view and the notes presenter
 */
public interface NotesFragmentContract {
    interface View {
        void showNotes(@NonNull List<Note> noteList);

        void showNoteDetail(@NonNull Note note);

        void showAddNewNote();

        void uncheckSelectedNotes(List<Note> noteList);

        void showNoteUnchecked(Note note);

        void showNoteChecked(Note note);

        void showTrashCan();

        void showBackArrow();

        void hideTrashCan();

        void hideBackArrow();

        void exitApp();

        void showConfirmDeleteDialog();

        void showSettings();

        void showAbout();

        void showPlaceholder();

        void hidePlaceholder();

        void showFab();

        void hideFab();

        void showCheckBoxes();

        void hideCheckBoxes();
    }

    interface PresenterContract extends BasePresenterContract {

//        @Nullable int[] getCheckedNotes();

        void userPressedBackButton();

        void userClickedOnNote(@NonNull Note note);

        void userLongClickedOnNote(@NonNull Note note);

        void userClickedOnFab();

        void userClickedOnTrashCan();

        void userClickedConfirmDelete();

        void userClickedOnChangePassword();

        void userClickedOnAbout();
    }
}
