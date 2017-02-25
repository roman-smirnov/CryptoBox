package cryptobox.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import cryptobox.R;
import cryptobox.contracts.EditorContract;
import cryptobox.database.DataLoader;
import cryptobox.dataobjects.Note;
import cryptobox.listeners.PopBackStackListner;
import cryptobox.presenters.EditorFragmentPresenter;

import static com.google.common.base.Preconditions.checkNotNull;
import static cryptobox.contracts.EditorContract.DEFAULT_NOTE_BUNDLE_RETURN_VALUE;


/**
 * A fragment representing an item editor view
 */
public class EditorFragment extends Fragment implements EditorContract.View {
    private EditorContract.Presenter mPresenter;
    private EditText mDateEditText;
    private EditText mTitleEditText;
    private EditText mContentEditText;
    private MenuItem mMenuItem;

    /**
     * mandatory constructor
     */
    public EditorFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editor, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = new EditorFragmentPresenter(this, DataLoader.getInstance());

        //get the views
        mDateEditText = (EditText) getView().findViewById(R.id.activity_editor_note_date);

        mDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDate();
            }
        });


        mTitleEditText = (EditText) getView().findViewById(R.id.activity_editor_note_title);

        mTitleEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickTitle(view);
            }
        });

        mContentEditText = (EditText) getView().findViewById(R.id.activity_editor_note_content);
        mContentEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickContent(view);
            }
        });

        //get the note from the intent
        Intent intent = getActivity().getIntent();
        int intentExtraParam = (int) intent.getLongExtra(Note.NOTE_KEY_STRING, DEFAULT_NOTE_BUNDLE_RETURN_VALUE);
        mPresenter.openNote(intentExtraParam);
    }

    @Override
    public void onStart() {
        mPresenter.start();
        super.onStart();
    }

    @Override
    public void onStop() {
        mPresenter.saveNote(mTitleEditText.getText().toString(), mContentEditText.getText().toString());
        super.onStop();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.activity_editor_menu, menu);
        mMenuItem = menu.findItem(R.id.activity_editor_edit_icon);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go to parent activity.
                onBackPressed();
                return true;
            case R.id.activity_editor_edit_icon:
                mPresenter.toggleEditState(null);
                return true;
            case R.id.activity_editor_delete_icon:
                mPresenter.deleteNoteSelected();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * the date editText click listener
     */
    public void onClickDate() {
        //do nothing
    }

    /**
     * the title edtiText click listener
     */
    public void onClickTitle(View view) {
        mPresenter.toggleEditState(view);
    }

    /**
     * the content edtiText click listener
     */
    public void onClickContent(View view) {
        mPresenter.toggleEditState(view);
    }

    /**
     * show the user a specific note
     *
     * @param n
     */
    @Override
    public void showNote(Note n) {
        //set the text to the views
        mDateEditText.setText(n.getLastModified());
        mTitleEditText.setText(n.getTitle());
        mContentEditText.setText(n.getContent());
    }

    /**
     * make all the edittext edtitable and change the toolbar icon to V icon
     */
    @Override
    public void makeViewsEditable() {
        //set title stuff
        mTitleEditText.setFocusableInTouchMode(true);
        mTitleEditText.setFocusable(true);
        mTitleEditText.setBackground(getResources().getDrawable(R.drawable.bg_edit_text, getActivity().getTheme()));
        //set content stuff
        mContentEditText.setFocusableInTouchMode(true);
        mContentEditText.setFocusable(true);
        mContentEditText.setBackground(getResources().getDrawable(R.drawable.bg_edit_text, getActivity().getTheme()));

        mMenuItem.setIcon(getResources().getDrawable(R.drawable.ic_done, getActivity().getTheme()));
    }

    /**
     * make all the edittext uneditable and change the toolbar icon to a pencil icon
     */
    @Override
    public void makeViewsUneditable() {
        closeKeyboard();
        //set title stuff
        mTitleEditText.setFocusable(false);
        mTitleEditText.setBackgroundColor(Color.TRANSPARENT);
        //set content stuff
        mContentEditText.setFocusable(false);
        mContentEditText.setBackgroundColor(Color.TRANSPARENT);
        mMenuItem.setIcon(getResources().getDrawable(R.drawable.ic_edit, getActivity().getTheme()));
    }

    /**
     * force open the keyboard
     *
     * @param view the currently focused view
     */
    private void openKeyboard(@NonNull View view) {
        checkNotNull(view);
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * * force close the keyboard
     */
    private void closeKeyboard() {
        View currentFocus = getActivity().getCurrentFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void focusOnView(@NonNull View view) {
        checkNotNull(view);
        view.requestFocus();
        openKeyboard(view);
    }

    public void onBackPressed() {
        mPresenter.saveNote(mTitleEditText.getText().toString(), mContentEditText.getText().toString());
        ((PopBackStackListner) getActivity()).popBackStack();
    }

    @Override
    public void closeEditorView() {
        getActivity().finish();
    }

    /**
     * show a delete confirmation dialog
     */
    @Override
    public void showDeleteConfirmation() {
        //TODO move to strings resources
        new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AppCompatAlertDialogStyle))
                .setTitle(R.string.confirm_delete_note_title)
                .setMessage(R.string.confirm_delete_note_message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deleteNoteConfirmed();
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
    public void showEmptyTitleMessage() {
        // how is the user supposed to exit without saving a note?
    }

    /**
     * show a toast with a message saying the note was saved
     */
    @Override
    public void showSavedMessage() {
// removed becase it feels weird showing this on tablet while in two-pane mode
//        Toast.makeText(getContext(), "Saved Note", Toast.LENGTH_SHORT).show();
    }

}
