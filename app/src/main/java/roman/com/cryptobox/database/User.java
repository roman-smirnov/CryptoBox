package roman.com.cryptobox.database;

/**
 * Created by roman on 9/17/16.
 */
public class User {
    private int mId;
    private String mUserName;
    private String mPassword;

    public User(){
    }

    public User(String userName, String password) {
        mUserName = userName;
        mPassword = password;
    }

    public User(int id, String userName, String password) {
        mId = id;
        mUserName = userName;
        mPassword = password;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }


}
