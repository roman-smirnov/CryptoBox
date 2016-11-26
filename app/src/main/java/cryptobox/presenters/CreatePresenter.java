

package cryptobox.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import com.apkfuns.logutils.LogUtils;

import cryptobox.contracts.CreateContract;
import cryptobox.utils.PasswordAnalyzer;
import cryptobox.utils.PasswordHandler;

/**
 * Created by roman on 11/5/16.
 */
public class CreatePresenter implements CreateContract.Presenter {

    private CreateContract.View mView;
    private boolean mIsRepeat = false;
    private String mPassword = "";

    public CreatePresenter(CreateContract.View view) {
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
                PasswordHandler.StoredPassword.setStoredPassword(mPassword);
                PasswordHandler.SessionPassword.setSessionPassword(password);
                mView.showNotesActivity();
            } else {
                //                TODO put the string in values.strings
                mView.showError("password don't match");
            }
        }
    }

    @Override
    public void userClickedBack() {
        if (!mIsRepeat) {
            mView.exitApplication();
        } else {
            mIsRepeat = false;
            mPassword = "";
            mView.showInputNewPassword();
            mView.showPasswordStrength();
        }
    }

    @Override
    public void start() {
        mView.showInputNewPassword();
        mView.showPasswordStrength();
    }
}
