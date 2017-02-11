package cryptobox.contracts;

/**
 * Created by roman on 2/11/17.
 */

public interface ExplainContract {

    interface Presenter {
        void start(boolean isTwoPane);
    }

    interface View {
        void loadTwoPaneFragments();

        void loadSingleFragment();

        void setToolbar();
    }
}
