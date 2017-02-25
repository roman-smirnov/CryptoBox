package cryptobox.presenters;

import android.support.annotation.NonNull;

import cryptobox.contracts.LoginContract;
import cryptobox.models.LoginModel;
import cryptobox.utils.PasswordHandler;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * this class is the logic module, handling user input and directing model and view changes
 */
public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View mView;
    private LoginContract.Model mModel;
    private boolean mIsPasswordVisible = false;

    public LoginPresenter(@NonNull LoginContract.Model loginModel) {
        this.mModel = loginModel;
    }


    @Override
    public void setView(LoginContract.View view) {
        mView = checkNotNull(view);
    }

    /**
     * logic for what happens when the login button in the LoginActivity is pressed
     *
     * @param password the password input by the user
     */
    @Override
    public void loginButtonClicked(@NonNull String password) {
        if (validatePassword(password)) {
//            PasswordHandler.SessionPassword.setSessionPassword(password);
            mModel.setSessionPassword(password);
            mView.showNotesActivity();
        } else {
            mView.showPasswordBad();
        }
    }

    /**
     * check if the password either not right or empty
     */
    private boolean validatePassword(@NonNull String password) {
        checkNotNull(password);
        //TODO PasswordHandler should be moved to a model since it doesn't mock well as it is now
        // Check for a valid password, if the user entered one.
//        if (password.isEmpty() || !PasswordHandler.StoredPassword.verifyPassword(password)) {
        if(!password.isEmpty() && mModel.verifyPassword(password)){
            return true;
        } else {
        mView.showPasswordBad();
        return false;
        }
    }


}
