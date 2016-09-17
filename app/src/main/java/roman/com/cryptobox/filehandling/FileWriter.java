package roman.com.cryptobox.filehandling;

import com.apkfuns.logutils.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * This class is responsible for writing stuff to files and encrypting them if needed
 * Created by roman on 9/17/16.
 */
public class FileWriter {
    /**
     * Encrypt file content and write to provided file path
     * @param filePath
     * @param fileContent
     */
    public void writeEncryptedFile(String filePath, String fileContent) {
        LogUtils.d(filePath);
//        TODO: encrypt the file creating it and writing to it
        File file = new File(filePath);
        writeFile(file, fileContent);
    }

    /**
     * Write file content to provided file path, without encrypting
     * @param filePath
     * @param fileContent
     */
    public void writeUnencryptedFile(String filePath, String fileContent) {
        LogUtils.d(filePath);
        File file = new File(filePath);
        writeFile(file, fileContent);
    }

    /**
     * Write fileContent to provided file
     * @param file
     * @param fileContent
     */
    private void writeFile(File file, String fileContent) {
        try {
            LogUtils.d("writing to file");
            FileOutputStream outputStream = new FileOutputStream(file,true);
            outputStream.write(fileContent.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            LogUtils.d("Failed to write to file");
            e.printStackTrace();
        }
    }
}
