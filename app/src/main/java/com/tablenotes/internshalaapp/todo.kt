package com.tablenotes.internshalaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class todo : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)

        val todo_text = findViewById<EditText>(R.id.todo_text)
        val todo_submit = findViewById<TextView>(R.id.todo_submit)
        auth = Firebase.auth
        todo_submit.setOnClickListener{
            if (todo_text.text.toString().isNotEmpty()){
                database = FirebaseDatabase.getInstance().getReference("todo")
                val Todo = TodoList(todo_text.text.toString())
                val current_uid = auth.currentUser?.uid
                database.child(current_uid.toString()).child(System.currentTimeMillis().toString()).setValue(Todo).addOnCompleteListener{
                    if (it.isSuccessful){
                        val intent = Intent(this, user_todolist::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this, it.exception.toString()+" Please Try Again Later", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                Toast.makeText(this, "Please Fill in All the Fields", Toast.LENGTH_SHORT).show()
            }
        }

    }
}