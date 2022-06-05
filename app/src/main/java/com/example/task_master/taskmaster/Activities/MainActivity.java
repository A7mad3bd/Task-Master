package com.example.task_master.taskmaster.Activities;

import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.datastore.AWSDataStorePlugin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.core.Amplify;

import com.example.task_master.R;


import com.amplifyframework.api.graphql.model.ModelQuery;
import com.example.task_master.taskmaster.Recyclerview.ViewAdapter;
import com.example.task_master.taskmaster.ui.login.LoginActivity;


import java.util.ArrayList;
import java.util.List;





public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private static final String TAG1 = MainActivity.class.getSimpleName();
    private Handler handler;
    private Button button, button1, button2, button3, button4;
    private TextView mUsernameText;
    ViewAdapter myadapter;
    SharedPreferences sharedpreferencesmain;
    List<com.amplifyframework.datastore.generated.model.Task> TasklistDB = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.ADDTASK);
        mUsernameText = (TextView) findViewById(R.id.username);
        button1 = (Button) findViewById(R.id.AllTasks);
        button2 = (Button) findViewById(R.id.Settings);

//        configureAmplify();
        SetUserName();
        SetAdapter();
        OnclistButtons();

        Log.i(TAG, "onCreate: Called");
    }

    private void SendCountTasks() {
        Intent Total = new Intent(getApplicationContext(), AddingTask.class);
        Total.putExtra("TotalTasks", TasklistDB.size());
    }

    private void fetch() {
        Amplify.API.query(ModelQuery.list(com.amplifyframework.datastore.generated.model.Task.class), res ->
                {
                    TasklistDB.clear();
                    if (res.hasData()) {
                        for (com.amplifyframework.datastore.generated.model.Task task : res.getData()) {
                            TasklistDB.add(task);
                        }
                    }
                    runOnUiThread(() -> {
                        myadapter.notifyDataSetChanged();
                    });
                }
                , err -> {
                });
    }




    private void SetUserName() {
        sharedpreferencesmain = getSharedPreferences("SHARED_PREF", MODE_PRIVATE);
        String usernamemain = sharedpreferencesmain.getString("username", "");
        mUsernameText.setText(usernamemain);


    }

    private void OnclistButtons() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity1();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Settings = new Intent(MainActivity.this, Settings.class);
                startActivity(Settings);
            }
        });

    }


    private void SetAdapter() {
        RecyclerView rrecyclerview = findViewById(R.id.rere);
        ;
        myadapter = new ViewAdapter(TasklistDB, new ViewAdapter.MyOnClickListener() {
            @Override

            public void onClicked(com.amplifyframework.datastore.generated.model.Task task) {
                Intent taskDetailActivity = new Intent(getApplicationContext(), Task_Detail.class);
                taskDetailActivity.putExtra("title", task.getTitle());
                taskDetailActivity.putExtra("body", task.getDescription());
                taskDetailActivity.putExtra("state", task.getStatus());

                startActivity(taskDetailActivity);
            }
        });
        rrecyclerview.setAdapter(myadapter);
        rrecyclerview.setHasFixedSize(true);
        rrecyclerview.setLayoutManager(new
                LinearLayoutManager(this));

    }


    public void openActivity1() {
        Intent intent = new Intent(MainActivity.this, AllTasks.class);
        startActivity(intent);
    }

    public void openActivity2() {
        Intent intent = new Intent(MainActivity.this, AddingTask.class);
        startActivity(intent);
    }

    public void getusername() {
        TextView textView = findViewById(R.id.username);
        String task = getIntent().getExtras().get("username").toString();
        textView.setText(task);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

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
//
//    private void navigateToMain() {
//        Intent settingsIntent = new Intent(this, MainActivity.class);
//        startActivity(settingsIntent);
//    }
@Override
public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main, menu);
    MenuItem usernamemenu = menu.findItem(R.id.usernamee);
//    Intent intent1 = getIntent();
//    String usernamesent =intent1.getStringExtra("usernamemenu");
//    usernamemenu.setTitle(usernamesent);
    SharedPreferences sharedPreferences = PreferenceManager
            .getDefaultSharedPreferences(getApplicationContext());
    String name = sharedPreferences.getString("usernamesignup", " ");
    usernamemenu.setTitle(name);

    Amplify.Auth.fetchUserAttributes(
            attributes -> {
                usernamemenu.setTitle( attributes.get(2).getValue().toString());
                mUsernameText.setText( attributes.get(2).getValue().toString());
            },
            error -> Log.e(TAG, "error", error)
    );

    return true;
}


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.Homepage:
//                navigateToMain();
//                return true;
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

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: called");
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume: called - The App is VISIBLE");
        fetch();
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause: called");
        super.onPause();

    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop: called");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy: called");
        super.onDestroy();
    }

}





