package cryptobox.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import cryptobox.R;
import cryptobox.activities.CreateActivity;


/**
 * A fragment representing an item editor view
 */
public class ExplainFragment extends Fragment {

    private Button mCreateButton;

    /**
     * mandatory constructor
     */
    public ExplainFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explain, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCreateButton = (Button) view.findViewById(R.id.button_create);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // we need to hide the button and not auto-show the keyboard on tablet
        if (getResources().getBoolean(R.bool.has_two_panes)) {
            mCreateButton.setVisibility(View.GONE);
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        } else {
            mCreateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToCreateActivity();
                }
            });
        }

    }

    /**
     * go to the CreateActivity - the one in which a virgin user selects a password
     */
    private void goToCreateActivity() {
        //launch the permissions activity
        Intent intent = new Intent(getActivity(), CreateActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

}
