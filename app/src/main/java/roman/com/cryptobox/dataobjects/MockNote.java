package roman.com.cryptobox.dataobjects;

import android.os.Parcel;
import android.os.Parcelable;

import roman.com.cryptobox.fileutils.MockNoteGenerator;

/**
 * Created by roman on 10/8/16.
 */

public class MockNote implements Parcelable {
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
        return MockNoteGenerator.getInstance().getNoteById(mId);
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

    protected MockNote(Parcel in) {
        this.mTitle = in.readString();
        this.mLastModified = in.readString();
        this.mId = in.readInt();
    }

    public static final Parcelable.Creator<MockNote> CREATOR = new Parcelable.Creator<MockNote>() {
        @Override
        public MockNote createFromParcel(Parcel source) {
            return new MockNote(source);
        }

        @Override
        public MockNote[] newArray(int size) {
            return new MockNote[size];
        }
    };
}
