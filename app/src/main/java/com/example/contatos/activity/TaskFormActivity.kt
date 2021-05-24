package com.example.contatos.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.ParcelFileDescriptor
import android.text.Editable
import android.view.View
import android.widget.*
import com.example.contatos.R
import com.example.contatos.dao.TaskDAO
import com.example.contatos.model.Task
import com.example.contatos.util.DatabaseUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TaskFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mTaskFormTitle: TextView
    private lateinit var mTaskFormName: EditText
    private lateinit var mTaskFormDescription: EditText
    private lateinit var mTaskFormIsDone: Switch
    private lateinit var mTaskFormSave: Button

    private lateinit var mTaskDAO: TaskDAO

    private var mUserId = -1
    private var mTaskId = -1

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)

        mTaskDAO = DatabaseUtil.getInstance(applicationContext).getTaskDAO()

        mUserId = intent.getIntExtra("userId", -1)
        mTaskId = intent.getIntExtra("taskId", -1)

        mTaskFormTitle = findViewById(R.id.task_form_textview_title)
        mTaskFormName = findViewById(R.id.task_form_edittext_name)
        mTaskFormDescription = findViewById(R.id.task_form_edittext_description)
        mTaskFormIsDone = findViewById(R.id.task_form_switch_isdone)

        mTaskFormSave = findViewById(R.id.task_form_button_save)
        mTaskFormSave.setOnClickListener(this)

        if (mTaskId != -1) {
            GlobalScope.launch {
                val task = mTaskDAO.find(mTaskId)

                handler.post {
                    mTaskFormTitle.text = Editable.Factory.getInstance().newEditable("Editar Tarefa")
                    mTaskFormName.text = Editable.Factory.getInstance().newEditable(task.name)
                    mTaskFormDescription.text = Editable.Factory.getInstance().newEditable(task.description)
                    mTaskFormIsDone.isChecked = task.isDone
                }
            }
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.task_form_button_save -> {

                val name = mTaskFormName.text.toString()
                val description = mTaskFormDescription.text.toString()
                val isDone = mTaskFormIsDone.isChecked

                if (name.isEmpty()) {
                    mTaskFormName.error = "Este campo não pode ser vazio"
                    return
                }

                if (mTaskId == -1) {

                    GlobalScope.launch {
                        val task = Task(name = name, description = description, isDone = isDone, userId = mUserId)

                        mTaskDAO.insert(task)

                        handler.post {
                            Toast.makeText(applicationContext, "Tarefa ${task.name} adicionada com sucesso", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }

                } else {
                    GlobalScope.launch {
                        val task = Task(mTaskId, name, description, isDone, mUserId)
                        mTaskDAO.update(task)
                        handler.post {
                            Toast.makeText(applicationContext, "Tarefa ${task.name} editada com sucesso", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                }

            }
        }
    }
}