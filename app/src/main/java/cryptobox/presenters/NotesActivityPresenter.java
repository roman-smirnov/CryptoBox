package cryptobox.presenters;

import cryptobox.contracts.NotesActivityContract;

/**
 * Created by roman on 2/11/17.
 */

public class NotesActivityPresenter implements NotesActivityContract.Presenter {
    private NotesActivityContract.View mView;

    public NotesActivityPresenter(NotesActivityContract.View view) {
        mView = view;
    }

    @Override
    public void start(boolean isTwoPane) {
        if (isTwoPane) {
            mView.loadTwoPaneFragments();
        } else {
            mView.loadSingleFragment();
        }
    }
}
