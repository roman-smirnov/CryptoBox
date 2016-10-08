package roman.com.cryptobox.dataobjects;

import android.os.Parcel;
import android.os.Parcelable;

import roman.com.cryptobox.fileutils.MockNoteGenerator;

public class Note implements Parcelable {

    public static final String NOTE_KEY_STRING = "I_AM_A_NOTE";

    private String mTitle;
    private String mLastModified;
    private int mId;

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

    public String getContent() {
        //TODO implement connection to db
        return "";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitle);
        dest.writeString(this.mLastModified);
        dest.writeInt(this.mId);
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