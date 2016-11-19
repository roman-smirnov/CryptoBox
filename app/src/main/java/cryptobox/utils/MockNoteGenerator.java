package cryptobox.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashMap;

import cryptobox.dataobjects.MockNote;


/**
 * Created by avishai on 03/10/2016.
 */
public class MockNoteGenerator {
    private static MockNoteGenerator mMockNoteGenerator;
    private static final int NUMBER_OF_NOTES = 100;
    private static final int NUMBER_OF_ROWS = 50;

    private List<MockNote> noteList = new ArrayList<>();
    private HashMap<Integer, String> mMap = new HashMap<Integer, String>();

    private MockNoteGenerator() {
        //generateList();
    }

    /**
     * get an instance of mock generator
     *
     * @return
     */
    public static MockNoteGenerator getInstance() {
        if (mMockNoteGenerator != null) {
            return mMockNoteGenerator;
        } else {
            mMockNoteGenerator = new MockNoteGenerator();
            return mMockNoteGenerator;
        }
    }

    /**
     * generates a mock notes list
     */
    private void generateList() {
        //generate notes and add to notesList
        for (int i = 1; i < NUMBER_OF_NOTES; i++) {
            MockNote tempNote = new MockNote("Title " + i, new Date(System.currentTimeMillis()).toString(), i);
            noteList.add(tempNote);
            mMap.put(i, generateContent(i));
        }
    }

    /**
     * generate a mock id - content mMap for the contents of notes
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
    public List<MockNote> getNotesList() {
        return noteList;
    }

    /**
     * return the content of the note, given the id
     *
     * @param id
     * @return
     */
    public String getContentById(int id) {
        return mMap.get(id);
    }

    public void setContentById(int id, String content) {
        mMap.put(id, content);
    }

    public void setNoteById(int id, String content) {
        mMap.put(id, content);
    }

    public void deleteNotes(List<MockNote> mockNotes) {
        // do nothing
    }
}
