package cryptobox.repositories;

import cryptobox.utils.PasswordHandler;

/**
 * *********************************
 * Project: Blin.gy Android Application
 * Description:
 * Created by Oren Zakay on 25/02/17.
 * History:
 * ***********************************
 */
public class LoginLogic implements LoginRepository{

    @Override
    public boolean verifyPassword(String password) {
        return PasswordHandler.StoredPassword.verifyPassword(password);
    }

    @Override
    public void setSessionPassword(String password) {
        PasswordHandler.SessionPassword.setSessionPassword(password);
    }
}
