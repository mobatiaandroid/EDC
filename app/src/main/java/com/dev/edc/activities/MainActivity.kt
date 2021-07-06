
package com.dev.edc.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.media.Image
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.dev.edc.R
import com.dev.edc.common_classes.ApiClient
import com.dev.edc.common_classes.ProgressBarDialog
import com.dev.edc.common_classes.models.Courses
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var branch: TextView
    lateinit var selectBranch: ImageView
    lateinit var course: TextView
    lateinit var selectCourse: ImageView
    lateinit var context: Context
    lateinit var backButton: ImageView
    lateinit var payButton: ImageView
    lateinit var coursesObject: Courses
    var progressBarDialog: ProgressBarDialog? = null
    var vcFeeCd: String = ""
    var vcFeeDesc: String = ""
    var decAmount: String = ""
    val branches = arrayOf("Abu Dhabi", "Al Ain", "Delma Island", "Madinat Zayed")
    val courses = arrayOf("LMV", "HMV", "HGMV")
    lateinit var coursesList: ArrayList<Courses>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialiseUI()
    }

    private fun initialiseUI() {
        context = this
        progressBarDialog = ProgressBarDialog(context)
        backButton = findViewById(R.id.backButton)
        branch = findViewById(R.id.branch)
        selectBranch = findViewById(R.id.selectBranch)
        course = findViewById(R.id.course)
        selectCourse = findViewById(R.id.selectCourse)
        backButton.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in_activity,R.anim.fade_out_activity)
        }
        selectBranch.setOnClickListener {
            showBranchListPopUp()
        }
        selectCourse.setOnClickListener {
            showCourseListPopUp()
        }
    }

    private fun showCourseListPopUp() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Select Course")
        var checkedItem = -1
//        val call: Call<ResponseBody> = ApiClient.getApiService().coursesListCall()
//        progressBarDialog!!.show()
//        call.enqueue(object : Callback<ResponseBody> {
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                progressBarDialog!!.dismiss()
//                val responseData = response.body()
//                if (responseData != null) {
//                    val jsonObject = JSONObject(responseData.string())
//                    if (jsonObject.has("status")) {
//                        val status = jsonObject.optString("status")
//                        if (status.equals("success")) {
//                            val responseArray: JSONObject = jsonObject.optJSONObject("languages")
//                            for (i in 0 until responseArray.length()) {
//                                val dataObject = responseArray.getJSONObject(i.toString())
//                                coursesObject.vcFeeCd = dataObject.optString("vcFeeCd")
//                                coursesObject.vcFeeDesc = dataObject.optString("vcFeeDesc")
//                                coursesObject.decAmount = dataObject.optString("decAmount")
//                                coursesList.add(coursesObject)
//                                courses[i] = dataObject.optString("vcFeeDesc")
//                            }
//                        } else {
//                            Toast.makeText(context,"Some Error Occurred", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                progressBarDialog!!.dismiss()
//            }
//
//        })
        builder.setSingleChoiceItems(courses, checkedItem) { dialog, which ->
            checkedItem = which

        }
        builder.setPositiveButton("OK") { dialog, which ->
            branch.text = courses[checkedItem]
        }
        builder.setNegativeButton("Cancel", null)

        val dialog = builder.create()
        dialog.show()
    }

    private fun showBranchListPopUp() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Select Branch")
        var checkedItem = -1
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
}