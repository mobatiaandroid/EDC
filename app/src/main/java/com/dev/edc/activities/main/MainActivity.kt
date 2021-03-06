
package com.dev.edc.activities.main

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
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
import com.dev.edc.activity.login.LoginActivityNew
import com.dev.edc.common.CommonMethods
import com.dev.edc.common_classes.ApiClient
import com.dev.edc.common_classes.ProgressBarDialog
import kotlinx.android.synthetic.main.activity_main.*
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
    lateinit var totalText: TextView
    lateinit var table: TableLayout
    var progressBarDialog: ProgressBarDialog? = null
    var no = 0
    lateinit var orderID: ArrayList<String>
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
        orderID = ArrayList()
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
        totalText = findViewById(R.id.text2)
        table = findViewById(R.id.table)
        total.text = "0.0"
        total2.text = "0.0"

        payButton.visibility = View.GONE
        total.visibility = View.GONE
        text2.visibility = View.GONE
        table.visibility = View.GONE
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
            val intent = Intent(context, LoginActivityNew::class.java)
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
                for (i in orderList.indices){
                    orderID.add(orderList[i].vcFeeCd)
                }
                if (total.text.toString().equals("0.0")) {
                    CommonMethods.showLoginErrorPopUp(context,"Alert","No Course Selected")
                    } else {
                    val intent = Intent(context, PaymentActivity::class.java)
                    intent.putStringArrayListExtra("orderID", orderID)
//                intent.putExtra("coursesList", coursesList)
                    intent.putExtra("total", total.text.toString())
                    startActivity(intent)
                }

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
            payButton.visibility = View.VISIBLE
            course.text = coursesSelectorList[checkedItem]
            if (orderList.contains(coursesList[checkedItem])) {
                Toast.makeText(context,"Course already added",Toast.LENGTH_SHORT).show()
            } else {
                orderList.add(coursesList[checkedItem])
            }
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
        if (totalPrice == 0.0) {
            total.visibility = View.GONE
            payButton.visibility = View.GONE
            text2.visibility = View.GONE
            table.visibility = View.GONE
        } else {
            total.visibility = View.VISIBLE
            payButton.visibility = View.VISIBLE
            text2.visibility = View.VISIBLE
            table.visibility = View.VISIBLE
        }
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