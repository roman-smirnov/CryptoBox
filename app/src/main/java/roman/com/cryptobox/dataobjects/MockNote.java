package roman.com.cryptobox.dataobjects;

import roman.com.cryptobox.utils.MockNoteGenerator;

/**
 * Created by roman on 10/8/16.
 */

public class MockNote {
    public static final String NOTE_KEY_STRING = "I_AM_A_NOTE";

    private String mTitle;
    private String mLastModified;
    private int mId;

    public MockNote(String title, String lastModified, int id) {
        mTitle = title;
        mLastModified = lastModified;
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String name) {
        mTitle = name;
    }

    public String getLastModified() {
        return mLastModified;
    }

    public void setLastModified(String lastModified) {
        mLastModified = lastModified;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getContent() {
        return MockNoteGenerator.getInstance().getContentById(mId);
    }

    public void setContent(String content) {
        MockNoteGenerator.getInstance().setContentById(mId, content);
    }
}
