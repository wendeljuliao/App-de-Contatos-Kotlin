package com.example.contatos.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var userDAO: UserDAO
    private lateinit var mTaskList: RecyclerView
    private lateinit var mAddTask: FloatingActionButton
    private lateinit var mUserWithTasks: UserWithTasks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userDAO = DatabaseUtil.getInstance(applicationContext).getUserDAO()
        mTaskList = findViewById(R.id.main_recyclerview_tasks)
        mAddTask = findViewById(R.id.main_floatingbutton_add_task)
        mAddTask.setOnClickListener(this)

        var userId = -1

        if (intent != null) {
            userId = intent.getIntExtra("userId", -1)

        }

        GlobalScope.launch {

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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.main_floatingbutton_add_task -> {
                val it = Intent(applicationContext, TaskFormActivity::class.java)
                startActivity(it)
            }
        }
    }
}