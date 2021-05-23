package com.example.contatos.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.contatos.R

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mRegister: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mRegister = findViewById(R.id.login_textview_register)
        mRegister.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login_textview_register -> {
                val it = Intent(this, RegisterActivity::class.java)
                startActivity(it)
            }
        }
    }
}