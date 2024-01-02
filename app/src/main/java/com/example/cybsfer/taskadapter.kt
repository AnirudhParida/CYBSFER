package com.example.cybsfer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cybsfer.data.Task

// TaskAdapter.kt
class TaskAdapter(
    private var tasks: List<Task>,
    private val updateClickListener: (Task) -> Unit,
    private val deleteClickListener: (Task) -> Unit
) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task, updateClickListener,deleteClickListener)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    fun setTasks(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        private val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        private val updateButton : Button = itemView.findViewById(R.id.updateButton)
        private val deleteButton: Button = itemView.findViewById(R.id.button_delete)


        fun bind(task: Task, updateClickListener: (Task) -> Unit, deleteClickListener: (Task) -> Unit) {
            titleTextView.text = task.title
            descriptionTextView.text = task.description
            dateTextView.text = task.date

            // Set click listener for the item
            updateButton.setOnClickListener {
                updateClickListener(task)
            }

            deleteButton.setOnClickListener {
                deleteClickListener(task)
            }
        }
    }
}
