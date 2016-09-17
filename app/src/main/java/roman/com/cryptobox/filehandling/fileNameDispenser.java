package roman.com.cryptobox.filehandling;

import android.os.Environment;

import com.apkfuns.logutils.LogUtils;

import java.io.File;

/**
 * This class is responsible for generating file paths, when given a file name
 * Created by roman on 7/29/16.
 */
public class fileNameDispenser {
    // external storage directory name
    private final String EXTERNAL_STORAGE_DIR_NAME = "CryptoBox_Files/";

    // encrypted file extension
    private final String ENCRYPTED_EXTENSION = ".enc";

    // the unencrypted file name extension
    private final String UNENCRYPTED_EXTENSION = ".txt";

    // path/file tree seperator token
    private final String FILE_SEPERATOR_TOKEN = "/";


    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        LogUtils.d("EXTERNAL STORAGE NOT WRITEABLE");
        return false;
    }

    /**
     * get the storage directory to write files to
     * @return
     */
    private String getStorageDirectory() {
        // Get the directory for the user's external storage.
        File file = new File(Environment.getExternalStorageDirectory(), EXTERNAL_STORAGE_DIR_NAME);

        if(file.mkdirs()) {
            //file was created
        }else{
            //failed or already exists
        }
        return file.getAbsolutePath();
    }

    /**
     * get the file path to write an unencrypted file to
     * @param fileName the file name for the file you want to create
     * @return the full path to the file to be created
     * @throws IllegalAccessException thrown if external storage is not writeable for some reason
     */
    public String getUnencryptedFilePath(String fileName) throws IllegalAccessException {
        if(!isExternalStorageWritable()){
            throw new IllegalAccessException("Can't write to external storage for some reason");
        }
        return getStorageDirectory() + FILE_SEPERATOR_TOKEN +fileName + UNENCRYPTED_EXTENSION;
    }

    /**
     * get the file path to write an encrypted file to
     * @param fileName  the file name for the file you want to create
     * @return  the full path to the file to be created
     * @throws IllegalAccessException thrown if external storage is not writeable for some reason
     */
    public String getEncryptedFilePath(String fileName) throws  IllegalAccessException{
        if(!isExternalStorageWritable()){
            throw new IllegalAccessException("Can't write to external storage for some reason");
        }
        return getStorageDirectory() + FILE_SEPERATOR_TOKEN +fileName + ENCRYPTED_EXTENSION;
    }
}
