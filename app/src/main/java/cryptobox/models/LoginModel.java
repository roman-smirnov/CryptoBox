package cryptobox.models;

import cryptobox.contracts.LoginContract;
import cryptobox.repositories.LoginLogic;

/**
 * *********************************
 * Project: Blin.gy Android Application
 * Description:
 * Created by Oren Zakay on 25/02/17.
 * History:
 * ***********************************
 */
public class LoginModel implements LoginContract.Model {

    private LoginLogic mRepository;

    public LoginModel(LoginLogic repository){
        this.mRepository = repository;
    }

    @Override
    public boolean verifyPassword(String password) {
        return mRepository.verifyPassword(password);
    }

    @Override
    public void setSessionPassword(String password) {
        mRepository.setSessionPassword(password);
    }
}
