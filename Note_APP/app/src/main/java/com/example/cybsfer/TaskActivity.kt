package com.example.cybsfer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.cybsfer.data.DatabaseHelper
//import com.example.cybsfer.databinding.ActivityUpdateTaskBinding
import com.example.cybsfer.data.Task
import com.example.cybsfer.databinding.ActivityTaskBinding

class TaskActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var binding: ActivityTaskBinding
    private var taskId: Long = -1 // Default value for a new task

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        // Retrieve task ID from intent
        taskId = intent.getLongExtra("TASK_ID", -1)

        if (taskId.toInt() != -1) {
            // Existing task, load task details for editing
            Log.println(Log.INFO, "TaskActivity", "Task ID: $taskId   Task Exists")
            loadTaskDetails()
        } else {
            // New task, perform initialization
            Log.println(Log.INFO, "TaskActivity", "Task ID: $taskId   New Task")
            initNewTask()
        }
        // Handle logic to update or save the task
        binding.saveButton.setOnClickListener {
            if (taskId.toInt() != -1) {
                // Update existing task
                Log.println(Log.INFO, "TaskActivity", "Task ID: $taskId   Update Task")
                updateTask()
            } else {
                // Save new task
                Log.println(Log.INFO, "TaskActivity", "Task ID: $taskId   Save Task")
                saveNewTask()
            }

            // Finish the activity or navigate back to the previous activity
            finish()
        }
    }

    private fun initNewTask() {
        // For a new task, you might want to perform some initialization
        // For example, you can set default values for the UI fields or handle any specific behavior

        // Clear the UI fields
        binding.titleEditText.setText("")
        binding.descriptionEditText.setText("")
        binding.dateEditText.setText("")

        // Additional initialization code if needed
    }

    private fun loadTaskDetails() {
        val task = dbHelper.getTasks(taskId)
        val selectedTask = task[taskId.toInt()]
        if (task != null) {
            // Populate the UI fields with the task details for editing
            binding.titleEditText.setText(selectedTask.title)
            binding.descriptionEditText.setText(selectedTask.description)
            binding.dateEditText.setText(selectedTask.date)
        }
    }

    private fun updateTask() {
        //the fields should already contian the existing values of the task


        // Update the existing task in the database
        val updatedTitle = binding.titleEditText.text.toString()
        val updatedDescription = binding.descriptionEditText.text.toString()
        val updatedDate = binding.dateEditText.text.toString()

        // Check if any fields are empty before updating
        if (updatedTitle.isNotEmpty() && updatedDescription.isNotEmpty() && updatedDate.isNotEmpty()) {
            val updatedTaskDetails = Task(taskId, updatedTitle, updatedDescription, updatedDate, 0)
            dbHelper.updateTask(updatedTaskDetails)
        } else {
            // Handle empty fields, show a message, or take appropriate action
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveNewTask() {
        /// Save a new task to the database
        val newTitle = binding.titleEditText.text.toString()
        val newDescription = binding.descriptionEditText.text.toString()
        val newDate = binding.dateEditText.text.toString()

        // Check if any fields are empty before saving
        if (newTitle.isNotEmpty() && newDescription.isNotEmpty() && newDate.isNotEmpty()) {
            val newTaskDetails = Task(0, newTitle, newDescription, newDate, 0)
            dbHelper.addTask(newTaskDetails)
        } else {
            // Handle empty fields, show a message, or take appropriate action
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }
}