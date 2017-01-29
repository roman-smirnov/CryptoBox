package cryptobox.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.testfairy.TestFairy;

import cryptobox.R;
import cryptobox.activities.NotesActivity;
import cryptobox.contracts.LoginContract;
import cryptobox.presenters.LoginPresenter;


/**
 * A fragment representing an item editor view
 */
public class LoginFragment extends Fragment implements LoginContract.View {

    //the presenter(logic module)
    private LoginContract.Presenter mPresenter;

    // UI references.
    private EditText mPasswordEditText;
    private TextInputLayout mTextInputLayout;
    private Button mLoginButton;

    /**
     * mandatory constructor
     */
    public LoginFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new LoginPresenter(this);

        //get the views
        mPasswordEditText = (EditText) view.findViewById(R.id.fragment_login_edittext_password_login);
        mTextInputLayout = (TextInputLayout) view.findViewById(R.id.fragment_login_textinputlayout_password);
        mLoginButton = (Button) view.findViewById(R.id.fragment_login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickLoginButton(view);
            }
        });
    }

    /**
     * a listener method for the login button click event
     *
     * @param view the login button
     */
    public void onClickLoginButton(View view) {
        //call the verification logic on the presenter
        mPresenter.loginButtonClicked(mPasswordEditText.getText().toString());
    }


    /**
     * go to NotesActivity - the ones with the list view of all notes
     */
    @Override
    public void showNotesActivity() {
        //launch the notes activity
        TestFairy.addEvent("Logged in successfully.");
        Intent intent = new Intent(getActivity(), NotesActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    /**
     * show user an error demonstration that the password was incorrect
     */
    @Override
    public void showPasswordBad() {
        mTextInputLayout.setError(getString(R.string.error_incorrect_password));
        mPasswordEditText.requestFocus();
    }
}
