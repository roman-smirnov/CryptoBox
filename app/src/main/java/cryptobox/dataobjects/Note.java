package cryptobox.dataobjects;

import cryptobox.database.DataManager;

public class Note {

    protected String mTitle;
    protected String mLastModified;
    protected long mId;
    protected long mKeyId;

    protected String mContent = null;
    protected boolean mIsContentChanged = false;

    public static final String NOTE_KEY_STRING = "I_AM_A_NOTE";

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

        if (mContent == null || !mIsContentChanged)
            mContent = DataManager.getInstance().getContent(this);

        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
        mIsContentChanged = true;
    }
}