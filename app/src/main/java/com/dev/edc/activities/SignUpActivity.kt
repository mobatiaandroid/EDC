package com.dev.edc.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import com.dev.edc.R
import com.dev.edc.common_classes.ApiClient
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var backButton: ImageView
    lateinit var trafficNumber: EditText
    lateinit var tryFileNo: EditText
    lateinit var proceedButton: ImageView
    lateinit var  trafficNumberVal: String
    lateinit var  tryFileNoVal: String
    lateinit var car: ImageView
    lateinit var city1: ImageView
    lateinit var city2: ImageView

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
        city1 = findViewById(R.id.city1)
        city2 = findViewById(R.id.city2)
        val cityAnimation: Animation = AnimationUtils.loadAnimation(this,R.anim.city_left)
        val carAnimation: Animation = AnimationUtils.loadAnimation(this,R.anim.car_right_small)
        val carAnimation2: Animation = AnimationUtils.loadAnimation(this,R.anim.car_right_exit)
        city1.startAnimation(cityAnimation)
        city2.startAnimation(cityAnimation)
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
                val call: Call<ResponseBody> = ApiClient.getApiService().validateStudentCall(
                    "0",trafficNumber.text.toString(),tryFileNo.text.toString()
                )
                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        val responseData = response.body()
                        if (responseData != null) {
                            val jsonObject = JSONObject(responseData.string())
                            if (jsonObject.has("status")) {
                                val status = jsonObject.optString("status")
                                if (status.equals("success")) {
                                    val responseArray: JSONObject = jsonObject.optJSONObject("student_details")
                                    val fullName: String = responseArray.optString("FullName")
                                    val fullNameArabic: String = responseArray.optString("FullNameArabic")
                                    val birthDate: String = responseArray.optString("BirthDate")
                                    val gender: String = responseArray.optString("Gender")
                                    val trafficNoVal = responseArray.optString("TrafficNo")
                                    val tryFileNoVal = responseArray.optString("TryFileNo")
                                    showValidateStudentPopUp(fullName,fullNameArabic,birthDate,trafficNoVal,tryFileNoVal,gender)

                                }
                            }
                        }

                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        showLoginErrorPopUp("Alert","Invalid Details")
                    }

                })



//                overridePendingTransition(R.anim.fade_in_activity,R.anim.fade_out_activity)

            }
        }
    }

    private fun showValidateStudentPopUp(
        fullName: String,
        fullNameArabic: String,
        birthDate: String,
        trafficNoVal: String,
        tryFileNoVal: String,
        gender: String
    ) {
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.validate_student_dialog)
        dialog.setCancelable(false)
        var name = dialog.findViewById<View>(R.id.fullNameVal) as TextView
        var nameArabic = dialog.findViewById<View>(R.id.fullNameArabicVal) as TextView
        var dateOfBirth = dialog.findViewById<View>(R.id.dateOfBirthVal) as TextView
        var trafficNo = dialog.findViewById<View>(R.id.trafficNumberVal)as TextView
        var ok = dialog.findViewById<View>(R.id.ok)as TextView
        var email = dialog.findViewById<View>(R.id.emailVal) as EditText
        name.text = fullName.toString()
        nameArabic.text = fullNameArabic.toString()
        dateOfBirth.text = birthDate.toString()
        trafficNo.text = trafficNoVal.toString()
        dialog.show()
        ok.setOnClickListener {
            val call: Call<ResponseBody> = ApiClient.getApiService().sendConfirmEmailCall(
                email.text.toString(),fullName,fullNameArabic,birthDate
            )
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    showVerificationCodePopUp(fullName,fullNameArabic,birthDate,trafficNoVal,tryFileNoVal,gender,email.text.toString())
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
            dialog.dismiss()

//            val intent = Intent(context, SignUpDetailActivity::class.java)
//            intent.putExtra("fullName",fullName)
//            intent.putExtra("fullNameArabic",fullNameArabic)
//            intent.putExtra("birthDate",birthDate)
//            intent.putExtra("gender",gender)
//            intent.putExtra("trafficNumber",trafficNoVal)
//            intent.putExtra("tryFileNo",tryFileNoVal)
//            startActivity(intent)
        }
    }

    private fun showVerificationCodePopUp(
        fullName: String,
        fullNameArabic: String,
        birthDate: String,
        trafficNoVal: String,
        tryFileNoVal: String,
        gender: String,
        email: String
    ) {
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.verification_alert)
        var otp = dialog.findViewById<View>(R.id.otpVal) as EditText
        var ok = dialog.findViewById<View>(R.id.ok)as TextView
        ok.setOnClickListener {
            if(otp.text.equals("")) {
                showLoginErrorPopUp("Alert","Cannot be left Empty")
            } else {
                val intent = Intent(context, SignUpDetailActivity::class.java)
                intent.putExtra("fullName",fullName)
                intent.putExtra("fullNameArabic",fullNameArabic)
                intent.putExtra("birthDate",birthDate)
                intent.putExtra("gender",gender)
                intent.putExtra("trafficNumber",trafficNoVal)
                intent.putExtra("tryFileNo", tryFileNoVal)
                intent.putExtra("email", email)
                startActivity(intent)
            }
        }
        dialog.show()
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