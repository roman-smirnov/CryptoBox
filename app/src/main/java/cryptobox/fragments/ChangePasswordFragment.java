package cryptobox.fragments;

import android.content.DialogInterface;
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
import cryptobox.contracts.ChangePasswordContract;
import cryptobox.listeners.PopBackStackListner;
import cryptobox.presenters.ChangePasswordPresenter;


/**
 * A fragment which displays the change password view
 * ANY ACTIVITY HOSTING THIS FRAGMENT MUST IMPLEMENT THE POPBACKSTACK LISTNER
 */
public class ChangePasswordFragment extends Fragment implements ChangePasswordContract.View, TextWatcher {
    private static final int MAX_PROGRESS_BAR = 10;

    private ChangePasswordContract.Presenter mPresenter;

    private TextInputLayout mPasswordTextInputLayout;
    private EditText mPasswordEditText;

    private TextView mPasswordStrengthTextView;
    private ProgressBar mPasswordStrengthProgressbar;

    private Button mOkButton;


    /**
     * mandatory empty constructor
     */
    public ChangePasswordFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPresenter = new ChangePasswordPresenter(this);

        mPasswordTextInputLayout = (TextInputLayout) getView().findViewById(R.id.activity_change_password_textinputlayout);
        mPasswordEditText = (EditText) getView().findViewById(R.id.activity_change_password_edittext);

        mPasswordEditText.addTextChangedListener(this);

        mPasswordStrengthTextView = (TextView) getView().findViewById(R.id.activity_change_password_password_strength_textview);

        mPasswordStrengthProgressbar = (ProgressBar) getView().findViewById(R.id.activity_change_password_password_strength_progressbar);
        mPasswordStrengthProgressbar.setMax(MAX_PROGRESS_BAR);
        mPasswordStrengthTextView.setText(getResources().getString(R.string.password_strength, " "));

        mOkButton = (Button) getView().findViewById(R.id.activity_change_password_ok_button);

        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.userClickedOk(mPasswordEditText.getText().toString());
            }
        });

        mPresenter.start();
    }

    @Override
    public void showInputNewPassword() {
        //show empty password at first
        mPasswordEditText.setText("");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.new_password_title);
        mPasswordTextInputLayout.setHint(getString(R.string.new_password_hint));
    }


    @Override
    public void showInputRepeatNewPassword() {
        //show empty password at first
        mPasswordEditText.setText("");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.repeat_password_title);
        mPasswordTextInputLayout.setHint(getString(R.string.repeat_password_hint));
    }

    @Override
    public void showPasswordStrength() {
        mPasswordStrengthTextView.setVisibility(View.VISIBLE);
        mPasswordStrengthProgressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void updatePasswordStrength(int passwordStrength, String passwordStrengthDescription) {
        mPasswordStrengthProgressbar.setProgress(passwordStrength);
        mPasswordStrengthTextView.setText(getString(R.string.password_strength, passwordStrengthDescription));
    }


    @Override
    public void hidePasswordStrength() {
        mPasswordStrengthTextView.setVisibility(View.GONE);
        mPasswordStrengthProgressbar.setVisibility(View.GONE);
    }

    @Override
    public void showNotesActivity() {
        ((PopBackStackListner) getActivity()).popBackStack();
    }

    public void onBackPressed() {
        mPresenter.userClickedBack();
    }


    /**
     * show a change password confirmation dialog
     */
    @Override
    public void showConfirmChangePassword() {
        new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AppCompatAlertDialogStyle))
                .setTitle(getResources().getString(R.string.change_password_setting))
                .setMessage(getResources().getString(R.string.question_delete_password))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.userClickedConfirmChangePassword();
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do  nothing
            }
        })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

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
        mPresenter.passwordChanged(mPasswordEditText.getText().toString());
    }

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



}
