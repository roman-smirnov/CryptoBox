package cryptobox.presenters;

import cryptobox.contracts.ExplainContract;

/**
 * Created by roman on 2/11/17.
 */

public class ExplainPresenter implements ExplainContract.Presenter {
    private ExplainContract.View mView;

    public ExplainPresenter(ExplainContract.View view) {
        mView = view;
    }

    @Override
    public void start(boolean isTwoPane) {
        if (isTwoPane) {
            mView.setToolbar();
            mView.loadTwoPaneFragments();
        } else {
            mView.loadSingleFragment();
        }
    }
}
