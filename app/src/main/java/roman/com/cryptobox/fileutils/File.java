package roman.com.cryptobox.fileutils;

/**
 * Created by roman on 9/17/16.
 */
public class File {

    private int mId;
    private String mFileName;
    private int mKeyId;

    public File(){
    }

    public File(String FileName) {
        mFileName = FileName;
    }

    public File(int id, String FileName, int KeyId) {
        mId = id;
        mFileName = FileName;
        mKeyId = KeyId;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getFileName() {
        return mFileName;
    }

    public void setFileName(String fileName) {
        mFileName = fileName;
    }


}
