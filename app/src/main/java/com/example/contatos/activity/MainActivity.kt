package com.example.contatos.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.contatos.R
import com.example.contatos.dao.ContatosDatabase
import com.example.contatos.dao.TaskDAO
import com.example.contatos.dao.UserDAO
import com.example.contatos.model.Task
import com.example.contatos.model.User
import com.example.contatos.util.DatabaseUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var userDAO: UserDAO
    private lateinit var mMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userDAO = DatabaseUtil.getInstance(applicationContext).getUserDAO()

        mMessage = findViewById(R.id.main_textview_message)

        if (intent != null) {
            GlobalScope.launch {
                val uid = intent.getIntExtra("userId", -1)
                if (uid != -1) {

                    val user = userDAO.find(uid)

                    Handler(Looper.getMainLooper()).post {
                        mMessage.text = user.firstName
                    }

                }
            }
        }

    }
}