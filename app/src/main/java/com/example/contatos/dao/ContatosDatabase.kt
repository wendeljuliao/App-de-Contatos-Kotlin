package com.example.contatos.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.contatos.model.User

@Database(entities = [User::class], version = 2)
abstract class ContatosDatabase: RoomDatabase() {

    abstract fun getUserDAO(): UserDAO

}