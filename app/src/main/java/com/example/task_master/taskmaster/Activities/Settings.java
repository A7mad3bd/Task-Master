package com.example.task_master.taskmaster.Activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;

import android.view.inputmethod.InputMethodManager;
import android.util.Log;
import android.widget.EditText;

import com.amplifyframework.core.Amplify;
import com.example.task_master.R;
import com.example.task_master.taskmaster.ui.login.LoginActivity;


public class Settings extends AppCompatActivity {
    private static final String TAG = Settings.class.getSimpleName();
    public static final String USERNAME = "UserName";
    private EditText mUsernameEditText;
    Button btnSubmit;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE);
        mUsernameEditText = findViewById(R.id.Write_your_username);
        btnSubmit = findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(view -> {
            Log.i(TAG, "Save button clicked");


//            // make sure the mUsernameEditText is greater than 10 characters
            if (mUsernameEditText.getText().toString().length() >= 4 && mUsernameEditText.getText().toString().length() <= 10) {
                saveUsername();
            } else {
                Toast.makeText(this, "Add a Username lenth 4-10", Toast.LENGTH_SHORT).show();
            }

            // Check if no view has focus: https://stackoverflow.com/questions/1109022/how-to-close-hide-the-android-soft-keyboard-programmatically
            View view2 = this.getCurrentFocus();
            if (view2 != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view2.getWindowToken(), 0);
            }
//            String username = mUsernameEditText.getText().toString();
        });

        mUsernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i(TAG, "onTextChanged: the text is : " + charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i(TAG, "afterTextChanged: the final text is : " + editable.toString());
                if (!btnSubmit.isEnabled()) {
                    btnSubmit.setEnabled(true);
                }

                if (editable.toString().length() == 0) {
                    btnSubmit.setEnabled(false);
                }

            }
        });
    }

    private void saveUsername() {

        String username = mUsernameEditText.getText().toString();

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("username",username);
        editor.apply();

        Intent intent=new Intent(Settings.this,MainActivity.class);
        startActivity(intent);
        finish();

        Toast.makeText(this, "Username Saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void navigateToSettings() {
        Intent settingsIntent = new Intent(this, Settings.class);
        startActivity(settingsIntent);
    }

    private void navigateToAllTasks() {
        Intent settingsIntent = new Intent(this, AllTasks.class);
        startActivity(settingsIntent);
    }

    private void navigateToAddTask() {
        Intent settingsIntent = new Intent(this, AddingTask.class);
        startActivity(settingsIntent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Homepage:
                navigateToMain();
                return true;
            case R.id.action_settings:
                navigateToSettings();
                return true;
            case R.id.Add_Task:
                navigateToAddTask();
                return true;
            case R.id.AllTasks:
                navigateToAllTasks();
                return true;
            case R.id.logout: {
                Amplify.Auth.signOut(
                        () -> {
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                        },
                        error -> Log.e("", error.toString())
                );
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void navigateToMain() {
        Intent settingsIntent = new Intent(this, MainActivity.class);
        startActivity(settingsIntent);
    }
}