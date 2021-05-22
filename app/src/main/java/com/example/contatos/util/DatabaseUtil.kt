package com.example.contatos.util

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.example.contatos.dao.ContatosDatabase

object DatabaseUtil {

    private var db:ContatosDatabase? = null

    fun getInstance(context: Context):ContatosDatabase {
        if (this.db == null) {

            this.db = Room.databaseBuilder(
                context,
                ContatosDatabase::class.java,
                "contatos.db"
            ).fallbackToDestructiveMigration().build()

        }

        return db!!

    }

}