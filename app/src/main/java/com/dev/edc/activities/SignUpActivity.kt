package com.dev.edc.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import com.dev.edc.R

class SignUpActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var backButton: ImageView
    lateinit var trafficNumber: EditText
    lateinit var tryFileNo: EditText
    lateinit var proceedButton: ImageView
    lateinit var  trafficNumberVal: String
    lateinit var  tryFileNoVal: String
    lateinit var car: ImageView

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
        car = findViewById(R.id.car)
        val carAnimation: Animation = AnimationUtils.loadAnimation(this,R.anim.car_right_small)
        val carAnimation2: Animation = AnimationUtils.loadAnimation(this,R.anim.car_right_exit)
        car.startAnimation(carAnimation)
        backButton.setOnClickListener {
            val intent = Intent(context, AccountActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
//            overridePendingTransition(R.anim.fade_in_activity,R.anim.fade_out_activity)
        }
        trafficNumberVal = trafficNumber.text.toString()
        tryFileNoVal = tryFileNo.text.toString()
        proceedButton.setOnClickListener {
            Log.e("Button","Checking")
            if(trafficNumber.text.toString().trim().equals("")) {
                showLoginErrorPopUp("Alert","Field cannot be empty")
//                Toast.makeText(context, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            } else if(tryFileNo.text.toString().trim().equals("")) {
                showLoginErrorPopUp("Alert","Field cannot be empty")
//            Toast.makeText(context, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            }
            else {
                Log.e("Error","checking")
                car.startAnimation(carAnimation2)
                val intent = Intent(context, SignUpDetailActivity::class.java)
//                intent.putExtra("trafficNumber",trafficNumberVal)
//                intent.putExtra("tryFileNo",tryFileNoVal)
                intent.putExtra("trafficNumber",trafficNumber.text.toString())
                intent.putExtra("tryFileNo",tryFileNo.text.toString())
                startActivity(intent)


//                overridePendingTransition(R.anim.fade_in_activity,R.anim.fade_out_activity)

            }
        }
    }
    private fun showLoginErrorPopUp(head: String, message: String) {
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_alert)
        val text = dialog.findViewById<View>(R.id.textDialog) as TextView
        val textHead = dialog.findViewById<View>(R.id.alertHead) as TextView
        text.text = message
        textHead.text = head
        dialog.show()
    }
}