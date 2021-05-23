package com.example.contatos.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.contatos.R
import com.example.contatos.dao.UserDAO
import com.example.contatos.model.User
import com.example.contatos.util.DatabaseUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var userDAO: UserDAO

    private lateinit var mRegisterFirstName: EditText
    private lateinit var mRegisterLastName: EditText
    private lateinit var mRegisterEmail: EditText
    private lateinit var mRegisterPassword: EditText
    private lateinit var mRegisterPasswordConfirmation: EditText
    private lateinit var mRegisterSignUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        userDAO = DatabaseUtil.getInstance(this).getUserDAO()

        mRegisterFirstName = findViewById(R.id.register_edittext_firstname)
        mRegisterLastName = findViewById(R.id.register_edittext_lastname)
        mRegisterEmail = findViewById(R.id.register_edittext_email)
        mRegisterPassword = findViewById(R.id.register_edittext_password)
        mRegisterPasswordConfirmation = findViewById(R.id.register_edittext_password_confirmation)

        mRegisterSignUp = findViewById(R.id.register_button_signup)
        mRegisterSignUp.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.register_button_signup -> {
                val firstName = mRegisterFirstName.text.toString()
                val lastName = mRegisterLastName.text.toString()
                val email = mRegisterEmail.text.toString()
                val password = mRegisterPassword.text.toString()
                val passwordConfirmation = mRegisterPasswordConfirmation.text.toString()

                var isFormRightFilled = true

                if (firstName.isEmpty()) {
                    mRegisterFirstName.error = getString(R.string.form_is_required_error)
                    isFormRightFilled = false
                }

                if (lastName.isEmpty()) {
                    mRegisterLastName.error = getString(R.string.form_is_required_error)
                    isFormRightFilled = false
                }

                if (email.isEmpty()) {
                    mRegisterEmail.error = getString(R.string.form_is_required_error)
                    isFormRightFilled = false
                }

                if (password.isEmpty()) {
                    mRegisterPassword.error = getString(R.string.form_is_required_error)
                    isFormRightFilled = false
                }

                if (passwordConfirmation.isEmpty()) {
                    mRegisterPasswordConfirmation.error = getString(R.string.form_is_required_error)
                    isFormRightFilled = false
                }

                if (isFormRightFilled) {
                    if (password != passwordConfirmation) {
                        mRegisterPasswordConfirmation.error = "As senhas são diferentes"
                        return
                    }

                    GlobalScope.launch {

                        val handler = Handler(Looper.getMainLooper())

                        val user = userDAO.findByEmail(email)

                        if (user == null) {
                            val newUser = User(
                                firstName = firstName,
                                lastName = lastName,
                                email = email,
                                password = BCrypt.withDefaults().hashToString(12, password.toCharArray())
                            )

                            userDAO.insert(newUser)

                            handler.post{
                                Toast.makeText(applicationContext, "O usuário ${firstName} foi cadastrado com sucesso!", Toast.LENGTH_LONG).show()
                                finish()
                            }
                        } else {
                            handler.post{
                                mRegisterEmail.error = "Já existe um usuário com este email."

                            }
                        }

                    }

                }


            }

        }
    }



}