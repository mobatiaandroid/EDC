package com.dev.edc.activities

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
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
    lateinit var  branch: TextView
    lateinit var  selectBranch: ImageView
    lateinit var  language: TextView
    lateinit var  selectLanguage: ImageView
    lateinit var  nationality: TextView
    lateinit var  selectNationality: ImageView
    lateinit var  dateOfBirth: TextView
    lateinit var  selectDateOfBirth: ImageView
    lateinit var  password: EditText
    lateinit var  showPassword: ImageView
    lateinit var  confirmPassword: EditText
    lateinit var  showConfirmPassword: ImageView
    lateinit var  signUpButton: ImageView
    lateinit var  backButton: ImageView
    lateinit var  context: Context
    lateinit var  extras: Bundle
    lateinit var  emailID: EditText

    var studentNumber = 791088
    var unifiedID = 34166372
    var languageID = ""
    var languageName = ""
    var countryID = ""
    var countryCode = ""
    var countryName = ""
    var countryPhoneCode = ""
    var branchID = ""
    var branchName = ""
    var branches : Array<String> = arrayOf()
    var languages : Array<String> = arrayOf()
    var nations : Array<String> = arrayOf()
    var calendar: Calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_detail)
        setListValues()
        initialiseUI()

    }

    private fun setListValues() {
        val call1: Call<ResponseBody> = ApiClient.getApiService().languagesListCall()
        call1.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                val responseData = response.body()
//                Log.e("Response",responseData.toString())
//                if (responseData != null) {
//                    val jsonObject = JSONObject(responseData.string())
//                    if (jsonObject.has("status")) {
//                        val status = jsonObject.optString("status")
//                        if (status.equals("success")) {
//                            val responseArray: JSONObject = jsonObject.optJSONObject("languages")
//                            for (i in 0 until responseArray.length()) {
//                                val dataObject = responseArray.getJSONObject(i.toString())
//                                branchID =  dataObject.optString("id")
//                                branchName = dataObject.optString("vcRegName")
//                                branches[i] = branchName
//                            }
//
//                        }
//                    }
//                } else {
//                    Toast.makeText(context,"Some Error Occurred",Toast.LENGTH_SHORT).show()
//                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(context,"Some Error Occurred",Toast.LENGTH_SHORT).show()
            }

        })
        val call2: Call<ResponseBody> = ApiClient.getApiService().branchesListCall()
        call2.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                val responseData = response.body()
//                Log.e("Response",responseData.toString())
//
//                if (responseData != null) {
//                    val jsonObject = JSONObject(responseData.string())
//                    if (jsonObject.has("status")) {
//                        val status = jsonObject.optString("status")
//                        if (status.equals("success")) {
//                            val responseArray: JSONObject = jsonObject.optJSONObject("languages")
//                            for (i in 0 until responseArray.length()) {
//                                val dataObject = responseArray.getJSONObject(i.toString())
//                                countryID =  dataObject.optString("id")
//                                countryCode = dataObject.optString("code")
//                                countryName = dataObject.optString("name")
//                                nations[i] = countryName
//                            }
//
//                        }
//                    }
//                } else {
//                    Toast.makeText(context,"Some Error Occurred",Toast.LENGTH_SHORT).show()
//                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(context,"Some Error Occurred",Toast.LENGTH_SHORT).show()
            }

        })
        val call3: Call<ResponseBody> = ApiClient.getApiService().countriesListCall()
        call3.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                val responseData = response.body()
