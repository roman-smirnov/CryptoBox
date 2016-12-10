package cryptobox.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


import cryptobox.R;
import cryptobox.contracts.EditorContract;
import cryptobox.database.DataLoader;
import cryptobox.dataobjects.Note;
import cryptobox.presenters.EditorPresenter;

import static com.google.common.base.Preconditions.checkNotNull;
import static cryptobox.contracts.EditorContract.DEFAULT_NOTE_BUNDLE_RETURN_VALUE;

/**
 * a view activity that allows the user to create and edit notes
 */
public class EditorActivity extends AppCompatActivity implements EditorContract.View {

    private EditorContract.Presenter mPresenter;
    private EditText mDateEditText;
    private EditText mTitleEditText;
    private EditText mContentEditText;
    private MenuItem mMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mPresenter = new EditorPresenter(this, DataLoader.getInstance());

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_editor_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get the views
        mDateEditText = (EditText) findViewById(R.id.activity_editor_note_date);
        mTitleEditText = (EditText) findViewById(R.id.activity_editor_note_title);
        mContentEditText = (EditText) findViewById(R.id.activity_editor_note_content);


        //get the note from the intent
        Intent intent = getIntent();
        int intentExtraParam = (int) intent.getLongExtra(Note.NOTE_KEY_STRING, DEFAULT_NOTE_BUNDLE_RETURN_VALUE);
        mPresenter.openNote(intentExtraParam);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_editor_menu, menu);
        mMenuItem = menu.findItem(R.id.activity_editor_edit_icon);
        return true;
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
     *
     * @param view
     */
    public void onClickDate(View view) {
        //do nothing
    }

    /**
     * the title edtiText click listener
     *
     * @param view
     */
    public void onClickTitle(View view) {
        mPresenter.toggleEditState(view);
        view.requestFocus();
    }

    /**
     * the content edtiText click listener
     *
     * @param view
     */
    public void onClickContent(View view) {
        mPresenter.toggleEditState(view);
        view.requestFocus();
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
        mTitleEditText.setBackground(getResources().getDrawable(R.drawable.bg_edit_text, getTheme()));
        //set content stuff
        mContentEditText.setFocusableInTouchMode(true);
        mContentEditText.setFocusable(true);
        mContentEditText.setBackground(getResources().getDrawable(R.drawable.bg_edit_text, getTheme()));

        mMenuItem.setIcon(getResources().getDrawable(R.drawable.ic_done, getTheme()));
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
        mMenuItem.setIcon(getResources().getDrawable(R.drawable.ic_edit, getTheme()));
    }

    /**
     * force open the keyboard
     *
     * @param view the currently focused view
     */
    private void openKeyboard(@NonNull View view) {
        checkNotNull(view);
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * * force close the keyboard
     */
    private void closeKeyboard() {
        View currentFocus = getCurrentFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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

    @Override
    public void onBackPressed() {
        mPresenter.saveNote(mTitleEditText.getText().toString(), mContentEditText.getText().toString());
        super.onBackPressed();
    }

    @Override
    public void closeEditorView() {
        finish();
    }

    /**
     * show a delete confirmation dialog
     */
    @Override
    public void showDeleteConfirmation() {
        new AlertDialog.Builder(this)
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
}

