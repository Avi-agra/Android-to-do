package com.example.myapplication

import androidx.lifecycle.LiveData

class TasksRepository(private val tasksDAO: TasksDAO) {
    val readAllData: LiveData<List<TasksEntity>> = tasksDAO.getAll()

    suspend fun insertTask(tasksEntity: TasksEntity){
        tasksDAO.insert(tasksEntity)
    }

    suspend fun deleteTask(id: Int){
        tasksDAO.delete(id)
    }
}