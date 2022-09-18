package com.tablenotes.internshalaapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoListAdapter(private val todoList: ArrayList<TodoList>) : RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val data : TextView = itemView.findViewById(R.id.todo_item)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_todo_card,parent,false)
        return TodoListAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoListAdapter.ViewHolder, position: Int) {
        val currentItem = todoList[position]

        holder.data.text = currentItem.data
    }

    override fun getItemCount(): Int {
        return todoList.size
    }
}