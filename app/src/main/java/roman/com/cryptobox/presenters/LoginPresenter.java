package roman.com.cryptobox.presenters;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import roman.com.cryptobox.contracts.LoginContract;
import roman.com.cryptobox.utils.PasswordHandler;

/**
 * this class is the logic module, handling user input and directing model and view changes
 */
public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View mLoginView;

    public LoginPresenter(@NonNull LoginContract.View loginView) {
        mLoginView = loginView;
    }

    /**
     * logic for what happens when the login button in the LoginActivity is pressed
     *
     * @param password the password input by the user
     */
    @Override
    public void loginButtonClicked(@NonNull String password) {
        if (validatePassowrd(password)) {
            PasswordHandler.setSessionPassword(password);
            mLoginView.showPasswordGood();
            mLoginView.showNotesActivity();
        } else {
            mLoginView.showPasswordBad();
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private boolean validatePassowrd(String password) {
        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !PasswordHandler.verifyPassword(password)) {
            mLoginView.showPasswordBad();
            return false;
        } else {
            return true;
        }
    }
}
