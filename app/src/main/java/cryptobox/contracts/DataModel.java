package cryptobox.contracts;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import cryptobox.dataobjects.Note;

/**
 * Created by avishi.lippner on 11/26/2016.
 */

public interface DataModel {

    void updateNote(@NonNull Note note);

    @Nullable Note getNoteById(int Id);

    void addNote(@NonNull String title, @NonNull String lastModified, @NonNull String content);

    @NonNull List<Note> getNotes();

    void deleteNote(@NonNull Note note);

}
