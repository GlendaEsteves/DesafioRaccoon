package com.example.raccoon_todolist.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.raccoon_todolist.R
import com.example.raccoon_todolist.service.model.TaskModel
import com.example.raccoon_todolist.view.listeners.TaskListener
import com.example.raccoon_todolist.view.viewholder.TaskViewHolder

class TaskAdapter : RecyclerView.Adapter<TaskViewHolder>() {

    private var mTaskList: List<TaskModel> = arrayListOf()
    private lateinit var mListener: TaskListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.row_tasks, parent, false)
        return TaskViewHolder(item, mListener)
    }

    override fun getItemCount(): Int {
        return mTaskList.count()
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(mTaskList[position])
    }

    fun updateTasks(list: List<TaskModel>) {
        mTaskList = list
        notifyDataSetChanged()
    }


    fun attachListener(listener: TaskListener) {
        mListener = listener
    }

}