package com.example.sanzu.eclass.LoginFIles

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.sanzu.eclass.R
import com.example.sanzu.eclass.hactivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class Login_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val auth= FirebaseAuth.getInstance()
        if (auth.getCurrentUser() != null){
            val hintent=Intent(this,hactivity::class.java)
            startActivity(hintent)
            finish()
            }
        button_login.setOnClickListener {
            performLogin()
        }
       goto_register_view.setOnClickListener {
           goBackToRegister()
       }

    }
    private fun goBackToRegister()
    {
        val intent = Intent(this@Login_Activity, Registration_Activity::class.java)
        startActivity(intent)
        finish()
    }
 private fun  performLogin(){
        val email = login_email.text.toString()
        val password = login_password.text.toString()
        Log.d("LoginActivity", "email is:" + email)
        Log.d("LoginActivity", "Password is:" + password)

     if (email.isEmpty() || password.isEmpty()){
         Toast.makeText(this,"Please Enter valid Username/Password", Toast.LENGTH_LONG).show()
         return
     }
     FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
         .addOnCompleteListener {
             if (!it.isSuccessful) return@addOnCompleteListener
             Log.d("login","succesfully logged in succesfully")
             val hactivityIntent = Intent(this@Login_Activity, hactivity::class.java)
             startActivity(hactivityIntent)
             finish()
         }
         .addOnFailureListener {
             Log.d("login","failed to login:${it.message}")
             if(it.message=="There is no user record corresponding to this identifier. The user may have been deleted.")
             {
                 Toast.makeText(this,"Invalid Email",Toast.LENGTH_LONG).show()
             }
            else if(it.message=="The password is invalid or the user does not have a password.")
             {
                 Toast.makeText(this,"Invalid Password",Toast.LENGTH_LONG).show()
             }
             else{
                 Toast.makeText(this,"Unknown Network Error",Toast.LENGTH_LONG).show()
             }

         }

         }
        }



