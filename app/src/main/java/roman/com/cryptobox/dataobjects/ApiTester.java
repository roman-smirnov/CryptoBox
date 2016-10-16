package roman.com.cryptobox.dataobjects;

import java.util.List;

import roman.com.cryptobox.database.DatabaseHandler;
import roman.com.cryptobox.mainapplication.MyApplication;

/**
 * Created by avishai on 16/10/2016.
 */

public class ApiTester {

    private DatabaseHandler DB_Object;

    public ApiTester()
    {
        DB_Object = new DatabaseHandler(MyApplication.getContext());

    }

    public void runScripts()
    {

    }

    public void simulateGetAllNotes()
    {
        //DB_Object = new DatabaseHandler(this);


        /*List<Note> lst =  DB_Object.getAllNotes();

        int listSize = lst.size();

        for (int i = 0; i < listSize ; i++) {

            Note temp = lst.get(i);


        }*/

        //String LastModified = new Date(System.currentTimeMillis()).toString();

       /* Note n =  DB_Object.addNote("This is my title", LastModified, "This is my content " + Math.random() );
        Toast.makeText(this, n.getTitle() + ", " + n.getId(), Toast.LENGTH_LONG).show();

        String content =  DatabaseHandler.getContentById(n.getId());

        n.setContent("This is my Content - new content");
        n.setTitle("This is my title - new title");

        String contentx = "";

        Boolean res = n.SaveToDb();
        if(res)
        {
            contentx =  DatabaseHandler.getContentById(n.getId());

        }*/


    }
}
