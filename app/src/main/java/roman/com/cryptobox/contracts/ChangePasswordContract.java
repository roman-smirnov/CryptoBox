package roman.com.cryptobox.contracts;

import android.support.annotation.NonNull;

/**
 * Created by roman on 11/5/16.
 */

public interface ChangePasswordContract {

    interface View {

        void showInputNewPassword();

        void showInputRepeatNewPassword();

        void showPassWordChanged();

        void showPasswordStrength(int passwordStrength, @NonNull String passwordStrengthDescription);

        void hidePasswordStrength();

        void showNotesActivity();

        void shwoConfirmChangePassowrd();

    }

    interface Presenter extends BasePresenterContract {

        void passwordChanged(@NonNull String password);

        void userClickedOk();

        void userClickedBack();

        void userClickedChangePassword();

        void userClickedChangeConfirmed();
    }
}
