package com.dev.edc.activities

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.dev.edc.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class SignUpDetailActivity : AppCompatActivity() {
    lateinit var  trafficNumber: TextView
    lateinit var  trafficNumberVal: String
    lateinit var  tryFileNo: TextView
    lateinit var  tryFileNoVal: String
    lateinit var  nameEnglish: EditText
    lateinit var  nameArabic: EditText
    lateinit var  emiratesID: EditText
    lateinit var  branch: EditText
    lateinit var  selectBranch: ImageView
    lateinit var  language: EditText
    lateinit var  selectLanguage: ImageView
    lateinit var  nationality: EditText
    lateinit var  selectNationality: ImageView
    lateinit var  dateOfBirth: EditText
    lateinit var  selectDateOfBirth: ImageView
    lateinit var  password: EditText
    lateinit var  showPassword: ImageView
    lateinit var  confirmPassword: EditText
    lateinit var  showConfirmPassword: ImageView
    lateinit var  signUpButton: ImageView
    lateinit var  backButton: ImageView
    lateinit var  context: Context
    lateinit var  extras: Bundle
    var calendar: Calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_detail)
        context = this
        initialiseUI()
    }

    private fun initialiseUI() {
        trafficNumber = findViewById(R.id.trafficNumber)
        tryFileNo = findViewById(R.id.tryFileNo)
        nameEnglish = findViewById(R.id.nameInEnglish)
        nameArabic = findViewById(R.id.nameInArabic)
        emiratesID = findViewById(R.id.emiratesID)
        branch = findViewById(R.id.branch)
        selectBranch = findViewById(R.id.selectBranch)
//        language = findViewById(R.id.language)
        selectLanguage = findViewById(R.id.selectLanguage)
//        nationality = findViewById(R.id.nationality)
        selectNationality = findViewById(R.id.selectNationality)
//        dateOfBirth = findViewById(R.id.dateOfBirth)
        selectDateOfBirth = findViewById(R.id.selectDateOFBirth)
//        password = findViewById(R.id.password)
        showPassword = findViewById(R.id.showPassword)
//        confirmPassword = findViewById(R.id.confirmPassword)
        showConfirmPassword = findViewById(R.id.showConfirmPassword)
        signUpButton = findViewById(R.id.signUpButton)
        backButton = findViewById(R.id.backButton)
//        val pickDate =
//            OnDateSetListener { view1: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
//                calendar = Calendar.getInstance()
//                calendar[Calendar.YEAR] = year
//                calendar[Calendar.MONTH] = month
//                calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
//                val s = "$dayOfMonth/$month/$year"
//                val sdf = SimpleDateFormat("dd/MM/yyyy")
//                try {
//                    val strDate: Date = sdf.parse(s)
//                    if (Date().after(strDate)) {
//                        dateOfBirth.setText(s)
//                    } else {
//                        Toast.makeText(context, "Enter a Valid Date", Toast.LENGTH_SHORT)
//                            .show()
//                        dateOfBirth.setText("")
//                        calendar = Calendar.getInstance()
//                    }
//                } catch (e: ParseException) {
//                    e.printStackTrace()
//                }
//            }
//        dateOfBirth.setOnClickListener { v ->
//            DatePickerDialog(
//                context, pickDate,
//                calendar[Calendar.YEAR],
//                calendar[Calendar.MONTH],
//                calendar[Calendar.DAY_OF_MONTH]
//            )
//                .show()
//        }
//        dateOfBirth.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
//            override fun afterTextChanged(s: Editable) {}
//        })

        backButton.setOnClickListener {
            val intent = Intent(context, SignUpActivity::class.java)
            startActivity(intent)
        }
        extras = intent.extras!!
        trafficNumberVal = extras.getString("trafficNumber")!!
        tryFileNoVal = extras.getString("tryFileNo")!!
//        Toast.makeText(context,trafficNumberVal,Toast.LENGTH_SHORT).show()
        trafficNumber.text = trafficNumberVal
        tryFileNo.text = tryFileNoVal
        selectBranch.setOnClickListener {
            showBranchListPopUp()
        }
        signUpButton.setOnClickListener {
            if(nameArabic.text.isEmpty() || nameEnglish.text.isEmpty() || emiratesID.text.isEmpty() /*|| branch.text.isEmpty()*/) {
                Toast.makeText(context,"Fields cannot be left empty",Toast.LENGTH_SHORT).show()
            } else {
                showSignUpSuccessDialog(context)
            }

        }
//        selectDateOfBirth.setOnClickListener {
//            DatePickerDialog(
//                context, pickDate,
//                calendar[Calendar.YEAR],
//                calendar[Calendar.MONTH],
//                calendar[Calendar.DAY_OF_MONTH]
//            )
//                .show()
//        }
    }

    private fun showBranchListPopUp() {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.list_pop_up)
        dialog.show()
    }

    private fun showSignUpSuccessDialog(context: Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_signup_success)
        val closeButton = dialog.findViewById<View>(R.id.close)
        closeButton.setOnClickListener {
            dialog.dismiss()
            val homeIntent = Intent(context, LoginActivity::class.java)
            startActivity(homeIntent)
            finish()
        }
        dialog.show()
    }
}