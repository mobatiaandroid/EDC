
package com.dev.edc.activities.main

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.edc.R
import com.dev.edc.activities.LoginActivity
import com.dev.edc.activities.main.adapter.CourseAdapter
import com.dev.edc.activities.main.model.CourseResponse
import com.dev.edc.activities.main.model.Courses
import com.dev.edc.common_classes.ApiClient
import com.dev.edc.common_classes.ProgressBarDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var branch: TextView
    lateinit var selectBranch: LinearLayout
    lateinit var course: TextView
    lateinit var selectCourse: LinearLayout
    lateinit var context: Context
    lateinit var backButton: ImageView
    lateinit var payButton: ImageView
    lateinit var coursesObject: CourseResponse
    lateinit var orderList: ArrayList<Courses>
    lateinit var recyclerView: RecyclerView
    lateinit var total: TextView
    var progressBarDialog: ProgressBarDialog? = null
    var vcFeeCd: String = ""
    var vcFeeDesc: String = ""
    var decAmount: String = ""
    val branches = arrayOf("Abu Dhabi", "Al Ain", "Delma Island", "Madinat Zayed")
    lateinit var coursesSelectorList : ArrayList<String>
    lateinit var coursesList: ArrayList<Courses>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialiseUI()
        Log.e("orderd", coursesList.toString())
    }

    private fun initialiseUI() {
        var courseResponse: CourseResponse
        coursesSelectorList = ArrayList()
        orderList = ArrayList()
        coursesList = ArrayList()
        context = this
        progressBarDialog = ProgressBarDialog(context)
        backButton = findViewById(R.id.backButton)
        branch = findViewById(R.id.branch)
        selectBranch = findViewById(R.id.branchSelector)
        course = findViewById(R.id.course)
        selectCourse = findViewById(R.id.courseSelector)
        recyclerView = findViewById(R.id.recycler)
        total = findViewById(R.id.totalPrice)
        total.text = "0.0"
        val call: Call<CourseResponse> = ApiClient.getApiService().coursesListCall()
        progressBarDialog!!.show()
        call.enqueue(object : Callback<CourseResponse> {
            override fun onResponse(
                call: Call<CourseResponse>,
                response: Response<CourseResponse>
            ) {
                progressBarDialog!!.dismiss()
                var i : Int = 0
                if (!response.body()!!.equals("")) {
                    courseResponse = response.body()!!
                    Log.e("Response",courseResponse.toString())
                    if (courseResponse.status.equals("success")) {
//                        Log.e("Courses",courseResponse.courses.toString())
//                        courseList!!.addAll(courseResponse.courses)
//                        Log.e("Courses",courseList.toString())
//                        Log.e("Courses",coursesSelectorList.toString())
                        coursesList = courseResponse.courses as ArrayList<Courses>
                        Log.e("Courses",coursesList[0].vcFeeDesc)
                        while (i<coursesList.size) {
                            coursesSelectorList.add(coursesList[i].vcFeeDesc)
                            i++
                        }
                    }
                }
            }

            override fun onFailure(call: Call<CourseResponse>, t: Throwable) {
                progressBarDialog!!.dismiss()
            }
        })
        Log.e("orderd", coursesList.toString())
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
        Log.e("Orders", orderList.toString())
    }

    private fun showCourseListPopUp() {
        val builder = AlertDialog.Builder(context)

        builder.setTitle("Select Course")
        var checkedItem = -1
        var coursesSelectorArray: Array<String> = coursesSelectorList.toTypedArray()
        builder.setSingleChoiceItems(coursesSelectorArray, checkedItem) { dialog, which ->
            checkedItem = which

        }
        builder.setPositiveButton("OK") { dialog, which ->
            course.text = coursesSelectorList[checkedItem]
//            Log.e("orderd", coursesList[0].toString())
            orderList.add(coursesList[checkedItem])
            findTotal()
            recyclerView.adapter = CourseAdapter(context,orderList)
            recyclerView.layoutManager = LinearLayoutManager(context)
//            recyclerView.notify()
            Log.e("orderd", orderList.toString())

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