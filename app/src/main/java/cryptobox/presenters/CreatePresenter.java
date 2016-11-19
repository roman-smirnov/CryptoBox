

package cryptobox.presenters;

import android.support.annotation.NonNull;

import cryptobox.contracts.CreateContract;
import cryptobox.utils.PasswordAnalyzer;

/**
 * Created by roman on 11/5/16.
 */
public class CreatePresenter implements CreateContract.Presenter {

    private CreateContract.View mView;
    private boolean mIsRepeat = false;

    public CreatePresenter(CreateContract.View view) {
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
}
