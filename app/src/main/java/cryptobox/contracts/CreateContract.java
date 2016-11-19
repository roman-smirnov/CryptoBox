package cryptobox.contracts;

import android.support.annotation.NonNull;

/**
 * Created by roman on 11/5/16.
 */

public interface CreateContract {

    interface View {

        void showInputNewPassword();

        void showInputRepeatNewPassword();

        void showPasswordStrength(int passwordStrength, @NonNull String passwordStrengthDescription);

        void hidePasswordStrength();

        void showNotesActivity();

    }

    interface Presenter extends BasePresenterContract {

        void passwordChanged(@NonNull String password);

        void userClickedOk();

        void userClickedBack();

    }
}
