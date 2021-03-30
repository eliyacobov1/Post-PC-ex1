package android.exercise.mini.interactions;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditTitleActivity extends AppCompatActivity {
  private static final String pageTitleMsg = "Page title here";
  private boolean isEditing = false;
  private CharSequence curTitle;
  FloatingActionButton fabStartEdit;
  FloatingActionButton fabEditDone;
  TextView textViewTitle;
  EditText editTextTitle;

   private void switchViewVisibility(View v1, View v2){
     v1.animate()
             .alpha(0f)
             .setDuration(300L)
             .start();
     v1.setVisibility(View.GONE);
     v2.setVisibility(View.VISIBLE);
     v2.animate()
             .alpha(1f)
             .setDuration(300L)
             .start();
   }

   private void findViews(){
     // find all views
     fabStartEdit = findViewById(R.id.fab_start_edit);
     fabEditDone = findViewById(R.id.fab_edit_done);
     textViewTitle = findViewById(R.id.textViewPageTitle);
     editTextTitle = findViewById(R.id.editTextPageTitle);
   }

   private void initializeViews(){
     // setup - start from static title with "edit" button
     fabStartEdit.setVisibility(View.VISIBLE);
     fabStartEdit.setAlpha(1f);
     fabEditDone.setVisibility(View.GONE);
     fabEditDone.setAlpha(0f);
     textViewTitle.setText(pageTitleMsg);
     textViewTitle.setVisibility(View.VISIBLE);
     textViewTitle.setAlpha(1f);
     editTextTitle.setText(pageTitleMsg);
     editTextTitle.setVisibility(View.GONE);
     editTextTitle.setAlpha(1f);
   }

  /**
   * this method hides the on-screen keyboard
    */
  private void hideSoftKeyboard() {
    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(
            Activity.INPUT_METHOD_SERVICE
    );
    inputMethodManager.hideSoftInputFromWindow(
            getCurrentFocus().getWindowToken(), 0
    );
  }

  /**
   * this method focuses the keyboard on the given input text
   */
  private void focusKeyboard(EditText editText){
    editText.requestFocus();
    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(
            Activity.INPUT_METHOD_SERVICE
    );
    inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_title);
    findViews();
    initializeViews();

    // handle clicks on "start edit"
    fabStartEdit.setOnClickListener(v -> {
      isEditing = true;
      CharSequence staticTitle = textViewTitle.getText();
      curTitle = staticTitle;
      switchViewVisibility(fabStartEdit, fabEditDone);
      // make sure the editable title's text is the same as the static one
      editTextTitle.setText(staticTitle);
      switchViewVisibility(textViewTitle, editTextTitle);
      focusKeyboard(editTextTitle); // focus keyboard on the edit text component
    });

    // handle clicks on "done edit"
    fabEditDone.setOnClickListener(v -> {
      isEditing = false;
      CharSequence editedTitle = editTextTitle.getText();
      curTitle = editedTitle;
      switchViewVisibility(fabEditDone, fabStartEdit);
      textViewTitle.setText(editedTitle);
      hideSoftKeyboard();
      switchViewVisibility(editTextTitle, textViewTitle);
    });
  }

  @Override
  public void onBackPressed() {
    // BACK button was clicked
    if(isEditing){
      // discard changes made to title
      findViews();
      textViewTitle.setText(curTitle);
      switchViewVisibility(editTextTitle, textViewTitle);
      switchViewVisibility(fabEditDone, fabStartEdit);
    }
    else super.onBackPressed();
  }
}