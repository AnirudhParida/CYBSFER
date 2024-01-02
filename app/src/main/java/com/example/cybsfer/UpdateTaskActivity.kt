package com.example.cybsfer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cybsfer.data.DatabaseHelper
import com.example.cybsfer.data.Task
import com.example.cybsfer.databinding.ActivityTaskBinding
import com.example.cybsfer.databinding.ActivityUpdateTask2Binding

class UpdateTaskActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var binding: ActivityUpdateTask2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateTask2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        // Retrieve task ID from intent
        val taskId = intent.getLongExtra("TASK_ID", -1)

        binding.saveButtonUpdate.setOnClickListener {
            updateTask(taskId)
            // Finish the activity or navigate back to the previous activity
            finish()
        }
    }

    private fun updateTask(taskId: Long) {
        //the fields should already contian the existing values of the task

        // Update the existing task in the database
        val updatedTitle = binding.titleEditTextUpdate.text.toString()
        val updatedDescription = binding.descriptionEditTextUpdate.text.toString()
        val updatedDate = binding.dateEditTextUpdate.text.toString()

        // Check if any fields are empty before updating
        if (updatedTitle.isNotEmpty() && updatedDescription.isNotEmpty() && updatedDate.isNotEmpty()) {
            val updatedTaskDetails = Task(taskId, updatedTitle, updatedDescription, updatedDate, 0)
            dbHelper.updateTask(updatedTaskDetails)
        } else {
            // Handle empty fields, show a message, or take appropriate action
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }


    }



}