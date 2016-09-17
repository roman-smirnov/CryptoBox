package roman.com.cryptobox.filehandling;

import com.apkfuns.logutils.LogUtils;

import org.spongycastle.crypto.paddings.PKCS7Padding;
import org.spongycastle.util.encoders.Hex;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * This class is responsible for handling file transactions
 * Created by roman on 9/17/16.
 */
public class FileManager {
    private fileNameDispenser mFileNameDispenser;
    private FileWriter mFileWriter;



    /**
     * Main and only constructor
     */
    public FileManager() {
        mFileNameDispenser = new fileNameDispenser();
        mFileWriter = new FileWriter();
    }

    /**
     * writes given filename and content to an encrypted file
     * @param fileName
     * @param fileContent
     */
    public void writeEncryptedFile(String fileName, String fileContent) {
        try {
            String encryptedFilePath = mFileNameDispenser.getEncryptedFilePath(fileName);
            mFileWriter.writeEncryptedFile(encryptedFilePath,fileContent);
        } catch (IllegalAccessException e) {
            LogUtils.d("ERROR GETTING EXTERNAL FILE STORAGE WRITEABLE PATH");
            e.printStackTrace();
        }
    }

    /**
     * writes given filename and content to an unencrypted file
     * @param fileName
     * @param fileContent
     */
    public void writeUnencryptedFile(String fileName, String fileContent) {
        try {
            String unencryptedFilePath = mFileNameDispenser.getUnencryptedFilePath(fileName);
            mFileWriter.writeUnencryptedFile(unencryptedFilePath,fileContent);
        } catch (IllegalAccessException e) {
            LogUtils.d("ERROR GETTING EXTERNAL FILE STORAGE WRITEABLE PATH");
            e.printStackTrace();
        }
    }
}

