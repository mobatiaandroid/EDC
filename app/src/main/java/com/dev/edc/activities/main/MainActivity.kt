
package com.dev.edc.activities.main

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.edc.R
import com.dev.edc.activities.LoginActivity
import com.dev.edc.activities.main.adapter.CourseAdapter
import com.dev.edc.activities.main.model.CourseResponse
import com.dev.edc.activities.main.model.Courses
import com.dev.edc.activities.payment.PaymentActivity
import com.dev.edc.common.CommonMethods
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
    lateinit var coursesObject: CourseResponse
    lateinit var orderList: ArrayList<Courses>
    lateinit var recyclerView: RecyclerView
    lateinit var total: TextView
    lateinit var total2: TextView
    lateinit var payButton: LinearLayout
    var progressBarDialog: ProgressBarDialog? = null
    var no = 0
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
        total2 = findViewById(R.id.totalPrice2)
        payButton = findViewById(R.id.payButton)
        total.text = "0.0"
        total2.text = "0.0"


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
                    if (courseResponse.status.equals("success")) {

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


        backButton.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in_activity,R.anim.fade_out_activity)
        }
        selectBranch.setOnClickListener {
            showBranchListPopUp()
        }
        selectCourse.setOnClickListener {
            no = no + 1
            showCourseListPopUp()
        }
        payButton.setOnClickListener {
            if (CommonMethods.isInternetAvailable(context)) {
                val intent = Intent(context, PaymentActivity::class.java)
                startActivity(intent)

            } else {
                CommonMethods.showLoginErrorPopUp(context,"Alert","Check Internet Connection")
            }
        }
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

            orderList.add(coursesList[checkedItem])
            findTotal()
            val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
            itemTouchHelper.attachToRecyclerView(recyclerView)
            recyclerView.adapter = CourseAdapter(context,orderList,no.toString())
            recyclerView.layoutManager = LinearLayoutManager(context)

        }
        builder.setNegativeButton("Cancel", null)

        val dialog = builder.create()
        dialog.show()
    }

    private fun findTotal() {
        var totalPrice = 0.0
        for (i in orderList.indices) {
            totalPrice = (totalPrice + orderList[i].decAmount.toFloat())
        }
        total.text = totalPrice.toString()
        total2.text = totalPrice.toString()
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
    val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
        ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {

            val position = viewHolder.adapterPosition
            orderList.removeAt(position)
            recyclerView.adapter!!.notifyDataSetChanged()
            findTotal()
        }
    }
}