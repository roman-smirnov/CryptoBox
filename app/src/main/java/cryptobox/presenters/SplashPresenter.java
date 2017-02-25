package cryptobox.presenters;

import cryptobox.contracts.SplashContract;
import cryptobox.models.SplashModel;
import cryptobox.utils.PasswordHandler;


/**
 * this class is a logic module, handling user input and directing model and view changes
 */
public class SplashPresenter implements SplashContract.Presenter {

    private SplashContract.View mView;
    private SplashContract.Model mModel;

    public SplashPresenter(SplashContract.Model model) {
        this.mModel = model;
    }


    public void setView(SplashContract.View view) {
        this.mView = view;
    }


    /**
     * called when the splash activity is launched
     */
    @Override
    public void start() {
        //check if the user needs to create a new password
        if (mModel.isStoredPasswordSet()) {
            mView.showLoginActivity();
        } else {
            mView.showExplainActivity();
        }
    }

}
