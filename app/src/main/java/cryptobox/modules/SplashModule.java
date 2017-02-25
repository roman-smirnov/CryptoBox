package cryptobox.modules;

import cryptobox.contracts.SplashContract;
import cryptobox.models.SplashModel;
import cryptobox.presenters.SplashPresenter;
import cryptobox.repositories.SplashLogic;
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
public class SplashModule {

    @Provides
    public SplashContract.Presenter provideSplashActivityPresenter(SplashContract.Model model){
        return new SplashPresenter(model);
    }

    @Provides
    public SplashContract.Model provideSplashActivityModel(SplashLogic repository){
        return new SplashModel(repository);
    }

    @Provides
    public SplashLogic provideSplashRepository(){
        return new SplashLogic();
    }
}
