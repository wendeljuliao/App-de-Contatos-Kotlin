package com.example.contatos.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.contatos.R
import com.example.contatos.dao.UserDAO
import com.example.contatos.util.DatabaseUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mLoginUserName: EditText
    private lateinit var mLoginPassword: EditText
    private lateinit var mLoginSignIn: Button
    private lateinit var mRegister: TextView

    private lateinit var userDAO: UserDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userDAO = DatabaseUtil.getInstance(applicationContext).getUserDAO()

        mLoginUserName = findViewById(R.id.login_edittext_username)
        mLoginPassword = findViewById(R.id.login_edittext_password)

        mLoginSignIn = findViewById(R.id.login_button_signin)
        mLoginSignIn.setOnClickListener(this)

        mRegister = findViewById(R.id.login_textview_register)
        mRegister.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login_textview_register -> {
                val it = Intent(this, RegisterActivity::class.java)
                startActivity(it)
            }

            R.id.login_button_signin -> {

                val email = mLoginUserName.text.toString()
                val password = mLoginPassword.text.toString()

                var isLoginFormFilled = true

                if (email.isEmpty()) {
                    mLoginUserName.error = "Este campo não pode ser nulo"
                    isLoginFormFilled = false
                }

                if (password.isEmpty()) {
                    mLoginPassword.error = "Este campo não pode ser nulo"
                    isLoginFormFilled = false
                }

                if (isLoginFormFilled) {
                    GlobalScope.launch {

                        val user = userDAO.findByEmail(email)

                        if (user != null) {

                            if (BCrypt.verifyer().verify(password.toCharArray(), user.password).verified) {

                                val it = Intent(applicationContext, MainActivity::class.java)
                                it.putExtra("userId", user.uid)
                                startActivity(it)
                                finish()

                            } else {
                                showLoginErrorMessage()
                            }

                        } else {
                            showLoginErrorMessage()

                        }
                    }

                }

            }

        }
    }

    private fun showLoginErrorMessage() {
        val handler = Handler(Looper.getMainLooper())

        handler.post {
            Toast.makeText(applicationContext, getString(R.string.login_error_message), Toast.LENGTH_SHORT).show()
        }

    }

}