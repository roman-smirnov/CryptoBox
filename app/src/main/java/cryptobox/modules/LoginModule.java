package cryptobox.modules;

import cryptobox.contracts.LoginContract;
import cryptobox.models.LoginModel;
import cryptobox.presenters.LoginPresenter;
import cryptobox.repositories.LoginLogic;
import dagger.Module;
import dagger.Provides;

/**
 * *********************************
 * Description:
 * Created by Oren Zakay on 25/02/17.
 * History:
 * ***********************************
 */
@Module
public class LoginModule {

    @Provides
    public LoginContract.Presenter provideSplashActivityPresenter(LoginContract.Model model){
        return new LoginPresenter(model);
    }

    @Provides
    public LoginContract.Model provideSplashActivityModel(LoginLogic repository){
        return new LoginModel(repository);
    }

    @Provides
    public LoginLogic provideSplashRepository(){
        return new LoginLogic();
    }
}
