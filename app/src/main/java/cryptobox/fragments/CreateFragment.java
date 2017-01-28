package cryptobox.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import cryptobox.R;
import cryptobox.activities.NotesActivity;
import cryptobox.contracts.CreateContract;
import cryptobox.presenters.CreatePresenter;


/**
 * A fragment which displays the create view
 */
public class CreateFragment extends Fragment implements CreateContract.View, TextWatcher {
    private CreateContract.Presenter mPresenter;
    //maximum score for progress bar
    private final static int MAX_PROGRESS_BAR = 10;
    private Button mButton;
    private TextInputLayout mPasswordTextInputLayout;
    private EditText mPasswordEditText;
    private TextView mPasswordStrengthTextView;
    private ProgressBar mPasswordStrengthProgressbar;

    /**
     * mandatory construcot
     */
    public CreateFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = new CreatePresenter(this);

        mButton = (Button) getView().findViewById(R.id.activity_create_button);

        mPasswordTextInputLayout = (TextInputLayout) getView().findViewById(R.id.activity_create_textinputlayout);
        mPasswordEditText = (EditText) getView().findViewById(R.id.activity_create_edittext);
        mPasswordStrengthTextView = (TextView) getView().findViewById(R.id.activity_create_textview);
        mPasswordStrengthProgressbar = (ProgressBar) getView().findViewById(R.id.activity_create_progressbar);
        mPasswordStrengthProgressbar.setMax(MAX_PROGRESS_BAR);
//        mPasswordStrengthTextView.setText(getResources().getString(R.string.password_strength, " "));

        mPasswordEditText.addTextChangedListener(this);

        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mPresenter.userClickedOk(mPasswordEditText.getText().toString());
            }
        });

        mPresenter.start();
    }

    /**
     * go to NotesActivity - the ones with the list view of all notes
     */
    private void gotToNotesActivity() {
        //launch the permissions activity
        Intent intent = new Intent(getActivity(), NotesActivity.class);
        startActivity(intent);
        return;
    }


    /**
     * show the user the "input new password" field
     */
    @Override
    public void showInputNewPassword() {
        mPasswordEditText.setText("");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.new_password_title));
        mPasswordTextInputLayout.setHint(getString(R.string.new_password_hint));
    }

    /**
     * show the user the "repeat password" field
     */
    @Override
    public void showInputRepeatNewPassword() {
        mPasswordEditText.setText("");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.repeat_password_title));
        mPasswordTextInputLayout.setHint(getString(R.string.repeat_password_hint));
    }


    /**
     * make the password strength progress bar and editetext visible
     */
    @Override
    public void showPasswordStrength() {
        mPasswordStrengthProgressbar.setVisibility(View.VISIBLE);
        mPasswordStrengthTextView.setVisibility(View.VISIBLE);
        updatePasswordStrength(0, " ");
    }

    /**
     * set the password strength bar progress and the textview text
     *
     * @param passwordStrength
     * @param passwordStrengthDescription does not make the views visible if they're hidden (call showPasswordStregth for this)
     */
    @Override
    public void updatePasswordStrength(int passwordStrength, @NonNull String passwordStrengthDescription) {
        mPasswordStrengthProgressbar.setProgress(passwordStrength);
        mPasswordStrengthTextView.setText(getString(R.string.password_strength, passwordStrengthDescription));
    }

    /**
     * stop the password strength progress bar and editetext from being displayed
     */
    @Override
    public void hidePasswordStrength() {
        mPasswordStrengthProgressbar.setVisibility(View.GONE);
        mPasswordStrengthTextView.setVisibility(View.GONE);
    }

    /**
     * go to the notes activity
     */
    @Override
    public void showNotesActivity() {
        Intent intent = new Intent(getActivity(), NotesActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    /**
     * kill the current activity
     */
    @Override
    public void exitApplication() {
        getActivity().finish();
    }

    /**
     * handle stuff when user clicked the back button
     */
    public void onBackPressed() {
        mPresenter.userClickedBack();
    }

    /**
     * show an error dialog to the user
     *
     * @param errorMessage
     */
    @Override
    public void showError(@NonNull String errorMessage) {
//        TODO move string to values.string
        new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AppCompatAlertDialogStyle))
                .setTitle("Password Error")
                .setMessage(errorMessage)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /////////////////////////////////////////
    /// stuff for the textChange listener ///
    /////////////////////////////////////////

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //do nothing
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //do nothing
    }

    @Override
    public void afterTextChanged(Editable s) {
        //update the password strength when the text changes
        mPresenter.passwordChanged(mPasswordEditText.getText().toString());
    }

}

