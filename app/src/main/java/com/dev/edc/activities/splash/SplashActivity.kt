package com.dev.edc.activities.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.dev.edc.R
import com.dev.edc.activities.LoginActivity
import com.dev.edc.activity.login.LoginActivityNew

class SplashActivity : AppCompatActivity() {
    lateinit var logo: ImageView
    lateinit var car: ImageView
    lateinit var city1: ImageView
    lateinit var city2: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        logo = findViewById(R.id.logo)
        car = findViewById(R.id.car)
        city1 = findViewById(R.id.city1)
        city2 = findViewById(R.id.city2)
        val cityAnimation: Animation = AnimationUtils.loadAnimation(this,R.anim.city_left)
        val carAnimation: Animation = AnimationUtils.loadAnimation(this,R.anim.car_right)
        val fadeInAnimation: Animation = AnimationUtils.loadAnimation(this,R.anim.fade_in)
        logo.startAnimation(fadeInAnimation)
        val animUpDown: Animation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.logo_animation
        )
        logo.startAnimation(animUpDown)
        car.startAnimation(carAnimation)
//        city1.startAnimation(cityAnimation)
//        city2.startAnimation(cityAnimation)

        Handler().postDelayed({
            var intent: Intent = Intent(this, LoginActivityNew::class.java)
            startActivity(intent)
           // overridePendingTransition(R.anim.fade_in_activity,R.anim.fade_out_activity)
           overridePendingTransition(0,0)
            finish()
        },2000)
    }
}