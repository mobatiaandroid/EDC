package com.dev.edc.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.dev.edc.R

class SplashActivity : AppCompatActivity() {
    lateinit var logo: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        logo = findViewById(R.id.logo)
        val fadeInAnimation: Animation = AnimationUtils.loadAnimation(this,R.anim.fade_in)
        logo.startAnimation(fadeInAnimation)
        Handler().postDelayed({
            var intent: Intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in_activity,R.anim.fade_out_activity)
            finish()
        },3000)
    }
}