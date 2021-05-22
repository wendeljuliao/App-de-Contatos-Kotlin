package com.example.contatos.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.contatos.R
import com.example.contatos.dao.ContatosDatabase
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

        val user = User(firstName = "Bruno", lastName = "Alex", email="alex@hotmail.com", phone="99999-9999")

        GlobalScope.launch {
            userDAO.insert(user)

            userDAO.findAll().forEach {
                Log.i("App","[${it.id}, ${it.firstName}, ${it.lastName}, ${it.email}, ${it.phone}]")
            }
        }

    }
}