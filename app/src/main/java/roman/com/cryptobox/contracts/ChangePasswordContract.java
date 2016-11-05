package roman.com.cryptobox.contracts;

import android.support.annotation.NonNull;

/**
 * Created by roman on 11/5/16.
 */

public interface ChangePasswordContract {

    interface View {
        void showCurrentPasswordBad();

        void hideCurrentPasswordBad();

        void showInputCurrentPassword();

        void hideInputCurrentPassword();

        void showInputNewPassword();

        void hideInputNewPassword();

        void showInputRepeatNewPassword();

        void hideInputRepeatNewPassword();

        void showPassWordChanged();

        void showPasswordStrength(int passwordStrength);

        void hidePasswordStrength();

        void showNotesActivity();

    }

    interface Presenter extends BasePresenterContract {

        void passwordChanged(@NonNull String password);

        void userClickedOk();

        void userClickedBack();
    }
}
