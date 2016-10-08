package roman.com.cryptobox.notes;

public class Note {
    private String mTitle;
    private String mLastModified;
    private int mId;

    public Note() {
    }

    public Note(String title, String lastModified, int id) {
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
}