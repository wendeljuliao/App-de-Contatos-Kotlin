package com.example.contatos.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.contatos.R
import com.example.contatos.adapter.TaskAdapter
import com.example.contatos.dao.ContatosDatabase
import com.example.contatos.dao.TaskDAO
import com.example.contatos.dao.UserDAO
import com.example.contatos.model.Task
import com.example.contatos.model.User
import com.example.contatos.model.UserWithTasks
import com.example.contatos.util.DatabaseUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var taskDAO: TaskDAO

    private lateinit var userDAO: UserDAO
    private lateinit var mTaskList: RecyclerView
    private lateinit var mUserWithTasks: UserWithTasks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskDAO = DatabaseUtil.getInstance(applicationContext).getTaskDAO()

        userDAO = DatabaseUtil.getInstance(applicationContext).getUserDAO()
        mTaskList = findViewById(R.id.main_recyclerview_tasks)

        var userId = -1

        if (intent != null) {
            userId = intent.getIntExtra("userId", -1)

        }

        GlobalScope.launch {

            val task1 = Task(name="Lavar louça", description = "aaa", isDone = false, userId = 1)
            val task2 = Task(name="Lavar louça2", description = "aaa", isDone = true, userId = 1)

            taskDAO.insert(task1)
            taskDAO.insert(task2)


            if (userId != -1) {
                mUserWithTasks = userDAO.findTasksByUser(userId)
                val taskAdapter = TaskAdapter(mUserWithTasks.tasks)
                val llm = LinearLayoutManager(applicationContext)

                mTaskList.apply {
                    adapter = taskAdapter
                    layoutManager = llm
                }
            }
        }

    }
}