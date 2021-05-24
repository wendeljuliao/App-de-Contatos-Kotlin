package com.example.contatos.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.contatos.R
import com.example.contatos.adapter.TaskAdapter
import com.example.contatos.adapter.TaskItemListener
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

class MainActivity : AppCompatActivity(), View.OnClickListener, TaskItemListener {

    private lateinit var userDAO: UserDAO
    private lateinit var mTaskList: RecyclerView
    private lateinit var mAddTask: FloatingActionButton

    private lateinit var mUserWithTasks: UserWithTasks

    private val handler = Handler(Looper.getMainLooper())

    private var mUserId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userDAO = DatabaseUtil.getInstance(applicationContext).getUserDAO()
        mTaskList = findViewById(R.id.main_recyclerview_tasks)
        mAddTask = findViewById(R.id.main_floatingbutton_add_task)
        mAddTask.setOnClickListener(this)


        if (intent != null) {
            mUserId = intent.getIntExtra("userId", -1)

        }



    }

    override fun onStart() {
        super.onStart()
        GlobalScope.launch {
            if (mUserId != -1) {
                mUserWithTasks = userDAO.findTasksByUser(mUserId)
                val taskAdapter = TaskAdapter(mUserWithTasks.tasks)
                taskAdapter.setTaskItemListener(this@MainActivity)
                val llm = LinearLayoutManager(applicationContext)

                handler.post {
                    mTaskList.apply {
                        adapter = taskAdapter
                        layoutManager = llm
                    }
                }

            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.main_floatingbutton_add_task -> {
                val it = Intent(applicationContext, TaskFormActivity::class.java)
                it.putExtra("userId", mUserId )
                startActivity(it)
            }
        }
    }

    override fun onTaskItemClick(v: View, pos: Int) {
        val it = Intent(applicationContext, TaskFormActivity::class.java)
        it.putExtra("userId", mUserId)
        it.putExtra("taskId", mUserWithTasks.tasks[pos].tid)

        startActivity(it)
    }

    override fun onTaskItemLongClick(v: View, pos: Int) {
        Toast.makeText(applicationContext, "Long Click", Toast.LENGTH_SHORT).show()
    }
}