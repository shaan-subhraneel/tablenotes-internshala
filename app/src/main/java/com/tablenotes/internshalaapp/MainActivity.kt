package com.tablenotes.internshalaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth
        if (auth.currentUser!=null){
            val intent = Intent(this, home::class.java)
            startActivity(intent)
            finish()
        }
        val reg_button = findViewById<TextView>(R.id.reg_button)
        val login_submit = findViewById<TextView>(R.id.login_submit)

        val login_pass = findViewById<EditText>(R.id.login_pass)
        val login_email = findViewById<EditText>(R.id.login_email)

        reg_button.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
            finish()
        }

        login_submit.setOnClickListener{
            if (login_email.text.toString().isNotEmpty() && login_pass.text.toString().isNotEmpty()){
                auth.signInWithEmailAndPassword(login_email.text.toString(), login_pass.text.toString()).addOnCompleteListener{
                    if (it.isSuccessful){
                        val intent = Intent(this, home::class.java)
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