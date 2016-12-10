package cryptobox.contracts;

import android.support.annotation.NonNull;

/**
 * Created by roman on 11/5/16.
 */

public interface ChangePasswordContract {

    interface View {

        void showInputNewPassword();

        void showInputRepeatNewPassword();

        void showPasswordStrength();

        void updatePasswordStrength(int passwordStrength, @NonNull String passwordStrengthDescription);

        void hidePasswordStrength();

        void showNotesActivity();

        void showConfirmChangePassword();

        void showError(@NonNull String errorText);

    }

    interface Presenter extends BasePresenterContract {

        void passwordChanged(@NonNull String password);

        void userClickedOk(@NonNull String password);

        void userClickedBack();

        void userClickedConfirmChangePassword();
    }
}
