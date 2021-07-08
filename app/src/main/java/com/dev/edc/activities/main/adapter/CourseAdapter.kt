package com.dev.edc.activities.main.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dev.edc.R
import com.dev.edc.activities.main.model.Courses

class CourseAdapter(context: Context, orderList: ArrayList<Courses>): RecyclerView.Adapter<CourseAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val no = itemView.findViewById<View>(R.id.no) as TextView
        val vcFeeDesc = itemView.findViewById<View>(R.id.name) as TextView
        val decAmount = itemView.findViewById<View>(R.id.cost) as TextView
    }
    val list = orderList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.courses_adapter, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.no.text = list.size.toString()
        Log.e("Desc", holder.vcFeeDesc.text as String)
        holder.vcFeeDesc.text = list[position].vcFeeDesc
        holder.decAmount.text = list[position].decAmount
    }

    override fun getItemCount(): Int {
        return list.size
    }
}