package com.example.task_master.taskmaster.DB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.task_master.taskmaster.Recyclerview.Task;

import java.util.List;

@Dao
public interface TaskDAO {

    @Query("SELECT * FROM tasks")
    List<Task> getAll();

    @Query("SELECT * FROM tasks WHERE id = :id")
    Task getTaskByID(int id);

    @Query("SELECT * FROM tasks WHERE title = :title")
    Task getTaskByTitle(String title);

    @Insert
    Long insertTask(Task t);
}
