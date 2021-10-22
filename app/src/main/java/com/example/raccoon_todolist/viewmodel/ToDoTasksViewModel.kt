package com.example.raccoon_todolist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.raccoon_todolist.service.constants.TaskConstants
import com.example.raccoon_todolist.service.model.TaskModel
import com.example.raccoon_todolist.service.repository.TaskRepository

class ToDoTasksViewModel(application: Application): AndroidViewModel(application) {

    private val mTaskRepository = TaskRepository.getInstance(application.applicationContext)

    private val mTaskList = MutableLiveData<List<TaskModel>>()
    val taskList: LiveData<List<TaskModel>> = mTaskList

    fun load(filter: Int) {

        if (filter == TaskConstants.FILTER.PENDENT) {
            mTaskList.value = mTaskRepository.pendentTasks()
        } else {
            mTaskList.value = mTaskRepository.completedTasks()
        }
    }

    fun delete(id: Int) {
        mTaskRepository.delete(id)
    }
}