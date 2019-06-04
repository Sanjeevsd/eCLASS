package com.example.sanzu.eclass.LoginFIles

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log


import android.widget.Toast
import com.example.sanzu.eclass.R
import com.example.sanzu.eclass.User_details
import com.example.sanzu.eclass.Users
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registration.*
import kotlin.math.log


class Registration_Activity : AppCompatActivity() {
    var sec:String=""
  val ref=FirebaseDatabase.getInstance().getReference("/user")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)


        register_button.setOnClickListener {
            checkValidity()

        }
        already_accnt.setOnClickListener {
            //launch login activity
            login_activ()
            finish()
        }
    }
        private fun checkValidity(){
            val userRoll= user_rollno_view.text.toString()
            val email = email_view.text.toString()
            val username = username_view.text.toString()
            val password = password_view.text.toString()
            if (User_details.bct_a_roll.contains(userRoll)){ sec="A"}
            else if (User_details.bct_b_roll.contains(userRoll)){sec="B"}
            else if (User_details.bct_c_roll.contains(userRoll)){sec="C"}
            Log.d("register","the setion is:::::::::$sec and roll:$userRoll")
            if (User_details.bct_b_roll.contains(userRoll) || User_details.bct_a_roll.contains(userRoll) || User_details.bct_c_roll.contains(userRoll)) {
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this, "Please Enter valid Username/Password", Toast.LENGTH_LONG).show()
                    return
                }
                val regcheck = ref.child("/${sec}/$userRoll/")
                regcheck.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val checkregistered = p0.child("/LOGGEDS/").value.toString()
                        Log.d("register", "the user is rgistered:$checkregistered")
                        if (checkregistered == "FALSE" || checkregistered == "false") {
                            regcheck.child("/LOGGEDS").setValue("true")
                            performRegister()
                        } else if (checkregistered == "true" || checkregistered == "TRUE") {
                            Toast.makeText(this@Registration_Activity, "Roll No Taken", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
            else {
                Log.d("register","Invalid user")
                Toast.makeText(this,"Invalid User",Toast.LENGTH_LONG).show()
                return
            }
        }
        private fun performRegister()
        {
            val email = email_view.text.toString()
            val userRollNo= user_rollno_view.text.toString()
            val username = username_view.text.toString()
            val password = password_view.text.toString()
           var regisered:Boolean=false
            var adminauth:Boolean=false
            if (User_details.bct_a_roll.contains(userRollNo)){ sec="A"}
            else if (User_details.bct_b_roll.contains(userRollNo)){sec="B"}
            else if (User_details.bct_c_roll.contains(userRollNo)){sec="C"}
            Log.d("register","the setion is:::::::::$sec and roll:$userRollNo")

            Log.d("regiter","statsus:$regisered")
            if (regisered==true){
                Log.d("register","again check::$regisered")
                Toast.makeText(this,"Roll no Already Taken",Toast.LENGTH_SHORT).show()
                return
            }


                if (User_details.admin_eclass.contains(userRollNo)){
                     adminauth=true
                }
                Log.d("Registration", "Password is:" + password)
                Log.d("Registration", "Username is:" + userRollNo)

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener {
                        if (!it.isSuccessful) return@addOnCompleteListener
                        //else if successful
                        Log.d("register","Admin Previlage:$adminauth")
                        subscribeTO(sec)
                        Log.d("register","succesfully created user with uid:")
                        Toast.makeText(this,"Successfully Registered",Toast.LENGTH_SHORT).show()
                        saveusertodatabase(adminauth,sec)

                    }
                    .addOnFailureListener {
                        Log.d("register","Failed:${it.message}")
                        if(it.message=="The email address is badly formatted.")
                        {
                            Toast.makeText(this,"Invalid Email",Toast.LENGTH_SHORT).show()
                    }
                        else if(it.message=="The given password is invalid. [ Password should be at least 6 characters ]")
                        {Toast.makeText(this,"${it.message}",Toast.LENGTH_SHORT).show()
                        }
                    }


        }
    private fun login_activ()
    {
        Log.d("register","logging")
        val intent = Intent(this@Registration_Activity, Login_Activity::class.java)
        startActivity(intent)
        finish()
    }
   private fun saveusertodatabase(admin:Boolean,section:String){

        val sid=FirebaseAuth.getInstance().uid?:""
        val ref=FirebaseDatabase.getInstance().getReference("/user/$sid")
        val user= Users(sid,username_view.text.toString(),user_rollno_view.text.toString(),admin,section)
        ref.setValue(user)
            .addOnCanceledListener {
                Log.d("register","saved users database:")
            }
        login_activ()
    }
    private fun subscribeTO(sec:String){
        Log.d("register","Subscribing")
        var topic:String=""
        if (sec=="A"){ topic="bcta"}
        else if (sec=="B"){ topic="bctb"}
        else if (sec=="C"){ topic="bctc"}
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
            .addOnCompleteListener { task ->
                var msg = getString(R.string.msg_subscribed)
                if (!task.isSuccessful) {
                    msg = getString(R.string.msg_subscribed_failed)
                }
                else{Log.d("register","failed to subscribe")}
                Log.d("register", msg)
            }


    }
    private fun retryregister(){
        Toast.makeText(this,"Roll No Already Taken",Toast.LENGTH_SHORT).show()
       finish()
       startActivity(getIntent())
    }
}