//                Log.e("Response",responseData.toString())
//                if (responseData != null) {
//                    val jsonObject = JSONObject(responseData.string())
//                    if (jsonObject.has("status")) {
//                        val status = jsonObject.optString("status")
//                        if (status.equals("success")) {
//                            val responseArray: JSONObject = jsonObject.optJSONObject("languages")
//                            for (i in 0 until responseArray.length()) {
//                                val dataObject = responseArray.getJSONObject(i.toString())
//                                languageID = dataObject.optString("id")
//                                languageName = dataObject.optString("name")
//                                languages[i] = languageName
//                            }
//
//                        }
//                    }
//                } else {
//                    Toast.makeText(context,"Some Error Occurred",Toast.LENGTH_SHORT).show()
//                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(context,"Some Error Occurred",Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun signUpCall() {
        if (trafficNumber.text == "" ||
            tryFileNo.text == "" ||
            studentNumber.equals("") ||
            unifiedID.equals("") ||
            branch.text == "" ||
            language.text == "" ||
            nationality.text == "" ||
            emailID.equals("") ||
            password.equals("") ||
            confirmPassword.equals("") ||
            nameEnglish.equals("") ||
            dateOfBirth.equals("") ||
            emiratesID.equals("")
            ){
            showErrorPopUp("Fields Cannot be left Empty.")
        } else if(!AppUtils.isValidEmail(emailID.text.toString())) {
            showErrorPopUp("Enter a valid Email.")
        } else if (password.text.toString() != confirmPassword.text.toString()) {
            showErrorPopUp("Passwords do not match.")
        } else {
            signUpAPICall()
        }
    }

    private fun signUpAPICall() {
        val call: Call<ResponseBody> = ApiClient.getApiService().signUpCall(
            "791088",
            "34166372",
            "1190133779",
            "111210033012",
            "1",
            "",
            "1",
            "3",
            "1",
            "sanal.s@mobatia.com",
            "123",
            "",
            "2",
            "سيف الاسلام عبيد الحق",
            "SAIFUL ISLAM OBAYDUL HOQUE",
            "M",
            "05-07-2000",
            ""
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val responseData = response.body()
                Log.e("Response",responseData.toString())
                if (responseData != null) {
                    val jsonObject = JSONObject(responseData.string())
                    if (jsonObject.has("status")) {
                        val status = jsonObject.optString("status")
                        if (status.equals("success")) {
                            showSignUpSuccessDialog(context)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun showErrorPopUp(message: String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_alert)
        val text = dialog.findViewById<View>(R.id.textDialog) as TextView
//        val textHead = dialog.findViewById<View>(R.id.alertHead) as TextView
        text.text = message
//        textHead.text = head
        dialog.show()
    }

    private fun initialiseUI() {
        trafficNumber = findViewById(R.id.trafficNumber)
        tryFileNo = findViewById(R.id.tryFileNo)
        nameEnglish = findViewById(R.id.nameInEnglish)
        nameArabic = findViewById(R.id.nameInArabic)
        emiratesID = findViewById(R.id.emiratesID)
        branch = findViewById(R.id.branch)
        selectBranch = findViewById(R.id.selectBranch)
        language = findViewById(R.id.language)
        selectLanguage = findViewById(R.id.selectLanguage)
        nationality = findViewById(R.id.nationality)
        selectNationality = findViewById(R.id.selectNationality)
        dateOfBirth = findViewById(R.id.dateOfBirth)
        selectDateOfBirth = findViewById(R.id.selectDateOFBirth)
        password = findViewById(R.id.password)
        showPassword = findViewById(R.id.showPassword)
        confirmPassword = findViewById(R.id.confirmPassword)
        showConfirmPassword = findViewById(R.id.showConfirmPassword)
        emailID = findViewById(R.id.emailID)
        signUpButton = findViewById(R.id.signUpButton)
        backButton = findViewById(R.id.backButton)
        val pickDate =
            DatePickerDialog.OnDateSetListener { view1: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
                calendar = Calendar.getInstance()
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = month
                calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                val s = "$dayOfMonth/$month/$year"
                val sdf = SimpleDateFormat("dd/MM/yyyy")
                try {
                    val strDate: Date = sdf.parse(s)
                    if (Date().after(strDate)) {
                        dateOfBirth.text = s
                    } else {
                        Toast.makeText(context, "Enter a Valid Date", Toast.LENGTH_SHORT)
                            .show()
                        dateOfBirth.text = ""
                        calendar = Calendar.getInstance()
                    }
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
            }
        showPassword.setOnClickListener {
            password.transformationMethod = null;
//            password.transformationMethod = PasswordTransformationMethod()
        }
        showConfirmPassword.setOnClickListener {
            confirmPassword.transformationMethod = null;
//            confirmPassword.transformationMethod = PasswordTransformationMethod()
        }
        dateOfBirth.setOnClickListener { v ->
            DatePickerDialog(
                context, pickDate,
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
            )
                .show()
        }
        dateOfBirth.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {}
        })
        selectBranch.setOnClickListener {
            showBranchListPopUp()
        }
        selectNationality.setOnClickListener {
            showNationsListPopUp()
        }
        selectLanguage.setOnClickListener {
            showLanguagesListPopUp()
        }
        backButton.setOnClickListener {
            val intent = Intent(context, SignUpActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
//            overridePendingTransition(R.anim.fade_in_activity,R.anim.fade_out_activity)
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
            signUpCall()
        }
        selectDateOfBirth.setOnClickListener {
            DatePickerDialog(
                context, pickDate,
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
            )
                .show()
        }

    }

    private fun showLanguagesListPopUp() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Select")
        var checkedItem = -1

        languages = context.resources.getStringArray(R.array.languages)
        builder.setSingleChoiceItems(languages, checkedItem) { dialog, which ->
            checkedItem = which
        }
        builder.setPositiveButton("OK") { dialog, which ->
            language.text = languages[checkedItem]
        }
        builder.setNegativeButton("Cancel", null)

        val dialog = builder.create()
        dialog.show()
    }

    private fun showNationsListPopUp() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Select")
        var checkedItem = -1
        nations = context.resources.getStringArray(R.array.countries)
        builder.setSingleChoiceItems(nations, checkedItem) { dialog, which ->
            checkedItem = which
        }
        builder.setPositiveButton("OK") { dialog, which ->

            nationality.text = nations[checkedItem]
        }
        builder.setNegativeButton("Cancel", null)

        val dialog = builder.create()
        dialog.show()
    }

    private fun showBranchListPopUp() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Select")
        var checkedItem = -1
        branches = context.resources.getStringArray(R.array.branches)
        builder.setSingleChoiceItems(branches, checkedItem) { dialog, which ->
            checkedItem = which

        }
        builder.setPositiveButton("OK") { dialog, which ->
            branch.text = branches[checkedItem]
        }
        builder.setNegativeButton("Cancel", null)

        val dialog = builder.create()
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
        val call3: Call<ResponseBody> = ApiClient.getApiService().sendConfirmEmailCall(
            "sanal2net@gmail.com",
            "SAIFUL ISLAM OBAYDUL HOQUE",
            "سيف الاسلام عبيد الحق",
            "1985-01-01T00:00:00"
        )
        call3.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

        })

    }
}