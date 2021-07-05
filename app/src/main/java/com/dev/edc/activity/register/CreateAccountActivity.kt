package com.dev.edc.activity.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dev.edc.R
import com.dev.edc.activities.LoginActivity
import com.dev.edc.activities.SignUpActivity
import com.dev.edc.activities.SignUpActivityExistingUser
import com.dev.edc.activity.login.LoginActivityNew

class CreateAccountActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var car:ImageView
    lateinit var city1:ImageView
    lateinit var city2:ImageView

    lateinit var backImg:ImageView
    lateinit var imageView12:ImageView
    lateinit var existingUserImg:ImageView
    lateinit var newUserNextImg:ImageView
    lateinit var existingUserNextImg:ImageView
    lateinit var createAccountTxt:TextView
    lateinit var textView21:TextView
    lateinit var newUserTxt:TextView
    lateinit var existingUserTxt:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        context = this
        initialiseUI()
    }

    private fun initialiseUI() {
        car=findViewById(R.id.car)
        city1=findViewById(R.id.city1)
        city2=findViewById(R.id.city2)

        backImg=findViewById(R.id.backImg)
        createAccountTxt=findViewById(R.id.createAccountTxt)
        textView21=findViewById(R.id.textView21)
        imageView12=findViewById(R.id.imageView12)
        existingUserImg=findViewById(R.id.existingUserImg)
        newUserTxt=findViewById(R.id.newUserTxt)
        newUserNextImg=findViewById(R.id.newUserNextImg)
        existingUserTxt=findViewById(R.id.existingUserTxt)
        existingUserNextImg=findViewById(R.id.existingUserNextImg)
        backImg.visibility=View.GONE
        createAccountTxt.visibility=View.GONE
        textView21.visibility=View.GONE
        imageView12.visibility=View.GONE
        existingUserImg.visibility=View.GONE
        newUserTxt.visibility=View.GONE
        newUserNextImg.visibility=View.GONE
        existingUserTxt.visibility=View.GONE
        existingUserNextImg.visibility=View.GONE

        val cityAnimation: Animation = AnimationUtils.loadAnimation(this,R.anim.city_left)
        val carAnimation: Animation = AnimationUtils.loadAnimation(this,R.anim.car_right_new)
        city1.startAnimation(cityAnimation)
        car.startAnimation(carAnimation)

        Handler().postDelayed({
            backImg.visibility=View.VISIBLE
            createAccountTxt.visibility=View.VISIBLE
            textView21.visibility=View.VISIBLE
            imageView12.visibility=View.VISIBLE
            existingUserImg.visibility=View.VISIBLE
            newUserTxt.visibility=View.VISIBLE
            newUserNextImg.visibility=View.VISIBLE
            existingUserTxt.visibility=View.VISIBLE
            existingUserNextImg.visibility=View.VISIBLE
        },1000)

        imageView12.setOnClickListener {
            val intent = Intent(context, SignUpActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)

//            overridePendingTransition(R.anim.fade_in_activity,R.anim.fade_out_activity)
        }
        existingUserImg.setOnClickListener {
            val intent = Intent(context, SignUpActivityExistingUser::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)

        }
    }
}