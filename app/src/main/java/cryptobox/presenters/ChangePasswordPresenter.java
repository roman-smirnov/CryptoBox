package cryptobox.presenters;

import android.support.annotation.NonNull;

import cryptobox.contracts.ChangePasswordContract;
import cryptobox.utils.PasswordAnalyzer;
import cryptobox.utils.PasswordHandler;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by roman on 11/5/16.
 */
public class ChangePasswordPresenter implements ChangePasswordContract.Presenter {

    private ChangePasswordContract.View mView;
    private boolean mIsRepeat = false;
    private String mPassword = "";

    public ChangePasswordPresenter(ChangePasswordContract.View view) {
        mView = view;
    }

    @Override
    public void passwordChanged(@NonNull String password) {
        PasswordAnalyzer passwordAnalyzer = new PasswordAnalyzer();
        passwordAnalyzer.calcPasswordStregth(password);
        mView.updatePasswordStrength(passwordAnalyzer.getPasswordStrengthScore(), passwordAnalyzer.getPasswordStrengthDescription());
    }

    @Override
    public void userClickedOk(@NonNull String password) {
        checkNotNull(password);
        if (!mIsRepeat) {
            if (!password.isEmpty()) {
                mIsRepeat = true;
                mPassword = password;
                mView.showInputRepeatNewPassword();
                mView.hidePasswordStrength();
            } else {
//                TODO put the string in values.strings
                mView.showError("password is empty");
            }
        } else {
            if (password.equals(mPassword)) {
                mView.shwoConfirmChangePassowrd();
            } else {
//                TODO put the string in values.strings
                mView.showError("passwords don't match");
            }
        }
    }

    @Override
    public void userClickedBack() {
        if (!mIsRepeat) {
            mView.showNotesActivity();
        } else {
            mPassword = "";
            mIsRepeat = false;
            mView.showInputNewPassword();
            //set some basic password strength state when user didn't put anything in the edittext yet
            mView.updatePasswordStrength(0, "");
            mView.showPasswordStrength();
        }
    }

    @Override
    public void start() {
        mView.showInputNewPassword();
        //set some basic password strength state when user didn't put anything in the edittext yet
        mView.updatePasswordStrength(0, "");
    }

    @Override
    public void userClickedConfirmChangePassword() {
//        TODO add the actual password change functionality against the db
        PasswordHandler.SessionPassword.setSessionPassword(mPassword);
        PasswordHandler.StoredPassword.setStoredPassword(mPassword);
        mView.showNotesActivity();
    }
}
