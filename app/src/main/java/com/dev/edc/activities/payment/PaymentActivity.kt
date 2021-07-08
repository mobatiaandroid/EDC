package com.dev.edc.activities.payment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dev.edc.R
import com.dev.edc.activities.main.MainActivity
import com.dev.edc.activities.main.model.Courses
import com.dev.edc.activity.login.LoginActivityNew
import com.dev.edc.common.CommonMethods
import com.dev.edc.common_classes.ApiClient
import com.dev.edc.common_classes.ProgressBarDialog
import okhttp3.ResponseBody
import org.json.JSONObject
import payment.sdk.android.PaymentClient
import payment.sdk.android.cardpayment.CardPaymentData.IntentResolver.getFromIntent
import payment.sdk.android.cardpayment.CardPaymentRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentActivity : AppCompatActivity() {
    lateinit var  extras: Bundle
    lateinit var context: Context
    lateinit var coursesList: ArrayList<Courses>
    lateinit var orderID: ArrayList<String>
    lateinit var totalPrice: String
    lateinit var order_id: String
    lateinit var merchantOrderReference: String
    lateinit var order_reference: String
    lateinit var order_paypage_url: String
    lateinit var authorization: String
    var userID = 1
    var progressBarDialog: ProgressBarDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        initaliseUI()

    }

    override fun onBackPressed() {
//        CommonMethods.showLoginErrorPopUp(context,"Alert","Transaction Cancelled")
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
    private fun initaliseUI() {
        context = this
        progressBarDialog = ProgressBarDialog(context)

        try {


            extras = intent.extras!!
            orderID = intent.getStringArrayListExtra("orderID")
            totalPrice = intent.getStringExtra("total")
            Log.e("orders", orderID.toString())
        } catch (e: Exception){
            Log.e("Error",e.toString())
        }
        Log.e("ID",orderID.joinToString(separator = ","))
        val call: Call<ResponseBody> = ApiClient.getApiService().paymentCall(
            userID.toString(),orderID.joinToString(separator = ","),totalPrice
        )
        progressBarDialog!!.show()
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                progressBarDialog!!.dismiss()
                val responseData = response.body()
                if (responseData != null) {
                    val jsonObject = JSONObject(responseData.string())
                        if (jsonObject.has("status")) {
                            val status = jsonObject.optString("status")
                            if (status.equals("success")) {
                                order_id = jsonObject.optString("order_id")
                                merchantOrderReference = jsonObject.optString("merchantOrderReference")
                                order_reference = jsonObject.optString("order_reference")
                                order_paypage_url = jsonObject.optString("order_paypage_url")
                                authorization = jsonObject.optString("authorization")
                                val code: String = order_paypage_url.split("=").toTypedArray()[1]
                                val request = CardPaymentRequest(authorization, code)
                                val paymentClient = PaymentClient(context as Activity)
                                paymentClient.launchCardPayment(request, 0)

                            }
                        }
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                progressBarDialog!!.dismiss()
            }

        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            Toast.makeText(context, "Transaction cancelled", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        } else {
            if (requestCode == 0) {
                val cardPaymentData = getFromIntent(data)
                if (cardPaymentData.code == 2) {

                    paymentSuccessCall(order_id,userID)
                } else {
                    Toast.makeText(context, "Transaction failed", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                }
            }
        }
    }

    private fun paymentSuccessCall(orderId: String, userID: Int) {
        val call: Call<ResponseBody> = ApiClient.getApiService().paymentSuccessCall(
            userID.toString(),orderId
        )
        progressBarDialog!!.show()
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                progressBarDialog!!.dismiss()
                val responseData = response.body()
                if (responseData != null) {
                    val jsonObject = JSONObject(responseData.string())
                    if (jsonObject.has("status")) {
                        val status = jsonObject.optString("status")
                        if (status.equals("success")) {
                            CommonMethods.showLoginErrorPopUp(context,"Alert","Payment Successful")
                            Handler().postDelayed({
                                val intent = Intent(context, LoginActivityNew::class.java)
                                startActivity(intent)
                            },2000)
                        } else {
                            CommonMethods.showLoginErrorPopUp(context,"Alert","Cannot continue. Please try again later")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                progressBarDialog!!.dismiss()
                CommonMethods.showLoginErrorPopUp(context,"Alert","Cannot continue. Please try again later")
            }

        })
    }
}