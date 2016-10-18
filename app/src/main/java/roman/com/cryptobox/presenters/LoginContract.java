package roman.com.cryptobox.presenters;

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
         * implementation of transition to the notes list activity
         */
        void showNotesActivity();

        void showBadPassword();

        void showGoodPassword();
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
