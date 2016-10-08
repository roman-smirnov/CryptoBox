package roman.com.cryptobox.fileutils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.HashMap;

import roman.com.cryptobox.notes.Note;


/**
 * Created by avishai on 03/10/2016.
 */
public class MockNoteGenerator {

    private static final int NUMBER_OF_NOTES = 100;
    private static final int NUMBER_OF_ROWS = 50;

    public List<Note> noteList = new ArrayList<>();
    HashMap<Integer, String> map = new HashMap<Integer, String>();

    public MockNoteGenerator() {
        generateList();
    }

    /**
     * generates a mock notes list
     */
    private void generateList(){
        Calendar calendar = Calendar.getInstance();
        //generate notes and add to notesList
        for (int i = 1; i < NUMBER_OF_NOTES; i++) {
            calendar.setTimeInMillis(System.currentTimeMillis());
            Note tempNote = new Note("Title " + i, calendar.toString(), i);
            noteList.add(tempNote);
            map.put(i, generateContent(i));
        }
    }

    /**
     * generate a mock id - content map for the contents of notes
     *
     * @param index
     * @return
     */
    private String generateContent(int index) {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            str.append("Generated text for " + index);
        }

        return str.toString();
    }

    /**
     * returns a mock list of notes
     *
     * @return
     */
    public List<Note> getNotesList() {
        return noteList;
    }

    /**
     * return the content of the note, given the id
     * @param id
     * @return
     */
    public String getNoteById(int id){
        return map.get(id);
    }

}
