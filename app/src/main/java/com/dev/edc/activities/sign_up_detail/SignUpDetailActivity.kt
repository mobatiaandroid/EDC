package com.dev.edc.activities.sign_up_detail

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
import com.dev.edc.activities.sign_up.SignUpActivity
import com.dev.edc.activity.login.LoginActivityNew
import com.dev.edc.common_classes.ApiClient
import com.dev.edc.common_classes.AppUtils
import com.dev.edc.common_classes.ProgressBarDialog
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
    lateinit var  nameEnglish: TextView
    lateinit var  nameArabic: TextView
    lateinit var  emiratesID: EditText
    lateinit var  branch: TextView
    lateinit var  selectBranch: LinearLayout
    lateinit var  language: TextView
    lateinit var  selectLanguage: LinearLayout
    lateinit var  nationality: TextView
    lateinit var  selectNationality: LinearLayout
    lateinit var  dateOfBirth: TextView
    lateinit var  selectDateOfBirth: ImageView
    lateinit var  password: EditText
    lateinit var  showPassword: ImageView
    lateinit var  confirmPassword: EditText
    lateinit var  showConfirmPassword: ImageView
    lateinit var  signUpButton: TextView
    lateinit var  backButton: ImageView
    lateinit var  context: Context
    lateinit var  extras: Bundle
    lateinit var  emailID: TextView
    var progressBarDialog: ProgressBarDialog? = null
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
//    val branches = arrayOf("Dubai", "Abu Dhabi", "Sharjah", "Al Ain", "Ajman")
//    val languages = arrayOf("English","Arabic","Chinese", "Spanish", "Hindi")
//    var branches : Array<String> = arrayOf()
//    var languages : Array<String> = arrayOf()
//    var nations : Array<String> = arrayOf()
    val branches = arrayOf("Abu Dhabi", "Al Ain", "Delma Island", "Madinat Zayed")
    val languages = arrayOf("English", "Arabic")
    //    var languages : Array<String> = arrayOf()
//    var nations : Array<String> = arrayOf()
//    val nations = arrayOf("India", "UAE")
        val nations = arrayOf("Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua & Deps", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Central African Rep", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo", "Congo {Democratic Rep}", "Costa Rica", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland {Republic}", "Israel", "Italy", "Ivory Coast", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea North", "Korea South", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar, {Burma}", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russian Federation", "Rwanda", "St Kitts & Nevis", "St Lucia", "Saint Vincent & the Grenadines", "Samoa", "San Marino", "Sao Tome & Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Togo", "Tonga", "Trinidad & Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe")
//    val nations = arrayOf("Emirati","Saudi","Bangladeshi","Indian","Pakistani")
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
        selectBranch = findViewById(R.id.branchSelector)
        language = findViewById(R.id.language)
        selectLanguage = findViewById(R.id.languageSelector)
        nationality = findViewById(R.id.nationality)
        selectNationality = findViewById(R.id.nationalitySelector)
        dateOfBirth = findViewById(R.id.dateOfBirth)
        selectDateOfBirth = findViewById(R.id.selectDateOFBirth)
        password = findViewById(R.id.password)
        showPassword = findViewById(R.id.showPassword)
        confirmPassword = findViewById(R.id.confirmPassword)
        showConfirmPassword = findViewById(R.id.showConfirmPassword)
        emailID = findViewById(R.id.emailID)
        signUpButton = findViewById(R.id.signUpButton)
        backButton = findViewById(R.id.backButton)
        progressBarDialog = ProgressBarDialog(context)
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
        nameEnglish.text = extras.getString("fullName")
        nameArabic.text = extras.getString("fullNameArabic")
        dateOfBirth.text = extras.getString("birthDate")
        emailID.text = extras.getString("email")
        trafficNumber.text = trafficNumberVal
        tryFileNo.text = tryFileNoVal
        selectBranch.setOnClickListener {
            showBranchListPopUp()
        }
        signUpButton.setOnClickListener {
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



    private fun signUpAPICall() {
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
                emailID.text.toString(),
                password.text.toString(),
                "",
                "2",
                nameArabic.text.toString(),
                nameEnglish.text.toString(),
                "M",
                "05-07-2000",
                ""
            )
            progressBarDialog!!.show()
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    progressBarDialog!!.dismiss()
                    val responseData = response.body()
                    Log.e("Response", responseData.toString())
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
                    progressBarDialog!!.dismiss()
                }

            })
        }
    }


    private fun showLanguagesListPopUp() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Select Training Language")
        var checkedItem = -1
//        val call1: Call<ResponseBody> = ApiClient.getApiService().languagesListCall()
//        call1.enqueue(object : Callback<ResponseBody> {
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                val responseData = response.body()
//                Log.e("Response",responseData.toString())
//                if (responseData != null) {
//                    try {
//
//
//                        val jsonObject = JSONObject(responseData.string())
//                        if (jsonObject.has("status")) {
//                            val status = jsonObject.optString("status")
//                            if (status.equals("success")) {
//                                val responseArray: JSONArray = jsonObject.optJSONArray("languages")
//                                Log.e("Response",responseArray.toString())
//                                for (i in 0 until responseArray.length()) {
//                                    val dataObject = responseArray.getJSONObject(i)
//                                    countryID = dataObject.optString("id")
//                                    countryCode = dataObject.optString("code")
//                                    countryName = dataObject.optString("name")
//                                    nations[i] = countryName
//                                }
//
//                            }
//
//                        }
//                    } catch (e: Exception) {
//                       Log.e("Error",e.toString())
//                    }
//                    } else {
//                        Toast.makeText(context, "Some Error Occurred", Toast.LENGTH_SHORT).show()
//                    }
//
//            }
//
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                Toast.makeText(context,"Some Error Occurred",Toast.LENGTH_SHORT).show()
//            }
//
//        })
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
        builder.setTitle("Select Nationality")
        var checkedItem = -1
//        val call3: Call<ResponseBody> = ApiClient.getApiService().countriesListCall()
//        call3.enqueue(object : Callback<ResponseBody> {
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                val responseData = response.body()
//                Log.e("Response",responseData.toString())
//
//                if (responseData != null) {
//                    val jsonObject = JSONObject(responseData.string())
//                    if (jsonObject.has("status")) {
//                        val status = jsonObject.optString("status")
//                        if (status.equals("success")) {
//                            val responseArray: JSONObject = jsonObject.optJSONObject("countries")
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
//            }
//
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                Toast.makeText(context,"Some Error Occurred",Toast.LENGTH_SHORT).show()
//            }
//
//        })
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
        builder.setTitle("Select Branch")
        var checkedItem = -1
//        val call2: Call<ResponseBody> = ApiClient.getApiService().branchesListCall()
//        call2.enqueue(object : Callback<ResponseBody> {
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                val responseData = response.body()
//                Log.e("Response",responseData.toString())
//                if (responseData != null) {
//                    val jsonObject = JSONObject(responseData.string())
//                    if (jsonObject.has("status")) {
//                        val status = jsonObject.optString("status")
//                        if (status.equals("success")) {
//                            val responseArray: JSONObject = jsonObject.optJSONObject("countries")
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
//            }
//
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                Toast.makeText(context,"Some Error Occurred",Toast.LENGTH_SHORT).show()
//            }
//
//        })
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
            val homeIntent = Intent(context, LoginActivityNew::class.java)
            startActivity(homeIntent)
            finish()
        }
        dialog.show()
    }

    private fun showErrorPopUp(message: String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_alert)
        val text = dialog.findViewById<View>(R.id.textDialog) as TextView
        text.text = message
        dialog.show()
    }
}