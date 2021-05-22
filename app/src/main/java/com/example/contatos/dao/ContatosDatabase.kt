package com.example.contatos.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.contatos.model.Task
import com.example.contatos.model.User

@Database(entities = [User::class, Task::class], version = 7)
abstract class ContatosDatabase: RoomDatabase() {

    abstract fun getUserDAO(): UserDAO

    abstract fun getTaskDAO(): TaskDAO

}