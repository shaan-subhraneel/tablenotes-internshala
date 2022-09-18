package com.tablenotes.internshalaapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private val userList: ArrayList<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name : TextView = itemView.findViewById(R.id.textView5)
        val age : TextView = itemView.findViewById(R.id.textView7)
        val dob : TextView = itemView.findViewById(R.id.textView9)
        val reg_date : TextView = itemView.findViewById(R.id.textView12)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_card,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = userList[position]

        holder.name.text = currentItem.name
        holder.age.text = currentItem.age
        holder.dob.text = currentItem.dob
        holder.reg_date.text = currentItem.reg_date
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}