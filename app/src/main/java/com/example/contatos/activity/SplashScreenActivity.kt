package com.example.contatos.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.contatos.R
import com.example.contatos.util.DatabaseUtil

class SplashScreenActivity : AppCompatActivity() {

    private val DELAY_TIME = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        DatabaseUtil.getInstance(this)

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val it = Intent(this, LoginActivity::class.java)
            startActivity(it)
            finish()
        }, DELAY_TIME)

    }
}