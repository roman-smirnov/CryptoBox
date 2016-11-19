package cryptobox.presenters;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import cryptobox.contracts.LoginContract;
import cryptobox.utils.PasswordHandler;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * this class is the logic module, handling user input and directing model and view changes
 */
public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View mLoginView;

    public LoginPresenter(@NonNull LoginContract.View loginView) {
        mLoginView = checkNotNull(loginView);
    }

    /**
     * logic for what happens when the login button in the LoginActivity is pressed
     *
     * @param password the password input by the user
     */
    @Override
    public void loginButtonClicked(@NonNull String password) {
        if (validatePassword(password)) {
            PasswordHandler.setSessionPassword(password);
            mLoginView.showNotesActivity();
        } else {
            mLoginView.showPasswordBad();
        }
    }

    /**
     * check if the password either not right or empty
     */
    private boolean validatePassword(@NonNull String password) {
        checkNotNull(password);
        //TODO PasswordHandler should be moved to a model since it doesn't mock well as it is now
        // Check for a valid password, if the user entered one.
        if (password.isEmpty() || !PasswordHandler.verifyPassword(password)) {
            mLoginView.showPasswordBad();
            return false;
        } else {
            return true;
        }
    }
}
