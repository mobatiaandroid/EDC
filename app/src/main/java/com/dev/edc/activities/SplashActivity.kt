package com.dev.edc.activities

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.dev.edc.R

class SplashActivity : AppCompatActivity() {
    lateinit var logo: ImageView
    lateinit var car: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        logo = findViewById(R.id.logo)
        car = findViewById(R.id.car)
        val carAnimation: Animation = AnimationUtils.loadAnimation(this,R.anim.car_right)
        val fadeInAnimation: Animation = AnimationUtils.loadAnimation(this,R.anim.fade_in)
        logo.startAnimation(fadeInAnimation)
        val animUpDown: Animation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.up_anim
        )
        car.startAnimation(carAnimation)
        logo.startAnimation(animUpDown)
        Handler().postDelayed({
            var intent: Intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in_activity,R.anim.fade_out_activity)
//            overridePendingTransition(0,0)
            finish()
        },1500)
    }
}