package com.example.sanzu.eclass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_notes.*
import kotlinx.android.synthetic.main.activity_profile_setting.*

class profileSetting : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_setting)
        val nameofcurrentuser=intent.getStringExtra("setting").toString()
        val topics=intent.getStringExtra("topic").toString()
        Log.d("settingss","topics $topics")
        current_user_tv.text=nameofcurrentuser
        usertopics.text=topics
        send_topics.setOnClickListener {
           uploadTopic()

        }
        new_name_button.setOnClickListener {
            Log.d("settings","the sections is :$nameofcurrentuser")

            updateUsername()
        }

    }
    private fun updateUsername(){
            val newname=new_name_edittext.text.toString()
            val currentuserid=FirebaseAuth.getInstance().uid
            val updateref=FirebaseDatabase.getInstance().getReference("user/$currentuserid/names")
        updateref.setValue(newname)
        Log.d("settings","new name is :$newname")
        current_user_tv.text=newname
        new_name_edittext.text.clear()

    }
    private fun uploadTopic(){
        val topicInput=take_input_topics.text.toString()
        Log.d("settings","the body is:: $topicInput")
        val userId=FirebaseAuth.getInstance().uid
        val ref=FirebaseDatabase.getInstance().getReference("user/$userId/topic")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
              val previousTopic=p0.value.toString()

                Log.d("settings","the previous topics is: $previousTopic")
                if (previousTopic!="null")
                {
                    val newTopic= "$previousTopic $topicInput"
                 ref.setValue(newTopic)
                     .addOnSuccessListener {
                         Log.d("settings","Added topic")
                         Toast.makeText(this@profileSetting,"Added Topic",Toast.LENGTH_SHORT).show()
                     }
                }
                else
                {
                    ref.setValue(topicInput)
                        .addOnSuccessListener {
                            Log.d("settings","Added topic")
                            Toast.makeText(this@profileSetting,"Added Topic",Toast.LENGTH_SHORT).show()
                        }
                }
            }
        })

        take_input_topics.text.clear()
    }
}
