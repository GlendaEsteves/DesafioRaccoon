package com.example.raccoon_todolist.view.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.raccoon_todolist.R
import com.example.raccoon_todolist.service.model.TaskModel
import com.example.raccoon_todolist.view.listeners.TaskListener

class TaskViewHolder(itemView: View, private val listener: TaskListener) :
    RecyclerView.ViewHolder(itemView) {

    fun bind(task: TaskModel) {

        val textName = itemView.findViewById<TextView>(R.id.text_name)

        textName.text = task.task

        textName.setOnClickListener {
            listener.onClick(task.id)
        }

    }
}