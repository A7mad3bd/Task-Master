package com.example.task_master.taskmaster.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.example.task_master.R;
import com.example.task_master.taskmaster.Activities.MainActivity;
import com.example.task_master.taskmaster.Activities.Settings;
import com.example.task_master.taskmaster.Activities.Task_Detail;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();
    public static final String EMAIL = "email";
    private ProgressBar loadingProgressBar;
    SharedPreferences sharedPreferences;
    EditText usernameEditText;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);

        final EditText emailEditText = findViewById(R.id.email);
        usernameEditText = findViewById(R.id.userrname);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button signUpButton = findViewById(R.id.sign_up);
        loadingProgressBar = findViewById(R.id.loading);




        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEND) {
                    signUpButton.setEnabled(true);
                }
                signUpButton.setEnabled(true);
                return false;
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);

                signUp(emailEditText.getText().toString(),usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
    }


    private void saveUsername() {
        String username = usernameEditText.getText().toString();
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("usernamesignup", username);
        editor.apply();
    }

    private void signUp(String email,String username, String password) {

        AuthSignUpOptions options = AuthSignUpOptions.builder()
                .userAttribute(AuthUserAttributeKey.email(), email)
                .userAttribute(AuthUserAttributeKey.nickname(), username)
                .build();

        Amplify.Auth.signUp(email, password, options,
                result -> {
                    Log.i(TAG, "Result: " + result.toString());
                    loadingProgressBar.setVisibility(View.INVISIBLE);
                    saveUsername();
                    Intent intent = new Intent(SignUpActivity.this, VerificationActivity.class);
                    intent.putExtra(EMAIL, email);
                    startActivity(intent);

                    finish();
                },
                error -> {
                    Log.e(TAG, "Sign up failed", error);

                }
        );

    }
}