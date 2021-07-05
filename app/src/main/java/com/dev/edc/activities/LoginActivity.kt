package com.dev.edc.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.dev.edc.R
import com.dev.edc.common_classes.ApiClient
import com.dev.edc.common_classes.AppUtils
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var userNameEdtTxt: EditText
    lateinit var passwordEdtTxt: EditText
    lateinit var createAccount: TextView
    lateinit var loginBtn: TextView
    lateinit var logo: ImageView
    lateinit var car: ImageView
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
        car = findViewById(R.id.car)
        val carAnimation: Animation = AnimationUtils.loadAnimation(this,R.anim.car_right_small)

        loginBtn.setOnClickListener {
            if(userNameEdtTxt.text.toString().trim().equals("", ignoreCase = true)) {
                showLoginErrorPopUp("Alert","Field cannot be empty.")
//                Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            }
            else if (!AppUtils.isValidEmail(userNameEdtTxt.text.toString())) {
                showLoginErrorPopUp("Alert","Enter a Valid Email.")
//                Toast.makeText(this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
            }
            else if (passwordEdtTxt.text.toString().equals("", ignoreCase = true)) {
                showLoginErrorPopUp("Alert","Field cannot be empty.")
//                Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            }
            else {
                login()
//                val intent = Intent(context, MainActivity::class.java)
//                startActivity(intent)
//                overridePendingTransition(R.anim.fade_in_activity,R.anim.fade_out_activity)
//                finish()
            }

        }
        createAccount.setOnClickListener {
//            car.startAnimation(carAnimation)
//            Toast.makeText(this, "Being Clicked", Toast.LENGTH_SHORT).show()
//            car.animate().translationX()
            val intent = Intent(context, AccountActivity::class.java)
            startActivity(intent)

//            overridePendingTransition(R.anim.fade_in_activity,R.anim.fade_out_activity)
            overridePendingTransition(0,0)

            finish()
        }
    }

    private fun login() {
        val call: Call<ResponseBody> = ApiClient.getApiService().loginCall(
            userNameEdtTxt.text.toString(),
            passwordEdtTxt.text.toString())
        Log.e("userNameEdtTxt",userNameEdtTxt.text.toString())
        Log.e("passwordEdtTxt",passwordEdtTxt.text.toString())
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val responseData = response.body()
                if (responseData != null) {
                    val jsonObject = JSONObject(responseData.string())
                    Log.e("Response",jsonObject.toString())
                    if (jsonObject.has("status")) {
                        val status = jsonObject.optString("status")
                        if (status.equals("success")) {
                            showLoginErrorPopUp("Alert", "Successfully Logged In")
                            val intent = Intent(context, MainActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(R.anim.fade_in_activity,R.anim.fade_out_activity)
                            finish()
                        } else {
                            showLoginErrorPopUp("Alert", "Email and Password do not match")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                showLoginErrorPopUp("Alert", "Some Error Occurred")
            }

        })
    }

    private fun showLoginErrorPopUp(head: String, message: String) {
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_alert)
        val text = dialog.findViewById<View>(R.id.textDialog) as TextView
//        val textHead = dialog.findViewById<View>(R.id.alertHead) as TextView
        text.text = message
//        textHead.text = head
        dialog.show()
    }

}