package com.example.task_master.taskmaster.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;

import com.amplifyframework.core.Amplify;
import com.example.task_master.R;
import com.example.task_master.taskmaster.DB.AppDB;
import com.example.task_master.taskmaster.Recyclerview.Task;
import com.example.task_master.taskmaster.ui.login.LoginActivity;


import java.util.ArrayList;
import java.util.List;

public class AddingTask extends AppCompatActivity {

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        List<com.amplifyframework.datastore.generated.model.Task> TasklistDB = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_task);
        Button addTask = findViewById(R.id.ADDTASKA);
        TextView TotalTasks = (TextView) findViewById(R.id.TotalTasksText1);
        addemage();
        addTask.setOnClickListener(v -> {
            Toast.makeText(this, "submitted", Toast.LENGTH_SHORT).show();
        });
        addTask.setOnClickListener(new View.OnClickListener() {
            private static final String TAG = "not working";

            @Override
            public void onClick(View view) {

                EditText Task_name = findViewById(R.id.Tasknamee);
                String Taskname = Task_name.getText().toString();

                EditText Task_body = findViewById(R.id.Taskbodyy);
                String Taskbody = Task_body.getText().toString();

                Spinner Task_Stast = findViewById(R.id.TaskStatee);
                String TaskStast = Task_Stast.getSelectedItem().toString();

                Task task = new Task(Taskname, Taskbody, TaskStast);
                System.out.println(task);

                com.amplifyframework.datastore.generated.model.Task item = com.amplifyframework.datastore.generated.model.Task
                        .builder()
                        .title(Taskname)
                        .description(Taskbody)
                        .status(TaskStast).build();
                System.out.println("***********************");
                System.out.println(item);
                System.out.println("*********************");

                Amplify.API.mutate(ModelMutation.create(item),
                        res -> {
                            Log.i("Tutorial", "Saved item: " + res.getData());
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                            finish();
                        }
                        , err -> {

                            Log.e("Tutorial", "Could not save item to DataStore", err);


                        });


            }
        });

    }

    private void addemage() {
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            } else if (type.startsWith("image/")) {
                handleSendImage(intent); // Handle single image being sent
            }
        } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleSendMultipleImages(intent); // Handle multiple images being sent
            }
        } else {
            // Handle other intents, such as being started from the home screen
        }
    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            // Update UI to reflect text being shared
        }
    }

    void handleSendImage(Intent intent) {
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            // Update UI to reflect image being shared
        }
    }

    void handleSendMultipleImages(Intent intent) {
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (imageUris != null) {
            // Update UI to reflect multiple images being shared
        }
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