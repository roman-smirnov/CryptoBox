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
    }

    interface Presenter {

        void loadNotes();

        void addNewNote();

        void openNoteDetails(@NonNull MockNote note);

    }
}
