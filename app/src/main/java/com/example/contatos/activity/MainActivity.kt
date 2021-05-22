package com.example.contatos.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.contatos.R
import com.example.contatos.dao.ContatosDatabase
import com.example.contatos.dao.TaskDAO
import com.example.contatos.model.Task
import com.example.contatos.model.User
import com.example.contatos.util.DatabaseUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = DatabaseUtil.getInstance(this)
        val userDAO = db.getUserDAO()

        val user = User(firstName = "Bruno", lastName = "Alex", email="aalex@hotmail.com", password= "99999-9999")
        val user2 = User(firstName = "Bruno2", lastName = "Alex2", email="aalex2@hotmail.com", password= "99999-9999")

        val task = Task(name = "Lavar", description = "Lavar", isDone = false, userId = 1)
        val task2 = Task(name = "Lavar2", description = "Lavar2", isDone = true, userId = 1)
        val task3 = Task(name = "Lavar3", description = "Lavar3", isDone = true, userId = 2)

        GlobalScope.launch {
            userDAO.insert(user)
            userDAO.insert(user2)

            val taskDAO = db.getTaskDAO()
            taskDAO.insert(task)
            taskDAO.insert(task2)
            taskDAO.insert(task3)

            userDAO.findAll().forEach {
                Log.i("App","User: [${it.uid}, ${it.firstName}, ${it.lastName}, ${it.email}, ${it.password}]")
            }

            taskDAO.findAll().forEach {
                Log.i("App","Task: [${it.tid}, ${it.name}, ${it.description}, ${it.isDone}, ${it.userId}]")
            }

            val userTasks = userDAO.findTasksByUser(1)
            userTasks.tasks.forEach {
                Log.i("App","Tasks of User ${userTasks.user.firstName}: [${it.tid}, ${it.name}, ${it.description}, ${it.isDone}, ${it.userId}]")
            }

        }

    }
}