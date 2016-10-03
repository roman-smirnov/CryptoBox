package roman.com.cryptobox.fileutils;

import android.text.format.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashMap;


/**
 * Created by avishai on 03/10/2016.
 */
public class MockFileManager {

    public class MockedFile{
        public int id;
        public String title;
        public Date updatedDate;
        //public String content;
    }

    private static final int NUMBER_OF_NOTES = 500;
    private static final int NUMBER_OF_ROWS = 50;

    public  List<MockedFile> filesList = new ArrayList<>();
    HashMap<Integer, String> map = new HashMap<Integer, String>();

    public MockFileManager(){
        generateList();
    }

    private void generateList(){

        for (int i = 1; i < NUMBER_OF_NOTES; i++) {

            MockedFile file = new MockedFile();

            file.id = i;
            file.title = "Title " + i;
            file.updatedDate = new Date();

            map.put(i, generateContent(i));
        }
    }

    private String generateContent(int index) {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            str.append("Generated text for " + index);
        }

        return str.toString();
    }

    public List<MockedFile> getNotesList()
    {
        return filesList;
    }

    public String getNoteById(int id){
        return map.get(id);
    }

}
