package cryptobox.repositories;

/**
 * *********************************
 * Project: Blin.gy Android Application
 * Description:
 * Created by Oren Zakay on 25/02/17.
 * History:
 * ***********************************
 */
public interface LoginRepository {

    boolean verifyPassword(String password);

    void setSessionPassword(String password);
}
