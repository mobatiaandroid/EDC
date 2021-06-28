
package com.dev.edc.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.media.Image
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.dev.edc.R

class MainActivity : AppCompatActivity() {
    lateinit var branch: TextView
    lateinit var selectBranch: ImageView
    lateinit var course: TextView
    lateinit var selectCourse: ImageView
    lateinit var context: Context
    lateinit var backButton: ImageView
    lateinit var payButton: ImageView
    val branches = arrayOf("Dubai", "Abu Dhabi", "Sharjah", "Al Ain", "Ajman")
    val courses = arrayOf("LMV", "HMV", "HGMV")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialiseUI()
    }

    private fun initialiseUI() {
        backButton = findViewById(R.id.backButton)
//        branch = findViewById(R.id.branch)
//        selectBranch = findViewById(R.id.selectBranch)
//        course = findViewById(R.id.course)
//        selectCourse = findViewById(R.id.selectCourse)
        backButton.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in_activity,R.anim.fade_out_activity)
        }
//        selectBranch.setOnClickListener {
//            showBranchListPopUp()
//        }
//        selectCourse.setOnClickListener {
//            showCourseListPopUp()
//        }
    }

    private fun showCourseListPopUp() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Select")
        var checkedItem = -1
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
        builder.setTitle("Select")
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