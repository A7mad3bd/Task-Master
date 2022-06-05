package com.example.task_master.taskmaster.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.task_master.R;
import com.example.task_master.taskmaster.DB.AppDB;
import com.example.task_master.taskmaster.Recyclerview.Task;
//import com.example.task_master.taskmaster.Recyclerview.ViewAdapter;

public class Task_Detail extends AppCompatActivity {


    private static final String TAG = "......";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        getData();


    }

    public void getData() {

        Intent intent1 = getIntent();
        String title =intent1.getStringExtra("title");
        String body =intent1.getStringExtra("body");
        String state =intent1.getStringExtra("state");

        Task task = AppDB.getInstance(getApplicationContext()).taskDao().getTaskByTitle(title);
//        Log.d(TAG, "getData: "+task.title);
        TextView Task_Title__String = findViewById(R.id.Task_name);
        TextView Task_Body_String = findViewById(R.id.Task_Body);
        TextView Task_State_String = findViewById(R.id.Task_State);
        Task_Title__String.setText(title);
        Task_Body_String.setText(body);
        Task_State_String.setText(state);



    }
}