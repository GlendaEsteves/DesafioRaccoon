package com.example.raccoon_todolist.service.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.raccoon_todolist.service.constants.DataBaseConstants

class TaskDataBaseHandler(context: Context) : SQLiteOpenHelper (context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_TASKS)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Tasks.db"

        private const val CREATE_TABLE_TASKS =
            ("create table " + DataBaseConstants.TASK.TABLE_NAME + " ("
                    + DataBaseConstants.TASK.COLUMNS.ID + " integer primary key autoincrement, "
                    + DataBaseConstants.TASK.COLUMNS.TASK + " text, "
                    + DataBaseConstants.TASK.COLUMNS.COMPLETION + " integer);")
    }
}