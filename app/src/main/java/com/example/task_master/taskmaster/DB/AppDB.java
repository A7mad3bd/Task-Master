package com.example.task_master.taskmaster.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.task_master.taskmaster.Recyclerview.Task;

@Database(entities = {Task.class}, version = 1)
public abstract class AppDB extends RoomDatabase {
    public abstract TaskDAO taskDao();


    //1
    private static AppDB appDatabase; //declaration for the instance

    //2
    public AppDB() {

    }

    //3
    public static synchronized AppDB getInstance(Context context) {

        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(context,
                    AppDB.class, "appdb").allowMainThreadQueries().build();
        }
        return appDatabase;
    }
}
