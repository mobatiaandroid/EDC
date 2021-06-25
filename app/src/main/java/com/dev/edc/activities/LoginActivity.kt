package com.dev.edc.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.dev.edc.R
import com.dev.edc.common_classes.AppUtils

class LoginActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var userNameEdtTxt: EditText
    lateinit var passwordEdtTxt: EditText
    lateinit var createAccount: TextView
    lateinit var loginBtn: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        context = this
        initialiseUI()
    }

    private fun initialiseUI() {
        userNameEdtTxt = findViewById(R.id.email)
        passwordEdtTxt = findViewById(R.id.password)
        loginBtn = findViewById(R.id.login)
        createAccount = findViewById(R.id.createAccount)

        loginBtn.setOnClickListener {
            if(userNameEdtTxt.text.toString().trim().equals("", ignoreCase = true)) {
                Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            }
            else if (!AppUtils.isValidEmail(userNameEdtTxt?.text.toString())) {
                Toast.makeText(this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
            }
            else if (passwordEdtTxt.text.toString().equals("", ignoreCase = true)) {
                Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            }
            else {
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in_activity,R.anim.fade_out_activity)
                finish()
            }

        }
        createAccount.setOnClickListener {
            val intent = Intent(context, AccountActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in_activity,R.anim.fade_out_activity)
            finish()
        }
    }

}