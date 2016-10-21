package roman.com.cryptobox.contracts;

import android.support.annotation.NonNull;


/**
 * this interface is a contract between the LoginActivity (the view) and it's logic module (the presenter)
 */
public interface LoginContract {

    /**
     * implemented by the login view
     */
    interface View {
        /**
         * transition to the notes list activity
         */
        void showNotesActivity();

        /**
         * show the user the password was incorrect
         */
        void showPasswordBad();

    }

    /**
     * implemented by the login presenter
     */
    interface Presenter {
        /**
         * implementation of logic when login button is clicked on the view
         *
         * @param password
         */
        void loginButtonClicked(@NonNull String password);
    }
}
