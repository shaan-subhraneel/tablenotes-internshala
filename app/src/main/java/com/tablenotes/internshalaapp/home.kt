package com.tablenotes.internshalaapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class home : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        auth = Firebase.auth
        val logout_button = findViewById<TextView>(R.id.logout_button)
        val add_todo = findViewById<View>(R.id.add_todo)
        val currentUserName = findViewById<TextView>(R.id.textView5)
        val currentAge = findViewById<TextView>(R.id.textView7)
        val currentdob = findViewById<TextView>(R.id.textView9)
        val som1 = findViewById<TextView>(R.id.textView14)
        val som2 = findViewById<ConstraintLayout>(R.id.som2)
        val currenttimestamp = findViewById<TextView>(R.id.textView12)
        val user_todolist1 = findViewById<CardView>(R.id.user_todolist)
        val toggle_button = findViewById<TextView>(R.id.logout_button2)
        userRecyclerView = findViewById(R.id.userlist)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            som2.setBackgroundResource(R.color.white)
            currentUserName.setTextColor(Color.BLACK)
            currentAge.setTextColor(Color.BLACK)
            currentdob.setTextColor(Color.BLACK)
            currenttimestamp.setTextColor(Color.BLACK)
            som1.setTextColor(Color.BLACK)
            add_todo.setBackground(getDrawable(R.drawable.ic_baseline_add_circle_24_white))
        }
        userArrayList = arrayListOf<User>()
        database = FirebaseDatabase.getInstance().getReference("users")
        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        if (userSnapshot.key.toString()==auth.currentUser?.uid.toString()){
                            currentUserName.setText(userSnapshot.child("name").value.toString())
                            currentAge.setText(userSnapshot.child("age").value.toString())
                            currentdob.setText(userSnapshot.child("dob").value.toString())
                            currenttimestamp.setText(userSnapshot.child("reg_date").value.toString())

                            continue;
                        }
                        val user = userSnapshot.getValue(User::class.java)
                        userArrayList.add(user!!)
                    }
                    userRecyclerView.adapter = UserAdapter(userArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        logout_button.setOnClickListener{
            auth.signOut();
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        add_todo.setOnClickListener{
            val intent = Intent(this, todo::class.java)
            startActivity(intent)
        }
        user_todolist1.setOnClickListener{
            val intent = Intent(this, user_todolist::class.java)
            startActivity(intent)
        }
        toggle_button.setOnClickListener{
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO) //when dark mode is enabled, we use the dark theme
            } else {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            }
            }

        }
    }
