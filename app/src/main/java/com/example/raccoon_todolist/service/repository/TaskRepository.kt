package com.example.raccoon_todolist.service.repository

import android.content.ContentValues
import android.content.Context
import com.example.raccoon_todolist.service.constants.DataBaseConstants
import com.example.raccoon_todolist.service.model.TaskModel

class TaskRepository private constructor(context: Context) {

    private var mTaskDataBaseHandler: TaskDataBaseHandler = TaskDataBaseHandler(context)

    companion object {
        private lateinit var repository: TaskRepository

        fun getInstance(context: Context): TaskRepository {
            if (!::repository.isInitialized) {
                repository = TaskRepository(context)
            }
            return repository
        }
    }

    fun get(id: Int): TaskModel? {

        var mtask: TaskModel? = null
        return try {
            val db = mTaskDataBaseHandler.readableDatabase

            val projection = arrayOf(
                DataBaseConstants.TASK.COLUMNS.TASK,
                DataBaseConstants.TASK.COLUMNS.COMPLETION
            )

            val selection = DataBaseConstants.TASK.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            val cursor = db.query(
                DataBaseConstants.TASK.TABLE_NAME,
                projection,
                selection,
                args,
                null,
                null,
                null
            )

            if (cursor != null && cursor.count > 0) {
                cursor.moveToFirst()

                val task = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.TASK))
                val completion = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.COMPLETION)) == 1)

                mtask = TaskModel(id, task, completion)
            }

            cursor?.close()
            mtask
        } catch (e: Exception) {
            mtask
        }
    }

    fun pendentTasks(): List<TaskModel> {
        val list: MutableList<TaskModel> = ArrayList()
        return try {
            val db = mTaskDataBaseHandler.readableDatabase

            val cursor = db.rawQuery("SELECT id, task, completion FROM Tasks WHERE completion = 1", null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {

                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.ID))
                    val taskName = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.TASK))
                    val completion = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.COMPLETION)) == 1)

                    val task = TaskModel(id, taskName, completion)
                    list.add(task)
                }
            }

            cursor?.close()
            list
        } catch (e: Exception) {
            list
        }
    }

    fun completedTasks(): List<TaskModel> {
        val list: MutableList<TaskModel> = ArrayList()
        return try {
            val db = mTaskDataBaseHandler.readableDatabase

            val cursor = db.rawQuery("SELECT id, task, completion FROM Tasks WHERE completion = 0", null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {

                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.ID))
                    val taskName = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.TASK))
                    val completion = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.COMPLETION)) == 0)

                    val task = TaskModel(id, taskName, completion)
                    list.add(task)
                }
            }

            cursor?.close()
            list
        } catch (e: Exception) {
            list
        }
    }

    fun save(task: TaskModel): Boolean {
        return try {

            val db = mTaskDataBaseHandler.writableDatabase

            val contentValues = ContentValues()
            contentValues.put(DataBaseConstants.TASK.COLUMNS.TASK, task.task)
            contentValues.put(DataBaseConstants.TASK.COLUMNS.COMPLETION, task.completion)
            db.insert(DataBaseConstants.TASK.TABLE_NAME, null, contentValues)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun update(task: TaskModel): Boolean{
        return try {
            val db = mTaskDataBaseHandler.writableDatabase

            val contentValues = ContentValues()
            contentValues.put(DataBaseConstants.TASK.COLUMNS.TASK, task.task)
            contentValues.put(DataBaseConstants.TASK.COLUMNS.COMPLETION, task.completion)

            val selection = DataBaseConstants.TASK.COLUMNS.ID + " = ?"
            val args = arrayOf(task.id.toString())

            db.update(DataBaseConstants.TASK.TABLE_NAME, contentValues, selection, args)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun delete(id: Int): Boolean {
        return try {
            val db = mTaskDataBaseHandler.writableDatabase
            val selection = DataBaseConstants.TASK.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            db.delete(DataBaseConstants.TASK.TABLE_NAME, selection, args)

            true
        } catch (e: Exception) {
            false
        }
    }
}