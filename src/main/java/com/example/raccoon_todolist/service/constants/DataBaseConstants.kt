package com.example.raccoon_todolist.service.constants

class DataBaseConstants private constructor() {

    object TASK {
        const val TABLE_NAME = "Tasks"

        object COLUMNS{
            const val ID = "id"
            const val TASK = "task"
            const val COMPLETION = "completion"
        }
    }
}