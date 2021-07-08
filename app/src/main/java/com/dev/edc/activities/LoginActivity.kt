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
import com.dev.edc.activities.main.MainActivity
import com.dev.edc.activity.register.CreateAccountActivity
import com.dev.edc.common.CommonMethods
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
    lateinit var loginBtn: ImageView
    lateinit var logo: ImageView
    lateinit var car: ImageView
    lateinit var city1: ImageView
    lateinit var city2: ImageView
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
//        city1 = findViewById(R.id.city1)
//        city2 = findViewById(R.id.city2)
        val cityAnimation: Animation = AnimationUtils.loadAnimation(this,R.anim.city_left)
        val carAnimation: Animation = AnimationUtils.loadAnimation(this,R.anim.car_right_small)

        loginBtn.setOnClickListener {
            if(userNameEdtTxt.text.toString().trim().equals("")) {
                CommonMethods.showLoginErrorPopUp(context,"Alert","Field cannot be empty.")
            }
            else if (!AppUtils.isValidEmail(userNameEdtTxt.text.toString())) {
                CommonMethods.showLoginErrorPopUp(context,"Alert","Enter a Valid Email.")
            }
            else if (passwordEdtTxt.text.toString().equals("")) {
                CommonMethods.showLoginErrorPopUp(context,"Alert","Field cannot be empty.")
            }
            else {
                login()
            }

        }
        createAccount.setOnClickListener {
            val intent = Intent(context, CreateAccountActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)

            finish()
        }
    }

    private fun login() {
        val call: Call<ResponseBody> = ApiClient.getApiService().loginCall(userNameEdtTxt.text.toString(), passwordEdtTxt.text.toString())
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val responseData = response.body()
                if (responseData != null) {
                    val jsonObject = JSONObject(responseData.string())
                    Log.e("Response",jsonObject.toString())
                    if (jsonObject.has("status")) {
                        val status = jsonObject.optString("status")
                        if (status.equals("success")) {
                            CommonMethods.showLoginErrorPopUp(context,"Alert", "Successfully Logged In")
                            val intent = Intent(context, MainActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(R.anim.fade_in_activity,R.anim.fade_out_activity)
                            finish()
                        } else if (status.equals("invalid_user")) {
                            CommonMethods.showLoginErrorPopUp(context,"Alert", "Email and Password do not match")
                        } else {
                            CommonMethods.showLoginErrorPopUp(context,"Alert", "Some Error Occurred")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                CommonMethods.showLoginErrorPopUp(context,"Alert", "Some Error Occurred")
            }

        })
    }



}