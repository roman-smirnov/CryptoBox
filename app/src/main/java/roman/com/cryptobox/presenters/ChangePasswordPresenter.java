package roman.com.cryptobox.presenters;

import android.support.annotation.NonNull;

import roman.com.cryptobox.contracts.ChangePasswordContract;
import roman.com.cryptobox.utils.PasswordAnalyzer;

/**
 * Created by roman on 11/5/16.
 */
public class ChangePasswordPresenter implements ChangePasswordContract.Presenter {

    private ChangePasswordContract.View mView;
    private boolean mIsRepeat = false;

    public ChangePasswordPresenter(ChangePasswordContract.View view) {
        mView = view;
    }

    @Override
    public void passwordChanged(@NonNull String password) {
        PasswordAnalyzer passwordAnalyzer = new PasswordAnalyzer();
        passwordAnalyzer.calcPasswordStregth(password);
        mView.showPasswordStrength(passwordAnalyzer.getPasswordStrengthScore(), passwordAnalyzer.getPasswordStrengthDescription());
    }

    @Override
    public void userClickedOk() {
        if (!mIsRepeat) {
            mIsRepeat = true;
            mView.showInputRepeatNewPassword();
            mView.hidePasswordStrength();
        } else {
            mView.shwoConfirmChangePassowrd();
        }
    }

    @Override
    public void userClickedBack() {
        if (!mIsRepeat) {
            mView.showNotesActivity();
        } else {
            mIsRepeat = false;
            mView.showInputNewPassword();
            //set some basic password strength state when user didn't put anything in the edittext yet
            passwordChanged(" ");
        }
    }

    @Override
    public void start() {
        mView.showInputNewPassword();
        //set some basic password strength state when user didn't put anything in the edittext yet
        passwordChanged(" ");
    }


    @Override
    public void userClickedConfirmChangePassword() {
        mView.showNotesActivity();
    }
}
