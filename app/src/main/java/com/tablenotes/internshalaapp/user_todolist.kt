package com.tablenotes.internshalaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class user_todolist : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var database2: DatabaseReference
    private lateinit var todoRecyclerView: RecyclerView
    private lateinit var todoArrayList: ArrayList<TodoList>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_todolist)
        auth = Firebase.auth
        todoRecyclerView = findViewById(R.id.todolist)
        todoRecyclerView.layoutManager = LinearLayoutManager(this)
        todoRecyclerView.setHasFixedSize(true)

        val name_of_user = findViewById<TextView>(R.id.nameofuser)
        database2 = FirebaseDatabase.getInstance().getReference("users")
        database2.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        if (userSnapshot.key.toString()==auth.currentUser?.uid.toString()){
                            name_of_user.setText(userSnapshot.child("name").value.toString()+"'s To-do Lists")
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        todoArrayList = arrayListOf<TodoList>()
        database = FirebaseDatabase.getInstance().getReference("todo")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (todoSnapshot in snapshot.children) {
                        if (todoSnapshot.key.toString()==auth.currentUser?.uid.toString()){
                            for (todoSnapshot1 in snapshot.child(todoSnapshot.key.toString()).children) {
                                val todo1 = todoSnapshot1.getValue(TodoList::class.java)
                                todoArrayList.add(todo1!!)
                            }
                        }
                    }
                    todoRecyclerView.adapter = TodoListAdapter(todoArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
            })
        }
    }
