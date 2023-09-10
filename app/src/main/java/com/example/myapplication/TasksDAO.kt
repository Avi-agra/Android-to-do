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

    @Query("delete from TasksEntity where id=:id")
    suspend fun delete(id: Int)
}