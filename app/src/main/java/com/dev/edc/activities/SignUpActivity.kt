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
import com.dev.edc.activity.register.CreateAccountActivity
import com.dev.edc.common_classes.ApiClient
import com.dev.edc.common_classes.AppUtils
import com.dev.edc.common_classes.ProgressBarDialog
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
    var progressBarDialog: ProgressBarDialog? = null


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
        progressBarDialog = ProgressBarDialog(context)
        val cityAnimation: Animation = AnimationUtils.loadAnimation(this,R.anim.city_left)
        val carAnimation: Animation = AnimationUtils.loadAnimation(this,R.anim.car_right_small)
        val carAnimation2: Animation = AnimationUtils.loadAnimation(this,R.anim.car_right_exit)
//        city1.startAnimation(cityAnimation)
//        city2.startAnimation(cityAnimation)
        car.startAnimation(carAnimation)
        backButton.setOnClickListener {
            val intent = Intent(context, CreateAccountActivity::class.java)
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
                city1.startAnimation(cityAnimation)
                city2.startAnimation(cityAnimation)
                val call: Call<ResponseBody> = ApiClient.getApiService().validateStudentCall(
                    "0",trafficNumber.text.toString(),tryFileNo.text.toString()
                )
                progressBarDialog!!.show()

                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        progressBarDialog!!.dismiss()
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
                        progressBarDialog!!.dismiss()
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
        var date = birthDate.substring(0,10)
        var name = dialog.findViewById<View>(R.id.fullNameVal) as TextView
        var nameArabic = dialog.findViewById<View>(R.id.fullNameArabicVal) as TextView
        var dateOfBirth = dialog.findViewById<View>(R.id.dateOfBirthVal) as TextView
        var ok = dialog.findViewById<View>(R.id.ok)as TextView
        var email = dialog.findViewById<View>(R.id.emailVal) as EditText
        var close = dialog.findViewById<View>(R.id.close) as ImageView
        name.text = fullName.toString()
        nameArabic.text = fullNameArabic.toString()
        dateOfBirth.text = date.toString()
        dialog.show()
        close.setOnClickListener {
            dialog.dismiss()
        }
        ok.setOnClickListener {
            if (email.text.isEmpty()) {
                showLoginErrorPopUp("Alert","Field Cannot be Left Empty")
            } else if(!AppUtils.isValidEmail(email.text.toString())){
                showLoginErrorPopUp("Alert","Enter a Valid Email")
            } else {
                val call: Call<ResponseBody> = ApiClient.getApiService().sendConfirmEmailCall(
                    email.text.toString(), fullName, fullNameArabic, birthDate
                )
                progressBarDialog!!.show()

                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        progressBarDialog!!.dismiss()

                        val responseData = response.body()
                        if (responseData != null) {
                            val jsonObject = JSONObject(responseData.string())
                            if (jsonObject.has("status")) {
                                val status = jsonObject.optString("status")
                                if (status.equals("success")) {
                                    val responseArray: JSONObject =
                                        jsonObject.optJSONObject("details")
                                    val otp: String = responseArray.optString("otp")
                                    showVerificationCodePopUp(
                                        fullName,
                                        fullNameArabic,
                                        date,
                                        trafficNoVal,
                                        tryFileNoVal,
                                        gender,
                                        email.text.toString(),
                                        otp
                                    )
                                } else {
                                    showLoginErrorPopUp("Alert", "Error Loading Data")
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        progressBarDialog!!.dismiss()
                        showLoginErrorPopUp("Alert", "Error Loading Data")
                    }
                })
            }

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
        email: String,
        otpVal: String
    ) {
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.verification_alert)
        dialog.setCancelable(false)
        var otp = dialog.findViewById<View>(R.id.otpVal) as EditText
        var ok = dialog.findViewById<View>(R.id.ok)as TextView
        var cancel = dialog.findViewById<View>(R.id.cancel_button)
        cancel.setOnClickListener {
            dialog.dismiss()
        }
        ok.setOnClickListener {
            Log.e("OTP",otpVal)
            Log.e("OTP",otp.text.toString())
            if(otp.text.equals("")) {
                showLoginErrorPopUp("Alert","Cannot be left Empty")
            } else {
                if (otp.text.toString().equals(otpVal)) {
                    val intent = Intent(context, SignUpDetailActivity::class.java)
                    intent.putExtra("fullName", fullName)
                    intent.putExtra("fullNameArabic", fullNameArabic)
                    intent.putExtra("birthDate", birthDate)
                    intent.putExtra("gender", gender)
                    intent.putExtra("trafficNumber", trafficNoVal)
                    intent.putExtra("tryFileNo", tryFileNoVal)
                    intent.putExtra("email", email)
                    startActivity(intent)
                } else if(otp.text.toString() == "") {
                    showLoginErrorPopUp("Alert","Cannot be left Empty")
                } else {
                    showLoginErrorPopUp("Alert","Verification Code Invalid")
                }
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