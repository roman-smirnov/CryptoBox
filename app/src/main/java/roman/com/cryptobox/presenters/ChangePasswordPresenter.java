package roman.com.cryptobox.presenters;

import android.support.annotation.NonNull;

import roman.com.cryptobox.contracts.ChangePasswordContract;

/**
 * Created by roman on 11/5/16.
 */

public class ChangePasswordPresenter implements ChangePasswordContract.Presenter {

    private ChangePasswordContract.View mView;

    public ChangePasswordPresenter(ChangePasswordContract.View view) {
        mView = view;
    }

    @Override
    public void passwordChanged(@NonNull String password) {

    }

    @Override
    public void userClickedOk() {

    }

    @Override
    public void userClickedBack() {

    }

    @Override
    public void start() {

    }
}
