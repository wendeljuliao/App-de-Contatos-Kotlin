package com.example.contatos.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey
    val tid:Int? = null,
    val name:String,
    val description:String,
    @ColumnInfo(name="is_done")
    val isDone:Boolean,
    @ColumnInfo(name="user_id")
    val userId:Int
)