package com.dev.edc.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.dev.edc.R

class AccountActivity : AppCompatActivity() {
    lateinit var newUser: ImageView
    lateinit var existingUser: ImageView
    lateinit var backButton: ImageView
    lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        context = this
        initialiseUI()
    }

    private fun initialiseUI() {
        newUser = findViewById(R.id.newUser)
        existingUser = findViewById(R.id.existingUser)
        backButton = findViewById(R.id.backButton)
        backButton.setOnClickListener{
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in_activity,R.anim.fade_out_activity)
            finish()
        }
        newUser.setOnClickListener {
            val intent = Intent(context, SignUpActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in_activity,R.anim.fade_out_activity)
        }
    }
}