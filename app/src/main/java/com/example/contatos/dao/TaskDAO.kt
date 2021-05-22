package com.example.contatos.dao

import androidx.room.*
import com.example.contatos.model.Task

@Dao
interface TaskDAO {

    @Insert
    fun insert(task: Task)

    @Update
    fun update(task:Task)

    @Delete
    fun delete(task:Task)

    @Query("SELECT * FROM tasks WHERE tid = :tid")
    fun find(tid:Int):Task

    @Query("SELECT * FROM tasks")
    fun findAll():List<Task>


}