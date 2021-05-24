package com.example.contatos.adapter

import android.view.View

interface TaskItemListener {

    fun onTaskItemClick(v: View, pos: Int)

    fun onTaskItemLongClick(v: View, pos: Int)

}