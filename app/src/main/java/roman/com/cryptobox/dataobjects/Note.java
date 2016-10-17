package roman.com.cryptobox.dataobjects;

import roman.com.cryptobox.database.DatabaseHandler;

public class Note {

    //public static final String NOTE_KEY_STRING = "I_AM_A_NOTE";

    private String mTitle;
    private String mLastModified;
    private long mId;

    private String mContent = null;

    public Note(String title, String lastModified, long id) {
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

    public long getId() {
        return mId;
    }

    public String getContent() {

        if(mContent == null)
            mContent = DatabaseHandler.getContentById(mId);

        return mContent;
    }

    public Boolean SaveToDb()
    {
        Boolean res = DatabaseHandler.saveNoteToDB(mTitle, mContent, mLastModified, mId);

        return res;
    }

    public void setContent(String content) { mContent = content; }
}