package com.example.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TasksViewModel(application: Application): AndroidViewModel(application) {

    val readAllLiveData: LiveData<List<TasksEntity>>
    private val repository: TasksRepository

    init {
        val tasksDao = AppDatabase.getDatabse(application).TasksDAO()
        readAllLiveData = tasksDao.getAll()
        repository = TasksRepository(tasksDao)
    }

    fun addTask(tasksEntity: TasksEntity){
        viewModelScope.launch( Dispatchers.IO ){
            repository.insertTask(tasksEntity)
        }
    }

    fun deleteTask(id: Int){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteTask(id)
        }
    }

}