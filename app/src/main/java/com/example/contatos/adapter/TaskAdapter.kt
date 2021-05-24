package com.example.contatos.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contatos.R
import com.example.contatos.model.Task

class TaskAdapter(val tasks:List<Task>):RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.name.text = tasks[position].name
        holder.description.text = tasks[position].description

        if (tasks[position].isDone) {
            holder.isDone.setBackgroundColor(Color.GREEN)
        } else {
            holder.isDone.setBackgroundColor(Color.RED)
        }

    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.item_task_textview_name)
        val description: TextView = itemView.findViewById(R.id.item_task_textview_description)
        val isDone: View = itemView.findViewById(R.id.item_task_view_isdone)


    }

}