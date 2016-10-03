package roman.com.cryptobox.login;

/**
 * Created by roman on 10/3/16.
 */
public class PassHolder {
    private static String mPassword;
    private static PassHolder ourInstance = new PassHolder();

    public static PassHolder getInstance() {
        return ourInstance;
    }

    private PassHolder() {
        mPassword = "avishai_is_king";
    }

}
