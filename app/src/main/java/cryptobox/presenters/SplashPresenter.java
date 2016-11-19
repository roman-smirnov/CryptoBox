package cryptobox.presenters;

import cryptobox.contracts.SplashContract;
import cryptobox.utils.PasswordHandler;


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
    public void start() {
        //check if the user needs to create a new password
        if (!PasswordHandler.isStoredPasswordSet()) {
            mView.showExplainActivity();
        } else {
            mView.showLoginActivity();
        }
    }
}
