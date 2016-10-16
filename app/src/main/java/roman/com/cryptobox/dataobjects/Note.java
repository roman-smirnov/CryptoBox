package roman.com.cryptobox.dataobjects;

import android.os.Parcel;
import android.os.Parcelable;

import roman.com.cryptobox.database.DatabaseHandler;
import roman.com.cryptobox.fileutils.MockNoteGenerator;

public class Note implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitle);
        dest.writeString(this.mLastModified);
        dest.writeLong(this.mId);
    }

    protected Note(Parcel in) {
        this.mTitle = in.readString();
        this.mLastModified = in.readString();
        this.mId = in.readInt();
    }

    public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel source) {
            return new Note(source);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}