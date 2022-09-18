package com.tablenotes.internshalaapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class Register : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        // Initialize Firebase Auth
        auth = Firebase.auth
        val login_button = findViewById<TextView>(R.id.login_button)
        val reg_submit = findViewById<TextView>(R.id.reg_submit)

        val reg_name = findViewById<EditText>(R.id.regname)
        val reg_dob = findViewById<EditText>(R.id.editTextdob)
        val reg_age = findViewById<EditText>(R.id.editTextage)
        val reg_pass = findViewById<EditText>(R.id.regpass)
        val reg_email = findViewById<EditText>(R.id.regemail)



        login_button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        reg_submit.setOnClickListener{
            if (reg_name.text.toString().isNotEmpty() && reg_dob.text.toString().isNotEmpty() &&
                reg_age.text.toString().isNotEmpty() && reg_pass.text.toString().isNotEmpty() &&
                reg_email.text.toString().isNotEmpty())
            {
                if (Patterns.EMAIL_ADDRESS.matcher(reg_email.text.toString()).matches()){
                    if (reg_pass.text.toString().length>=6){
                        auth.createUserWithEmailAndPassword(reg_email.text.toString(), reg_pass.text.toString()).addOnCompleteListener{
                            if (it.isSuccessful){
                                val current_uid = it.getResult().user?.uid
                                val reg_date = it.getResult().user?.metadata?.creationTimestamp

                                database = FirebaseDatabase.getInstance().getReference("users")
                                val User = User(reg_name.text.toString(),reg_age.text.toString(),reg_dob.text.toString(),
                                    reg_date.toString())
                                database.child(current_uid.toString()).setValue(User).addOnCompleteListener{
                                    if (it.isSuccessful) {
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
                                Toast.makeText(this, it.exception.toString()+" Please Try Again Later", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    else{
                        Toast.makeText(this, "Password should be atleast 6 characters", Toast.LENGTH_SHORT).show()
                    }

                }
                else{
                    Toast.makeText(this, "Email Not in proper Format", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this, "Please Fill in All the Fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}