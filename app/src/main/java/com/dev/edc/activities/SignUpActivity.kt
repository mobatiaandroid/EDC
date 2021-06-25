package com.dev.edc.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.dev.edc.R

class SignUpActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var backButton: ImageView
    lateinit var trafficNumber: EditText
    lateinit var tryFileNo: EditText
    lateinit var proceedButton: ImageView
    lateinit var  trafficNumberVal: String
    lateinit var  tryFileNoVal: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        context = this
        initialiseUI()
    }

    private fun initialiseUI() {
        backButton = findViewById(R.id.backButton)
        trafficNumber = findViewById(R.id.trafficNumber)
        tryFileNo = findViewById(R.id.tryFileNo)
        proceedButton = findViewById(R.id.proceedButton)
        backButton.setOnClickListener {
            val intent = Intent(context, AccountActivity::class.java)
            startActivity(intent)
        }
        trafficNumberVal = trafficNumber.text.toString()
        tryFileNoVal = tryFileNo.text.toString()
        proceedButton.setOnClickListener {
            if(trafficNumber.text.toString().trim().equals("", ignoreCase = true)) {
                Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            } else if(tryFileNo.text.toString().trim().equals("", ignoreCase = true)) {
            Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            } else {
                val intent = Intent(context, SignUpDetailActivity::class.java)
//                intent.putExtra("trafficNumber",trafficNumberVal)
//                intent.putExtra("tryFileNo",tryFileNoVal)
                intent.putExtra("trafficNumber",trafficNumber.text.toString())
                intent.putExtra("tryFileNo",tryFileNo.text.toString())
                startActivity(intent)
            }
        }
    }
}