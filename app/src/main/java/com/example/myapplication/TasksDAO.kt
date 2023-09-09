package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TasksDAO {

    @Query("select * from TasksEntity")
    fun getAll():LiveData<List<TasksEntity>>

    @Insert
    suspend fun insert(task: TasksEntity)

    @Delete
    suspend fun delete(task: TasksEntity)
}