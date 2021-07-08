package com.dev.edc.activity.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dev.edc.R
import com.dev.edc.activities.main.MainActivity
import com.dev.edc.activity.register.CreateAccountActivity
import com.dev.edc.common.CommonMethods
import com.dev.edc.common_classes.ApiClient
import com.dev.edc.common_classes.ProgressBarDialog
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivityNew : AppCompatActivity() {
    lateinit var context: Context
    lateinit var passwordHideShowImg:ImageView
    lateinit var emailBgImg:ImageView
    lateinit var passwordBgImg:ImageView
    lateinit var buildingBgImg:ImageView
    lateinit var carImg:ImageView
    lateinit var passwordTxt:EditText
    lateinit var emailTxt:EditText
    lateinit var submitBtn:Button
    lateinit var emailHintTxt:TextView
    lateinit var passwordHintTxt:TextView
    lateinit var createAccount:TextView
    var progressBarDialog: ProgressBarDialog? = null
    var passwordHideShown:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activtiy_login_new)
        context = this
        initialiseUI()
    }

    private fun initialiseUI() {

        passwordHideShowImg=findViewById(R.id.passwordHideShowImg)
        passwordTxt=findViewById(R.id.passwordTxt)
        submitBtn=findViewById(R.id.submitBtn)
        emailTxt=findViewById(R.id.emailTxt)
        emailBgImg=findViewById(R.id.emailBgImg)
        emailHintTxt=findViewById(R.id.emailHintTxt)
        passwordHintTxt=findViewById(R.id.passwordHintTxt)
        passwordBgImg=findViewById(R.id.passwordBgImg)
        createAccount=findViewById(R.id.createAccount)
        buildingBgImg=findViewById(R.id.city1)
        carImg=findViewById(R.id.carImg)
        progressBarDialog = ProgressBarDialog(context)
        val cityAnimation: Animation = AnimationUtils.loadAnimation(this,R.anim.city_left)
        val carAnimation: Animation = AnimationUtils.loadAnimation(this,R.anim.car_right_small)
        var createAccunt="<font color=#000000>Don't have an account? </font> <font color=#F37021><u>Create Account</u></font>"
        createAccount.text = Html.fromHtml(createAccunt)
        passwordHideShowImg.setOnClickListener(View.OnClickListener {
            if (passwordHideShown) {
                passwordHideShown = false
                passwordTxt.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                passwordHideShown = true
                passwordTxt.transformationMethod = HideReturnsTransformationMethod.getInstance();
            }

        })

        createAccount.setOnClickListener(View.OnClickListener {

//            buildingBgImg.startAnimation(cityAnimation)
            carImg.startAnimation(carAnimation)
            val intent = Intent(context, CreateAccountActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
        })
        submitBtn.setOnClickListener(View.OnClickListener {

            if (emailTxt.text.toString().equals("")) {
                val shake = AnimationUtils.loadAnimation(context, R.anim.shake)
                emailBgImg.startAnimation(shake)
                emailHintTxt.startAnimation(shake)
                CommonMethods.showLoginErrorPopUp(context,"Alert","Field cannot be empty.")
            } else {
                var emailPattern = CommonMethods.isEmailValid(emailTxt.text.toString())
                if (!emailPattern) {
                    CommonMethods.showLoginErrorPopUp(context,"Alert","Enter a Valid Email.")
                } else {
                    if (passwordTxt.text.toString().equals("")) {
                        val shake = AnimationUtils.loadAnimation(context, R.anim.shake)
                        passwordBgImg.startAnimation(shake)
                        passwordHintTxt.startAnimation(shake)
                        CommonMethods.showLoginErrorPopUp(context,"Alert","Field cannot be empty.")
                    } else {

                        if (CommonMethods.isInternetAvailable(context)) {
                            callLoginApi(emailTxt.text.toString(),passwordTxt.text.toString())
                        } else {
                            CommonMethods.showLoginErrorPopUp(context,"Alert","Network error occurred. Please check your internet connection and try again later.")
                        }

                    }
                }
            }
        })


    }

    fun callLoginApi(email:String,password:String)
    {
        val call: Call<ResponseBody> = ApiClient.getApiService().loginCall(email, password)
        progressBarDialog!!.show()
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                progressBarDialog!!.dismiss()
                val responseData = response.body()
                if (responseData != null) {
                    val jsonObject = JSONObject(responseData.string())
                    Log.e("Response",jsonObject.toString())
                    if (jsonObject.has("status")) {
                        val status = jsonObject.optString("status")
                        if (status.equals("success")) {
                            CommonMethods.showLoginErrorPopUp(context,"Alert","Successfully Logged In.")
                            val intent = Intent(context, MainActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(R.anim.fade_in_activity,R.anim.fade_out_activity)
                            finish()
                        } else if (status.equals("invalid_user")) {
                            CommonMethods.showLoginErrorPopUp(context,"Alert", "Email and Password do not match")
                        }else {
                            //CommonMethods.showLoginErrorPopUp(context,"Alert","Email and Password do not match")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                progressBarDialog!!.dismiss()
                CommonMethods.showLoginErrorPopUp(context,"Alert","Field cannot be empty.")
            }

        })
    }

}