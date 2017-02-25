package cryptobox.utils;

import javax.inject.Singleton;

import cryptobox.activities.SplashActivity;
import cryptobox.modules.SplashModule;
import dagger.Component;

/**
 * *********************************
 * Description:
 * Created by Oren Zakay on 25/02/17.
 * History:
 * ***********************************
 */
@Singleton
@Component(modules = {ApplicationModule.class, SplashModule.class})
public interface ApplicationComponent {

    void inject(SplashActivity target);

}
