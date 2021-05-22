package com.example.contatos.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithTasks(
    @Embedded
    val user: User,
    @Relation(
        parentColumn = "uid",
        entityColumn = "user_id"
    )
    val tasks: List<Task>
)