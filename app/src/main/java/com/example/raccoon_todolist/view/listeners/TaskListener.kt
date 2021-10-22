package com.example.raccoon_todolist.view.listeners

interface TaskListener {
    fun onClick(id: Int)
    fun onDelete(id: Int)
}