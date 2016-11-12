package roman.com.cryptobox.dataobjects;

import roman.com.cryptobox.database.DataManager;
import roman.com.cryptobox.database.DatabaseHandler;

public class Note {

    //public static final String NOTE_KEY_STRING = "I_AM_A_NOTE";

    private String mTitle;
    private String mLastModified;
    private long mId;
    private long mKeyId;

    private String mContent = null;

    public Note(String title, String lastModified, long id, long keyId) {
        mTitle = title;
        mLastModified = lastModified;
        mId = id;
        mKeyId = keyId;
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

    public long getKeyId() {
        return mKeyId;
    }

    public String getContent() {

        if(mContent == null)
            mContent = DataManager.getInstance().getContent(this);

        return mContent;
    }

    public Boolean UpdateNote()
    {
        Boolean res = DataManager.getInstance().UpdateNote(this);

        return res;
    }

    public void setContent(String content) { mContent = content; }
}