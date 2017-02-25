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
public class SplashLogic implements SplashRepository{

    @Override
    public boolean isStoredPasswordSet() {
        return PasswordHandler.StoredPassword.isStoredPasswordSet();
    }
}
