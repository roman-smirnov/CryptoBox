package cryptobox.presenters;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import cryptobox.contracts.NotesContract;
import cryptobox.database.DataManager;
import cryptobox.dataobjects.Note;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * this class is the logic module, handling user input and directing model and view changes
 */
// TODO add the superclass with the start() method like you did in inventoryApp
public class NotesPresenter implements NotesContract.PresenterContract {

    private NotesContract.View mView;

    private List<Note> mCheckedNoteList;

    /**
     * main and only constrcutor
     *
     * @param view
     */
    public NotesPresenter(@NonNull NotesContract.View view) {
        mView = checkNotNull(view);
        mCheckedNoteList = new ArrayList<>(0);
    }

    /**
     * load the notes when the view is first created
     */
    @Override
    public void start() {
        loadNotes();
    }

    /**
     * load a list of notes from the model
     */
    private void loadNotes() {
        mView.showNotes(DataManager.getInstance().getAllNotes());
    }

    /**
     * user clicked on add a new note
     */
    private void openNewNote() {
        mView.showAddNewNote();
    }

    /**
     * user clicked show details of an existing note
     *
     * @param note
     */
    private void openNoteDetails(@NonNull Note note) {
        checkNotNull(note);
        mView.showNoteDetail(note);
    }


    /**
     * receives a note from the view - adds it or removes in
     *
     * @param note
     */
    private void addOrRemoveCheckedNote(@NonNull Note note) {
        checkNotNull(note);

        if (mCheckedNoteList.contains(note)) {
            removeCheckedNote(note);
        } else {
            addCheckedNote(note);
        }
    }

    /**
     * add a note to the checked note list
     *
     * @param note
     */
    private void addCheckedNote(@NonNull Note note) {
        checkNotNull(note);

        mView.showNoteChecked(note);

        // make the trashcan menu button visible
        if (isCheckNoteListEmpty()) {
            mView.showTrashCan();
        }
        mCheckedNoteList.add(note);
    }

    /**
     * remove a note from the checked note list
     *
     * @param note
     */
    private void removeCheckedNote(@NonNull Note note) {
        checkNotNull(note);

        mView.showNoteUnchecked(note);

        mCheckedNoteList.remove(note);

        // remove the trashcan button
        if (isCheckNoteListEmpty()) {
            mView.hideTrashCan();
        }
    }

    /**
     * delete all the checked notes from the db
     */
    private void deleteCheckedNotes() {
        for (Note n : mCheckedNoteList)
            DataManager.getInstance().deleteNote(n);

        mCheckedNoteList.clear();
        loadNotes();
        mView.hideTrashCan();
    }

    /**
     * uncheck all notes selected for deletion
     */
    private void clearCheckedNotes() {
        mView.uncheckSelectedNotes(mCheckedNoteList);
        mCheckedNoteList.clear();
        mView.hideTrashCan();
    }

    @Override
    public void userPressedBackButton() {
        if (isCheckNoteListEmpty()) {
            mView.exitApp();
        } else {
            clearCheckedNotes();
        }
    }

    private boolean isCheckNoteListEmpty() {
        return mCheckedNoteList.isEmpty();
    }

    @Override
    public void userClickedOnNote(@NonNull Note note) {
        checkNotNull(note);

        if (isCheckNoteListEmpty()) {
            openNoteDetails(note);
        } else {
            addOrRemoveCheckedNote(note);
        }
    }

    @Override
    public void userLongClickedOnNote(@NonNull Note note) {
        checkNotNull(note);
        addOrRemoveCheckedNote(note);
    }

    @Override
    public void userClickedOnFab() {
        if (!isCheckNoteListEmpty()) {
            return;
        }
        openNewNote();
    }

    @Override
    public void userClickedOnTrashCan() {
        if (!isCheckNoteListEmpty()) {
            mView.showConfirmDeleteDialog();
        }
    }

    @Override
    public void userClickedConfirmDelete() {
        if (!isCheckNoteListEmpty()) {
            deleteCheckedNotes();
        }
    }

    @Override
    public void userClickedOnSettings() {
        mView.showSettings();
    }

    //    TODO implement saving of checked notes on screen rotation etc
//    /**
//     * get the IDs to save
//     * @return
//     */
//    @Override
//    public @Nullable int[] getCheckedNotes() {
//
//        if (mCheckedNoteList.isEmpty()) {
//            return null;
//        }
//
//        int[] noteIdArray = new int[mCheckedNoteList.size()];
//
//        for (int i = 0; i < mCheckedNoteList.size(); i++) {
//            noteIdArray[i] = mCheckedNoteList.get(i).getId();
//        }
//        return noteIdArray;
//    }

}
