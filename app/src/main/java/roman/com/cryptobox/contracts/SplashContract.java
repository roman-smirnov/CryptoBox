package roman.com.cryptobox.contracts;

/**
 * this interface is a contract between the SplashActivity (the view) and it's logic module - SplashPresenter(the presenter)
 */
public interface SplashContract {

    interface View {
        void showLoginActivity();

        void showExplainActivity();
    }

    interface Presenter {
        void appLaunched();
    }
}
