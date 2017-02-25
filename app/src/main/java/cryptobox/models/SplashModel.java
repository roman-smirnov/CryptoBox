package cryptobox.models;

import cryptobox.contracts.SplashContract;
import cryptobox.repositories.SplashLogic;

/**
 * *********************************
 * Description:
 * Created by Oren Zakay on 22/02/17.
 * History:
 * ***********************************
 */
public class SplashModel implements SplashContract.Model {


    private SplashLogic mRepository;

    public SplashModel(SplashLogic repository){
        this.mRepository = repository;
    }

    @Override
    public boolean isStoredPasswordSet() {
        return mRepository.isStoredPasswordSet();
    }
}
