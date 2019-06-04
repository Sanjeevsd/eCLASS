package com.example.sanzu.eclass.Discussion

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.sanzu.eclass.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_discussion_post.*
import kotlinx.android.synthetic.main.nav_header_hactivity.*
import java.text.SimpleDateFormat
import java.util.*

class DiscussionPostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discussion_post)
        supportActionBar?.title="Post Discussion"

        discussion_post_button.setOnClickListener {
            postDiscussion()
        }

    }
    @SuppressLint("SimpleDateFormat")
    private fun postDiscussion(){
       val postContent:String=discussion_edittext.text.toString()
        val discussionTitle:String=Discussion_title.text.toString()
        val formatter = SimpleDateFormat("MMM dd yyyy HH:mma")
        val timeofDiscussionPosst: String = formatter.format(Date()).toString()
        if(postContent.isEmpty()){
            Toast.makeText(this,"Empty Content",Toast.LENGTH_SHORT).show()
            return
        }
        val uid=FirebaseAuth.getInstance().uid
        val ref=FirebaseDatabase.getInstance().getReference().child("/user/$uid")


        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(Uname: DataSnapshot) {

                val detailsofPoster= Uname.getValue(Users::class.java)
                Log.d("Discussion","special case:${detailsofPoster?.names.toString()} and :${detailsofPoster?.section.toString()}")
                val PosterName:String=detailsofPoster?.names.toString()
                val postersection:String=detailsofPoster?.section.toString()
                val DiscussionRef=FirebaseDatabase.getInstance().getReference("/Discussions/$postersection").push()
                Log.d("discussion","the kei is :${DiscussionRef.key}")
                val discussionpostt= discussionmodal(discussionTitle,postContent,PosterName,timeofDiscussionPosst,DiscussionRef.key.toString())


                DiscussionRef.setValue(discussionpostt)
                    .addOnCompleteListener {
                        Log.d("discussion", "Successfully published Discussion")
                    }
            }
        }
        )
            discussion_edittext.getText().clear()
            Discussion_title.getText().clear()

    }

}
