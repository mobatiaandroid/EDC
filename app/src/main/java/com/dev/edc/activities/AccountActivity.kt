package com.dev.edc.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.dev.edc.R

class AccountActivity : AppCompatActivity() {
    lateinit var newUser: ImageView
    lateinit var existingUser: ImageView
    lateinit var backButton: ImageView
    lateinit var context: Context
    lateinit var car: ImageView
    lateinit var city1: ImageView
    lateinit var city2: ImageView
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
        car = findViewById(R.id.car)
        city1 = findViewById(R.id.city1)
        city2 = findViewById(R.id.city2)
        val cityAnimation: Animation = AnimationUtils.loadAnimation(this,R.anim.city_left)
        val carAnimation: Animation = AnimationUtils.loadAnimation(this,R.anim.car_right_small)
        city1.startAnimation(cityAnimation)
        city2.startAnimation(cityAnimation)
        car.startAnimation(carAnimation)
        backButton.setOnClickListener{
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
//            overridePendingTransition(R.anim.fade_in_activity,R.anim.fade_out_activity)
            overridePendingTransition(0,0)

            finish()
        }
        newUser.setOnClickListener {
            val intent = Intent(context, SignUpActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
            finish()
//            overridePendingTransition(R.anim.fade_in_activity,R.anim.fade_out_activity)
        }
        existingUser.setOnClickListener {
            val intent = Intent(context, SignUpActivityExistingUser::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
            finish()
        }
    }
}