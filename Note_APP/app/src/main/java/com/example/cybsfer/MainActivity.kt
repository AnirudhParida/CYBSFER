package com.example.cybsfer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cybsfer.data.DatabaseHelper
import com.example.cybsfer.data.Task
import com.example.cybsfer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var binding: ActivityMainBinding
    private lateinit var taskAdapter: TaskAdapter
    private var userId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        dbHelper = DatabaseHelper(this)

        // Retrieve user ID from intent
        userId = intent.getLongExtra("USER_ID", 0)

        // Initialize RecyclerView and Adapter
        taskAdapter = TaskAdapter(ArrayList(), this::onUpdateButtonClick,this::onDeleteButtonClick)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = taskAdapter

        // Load tasks for the current user
        loadTasks()

        binding.fab.setOnClickListener {
            // Handle adding tasks, for example, show a dialog or navigate to a new activity
            TaskOperations(-1) // Passing -1 to indicate a new task
        }
    }

    private fun loadTasks() {
        // Load tasks for the current user from the database
        val tasks = dbHelper.getTasks(userId)
        taskAdapter.setTasks(tasks)
    }

    private fun onUpdateButtonClick(task: Task) {
        // Handle item click, for example, navigate to a task details or update activity
        UpdateTaskOperations(task.id)
    }

    private fun UpdateTaskOperations(taskId: Long) {
        val intent = Intent(this, UpdateTaskActivity::class.java)
        intent.putExtra("TASK_ID", taskId)
        startActivity(intent)
    }

    private fun TaskOperations(taskId: Long) {
        val intent = Intent(this, TaskActivity::class.java)
        intent.putExtra("TASK_ID", taskId)
        startActivity(intent)
    }

    private fun onDeleteButtonClick(task: Task) {
        // Handle delete button click
        // You may want to show a confirmation dialog before deleting the task
        // For simplicity, we'll proceed with the deletion directly
        dbHelper.deleteTask(task.id)

        // Reload data in MainActivity
        loadTasks()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_logout -> {
                // Handle logout option
                // You may want to show a confirmation dialog before logging out
                // For simplicity, we'll proceed with the logout directly
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        // Handle logout logic, for example, navigate to the login screen
        // In this example, I'm finishing the current activity
        startActivity(Intent(this, login::class.java))
        finish()
        // You might want to start the login activity here
    }

}