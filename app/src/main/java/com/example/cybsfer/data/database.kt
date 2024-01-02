package com.example.cybsfer.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// DatabaseHelper.kt
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "todo_app.db"

        // User table
        private const val TABLE_USERS = "users"
        private const val KEY_USER_ID = "id"
        private const val KEY_USERNAME = "username"
        private const val KEY_PASSWORD = "password"

        // Tasks table
        private const val TABLE_TASKS = "tasks"
        private const val KEY_TASK_ID = "id"
        private const val KEY_TASK_TITLE = "title"
        private const val KEY_TASK_DESCRIPTION = "description"
        private const val KEY_TASK_DATE = "date"
        private const val KEY_TASK_USER_ID = "user_id"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create Users table
        val createUsersTable = ("CREATE TABLE $TABLE_USERS "
                + "($KEY_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, $KEY_USERNAME TEXT,"
                + " $KEY_PASSWORD TEXT)")
        db.execSQL(createUsersTable)

        // Create Tasks table
        val createTasksTable = ("CREATE TABLE $TABLE_TASKS "
                + "($KEY_TASK_ID INTEGER PRIMARY KEY AUTOINCREMENT, $KEY_TASK_TITLE TEXT,"
                + " $KEY_TASK_DESCRIPTION TEXT, $KEY_TASK_DATE TEXT, $KEY_TASK_USER_ID INTEGER)")
        db.execSQL(createTasksTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Upgrade logic if needed
    }

    // User related methods

    fun addUser(user: User) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_USERNAME, user.username)
        values.put(KEY_PASSWORD, user.password)

        db.insert(TABLE_USERS, null, values)
        db.close()
    }

    fun getUser(username: String): User? {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE $KEY_USERNAME = ?"
        val cursor = db.rawQuery(query, arrayOf(username))

        return if (cursor.moveToFirst()) {
            val userIdIndex = cursor.getColumnIndex(KEY_USER_ID)
            val usernameIndex = cursor.getColumnIndex(KEY_USERNAME)
            val passwordIndex = cursor.getColumnIndex(KEY_PASSWORD)

            if (userIdIndex != -1 && usernameIndex != -1 && passwordIndex != -1) {
                val user = User(
                    cursor.getInt(userIdIndex),
                    cursor.getString(usernameIndex),
                    cursor.getString(passwordIndex)
                )
                cursor.close()
                user
            } else {
                cursor.close()
                null
            }
        } else {
            cursor.close()
            null
        }
    }

    // Task related methods

    fun addTask(task: Task) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_TASK_TITLE, task.title)
        values.put(KEY_TASK_DESCRIPTION, task.description)
        values.put(KEY_TASK_DATE, task.date)
        values.put(KEY_TASK_USER_ID, task.userId)

        db.insert(TABLE_TASKS, null, values)
        db.close()
    }

    fun getTasks(userId: Long): List<Task> {
        val taskList = mutableListOf<Task>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_TASKS WHERE $KEY_TASK_USER_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(userId.toString()))

        if (cursor.moveToFirst()) {
            val taskIdIndex = cursor.getColumnIndex(KEY_TASK_ID)
            val titleIndex = cursor.getColumnIndex(KEY_TASK_TITLE)
            val descriptionIndex = cursor.getColumnIndex(KEY_TASK_DESCRIPTION)
            val dateIndex = cursor.getColumnIndex(KEY_TASK_DATE)
            val userIdIndex = cursor.getColumnIndex(KEY_TASK_USER_ID)

            while (!cursor.isAfterLast) {
                if (taskIdIndex != -1 && titleIndex != -1 && descriptionIndex != -1 && dateIndex != -1 && userIdIndex != -1) {
                    val task = Task(
                        cursor.getLong(taskIdIndex),
                        cursor.getString(titleIndex),
                        cursor.getString(descriptionIndex),
                        cursor.getString(dateIndex),
                        cursor.getLong(userIdIndex)
                    )
                    taskList.add(task)
                }
                cursor.moveToNext()
            }
        }

        cursor.close()
        return taskList
    }

    fun updateTask(task: Task) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_TASK_TITLE, task.title)
        values.put(KEY_TASK_DESCRIPTION, task.description)
        values.put(KEY_TASK_DATE, task.date)
        values.put(KEY_TASK_USER_ID, task.userId)

        db.update(TABLE_TASKS, values, "$KEY_TASK_ID = ?", arrayOf(task.id.toString()))
        db.close()
    }

    fun deleteTask(taskId: Long) {
        val db = this.writableDatabase
        db.delete(TABLE_TASKS, "$KEY_TASK_ID = ?", arrayOf(taskId.toString()))
        db.close()
    }
}
