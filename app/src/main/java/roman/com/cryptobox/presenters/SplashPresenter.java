package roman.com.cryptobox.presenters;

import roman.com.cryptobox.contracts.SplashContract;


/**
 * this class is a logic module, handling user input and directing model and view changes
 */
public class SplashPresenter implements SplashContract.Presenter {

    private SplashContract.View mView;

    public SplashPresenter(SplashContract.View view) {
        mView = view;
    }

    /**
     * called when the splash activity is launched
     */
    @Override
    public void appLaunched() {
        //TODO implement show explain activity if user hasn't created a 'profile' yet
        mView.showLoginActivity();
    }
}
