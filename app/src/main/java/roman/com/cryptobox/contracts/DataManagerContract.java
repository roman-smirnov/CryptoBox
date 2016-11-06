package roman.com.cryptobox.contracts;

import java.util.List;

import roman.com.cryptobox.dataobjects.Note;

/**
 * Created by avishai on 05/11/2016.
 */

public interface DataManagerContract {

    //Create

    public Note addNote(String title, String lastModified, String content);


    //Read

    public List<Note> getAllNotes();

    public String getContent(Note n);


    //Update

    public Boolean UpdateNote(Note n);


    //Delete

    public Boolean deleteNote(Note n);

    public Boolean deleteNoteById(long NoteId, long keyId);
}
