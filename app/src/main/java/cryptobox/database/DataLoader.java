package cryptobox.database;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import cryptobox.contracts.DataModel;
import cryptobox.dataobjects.Note;

/**
 * Created by avishi.lippner on 11/26/2016.
 */
public class DataLoader implements DataModel {
    private static DataLoader ourInstance = new DataLoader();

    public static DataLoader getInstance() {
        return ourInstance;
    }

    private static DataManager mDataManager = DataManager.getInstance();
    private static List<Note> mNoteList = mDataManager.getAllNotes();


    private DataLoader() {
    }


    @Override
    public void updateNote(@NonNull Note note) {
        // the note received as param is held in the list anyway so it will be updated anyhow
        mDataManager.updateNote(note);
    }

    @Nullable
    @Override
    public Note getNoteById(int id) {
        for (Note note : mNoteList) {
            if (note.getId() == id) {
                return note;
            }
        }
        return null;
    }

    @Override
    public void addNote(@NonNull String title, @NonNull String lastModified, @NonNull String content) {
        //saves the note to the db and then add it to the note list
        mNoteList.add(mDataManager.addNote(title, lastModified, content));
    }

    @NonNull
    @Override
    public List<Note> getNotes() {
        return mNoteList;
    }

    @Override
    public void deleteNote(@NonNull Note note) {
        mNoteList.remove(note);
        mDataManager.deleteNote(note);
    }
}
