package com.example.raccoon_todolist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.raccoon_todolist.service.model.TaskModel
import com.example.raccoon_todolist.service.repository.TaskRepository

class TaskFormViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private var mTaskRepository: TaskRepository = TaskRepository.getInstance(mContext)

    private var mTask = MutableLiveData<Boolean>()
    val saveTask : LiveData<Boolean> = mTask

    fun save(id: Int, task: String, presence: Boolean) {
        val task = TaskModel(id, task, presence)

        if (id == 0) {
            mTask.value = mTaskRepository.save(task)
        } else {
            mTask.value = mTaskRepository.update(task)
        }
    }
}