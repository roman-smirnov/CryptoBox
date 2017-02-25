package cryptobox.contracts;

import android.support.annotation.NonNull;

import cryptobox.dataobjects.Note;

/**
 * Created by roman on 2/11/17.
 */

public interface NotesActivityContract {

    interface Presenter {
        void start(boolean isTwoPane);
    }

    interface View {

        void showNewNote();

        void showNoteDetail(@NonNull Note note);

        void loadTwoPaneFragments();

        void loadSingleFragment();

    }
}
