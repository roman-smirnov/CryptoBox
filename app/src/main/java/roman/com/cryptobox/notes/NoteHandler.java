package roman.com.cryptobox.notes;

import android.content.Context;

import com.apkfuns.logutils.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is responsible for handling file transactions
 * Created by roman on 9/17/16.
 */
public class NoteHandler {

    private String mInternalStoragegPath;
    private final static String NOTES_DIR = "/notes";

    /**
     * Main and only constructor
     *
     * @param context activity or app context - will not be held internally
     */
    public NoteHandler(Context context) {
        mInternalStoragegPath = context.getFilesDir().getAbsolutePath() + NOTES_DIR;
    }

    public List<Note> getNotes() {
        List<Note> noteList = new ArrayList<>();
        File folder = new File(mInternalStoragegPath);
        LogUtils.d(folder.getAbsolutePath());
        try {
            for (final File fileEntry : folder.listFiles()) {
                Note tempNote = new Note(fileEntry.getName(), new Date(fileEntry.lastModified()).toString());
                noteList.add(tempNote);
            }
        } catch (Exception e) {
            LogUtils.d("Failed to read from file");
            e.printStackTrace();
        }
        return noteList;
    }

    public void writeNote(String noteName, String noteContent) {
        File file = new File(mInternalStoragegPath, noteName);
        writeFile(file, noteContent);
    }

    private void writeFile(File file, String fileContent) {
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            LogUtils.d("writing note to file");
            FileOutputStream outputStream = new FileOutputStream(file, true);
            outputStream.write(fileContent.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            LogUtils.d("Failed to write to file");
            e.printStackTrace();
        }
    }
}

