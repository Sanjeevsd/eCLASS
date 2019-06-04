package com.example.sanzu.eclass.Discussion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sanzu.eclass.R
import com.example.sanzu.eclass.Users
import com.example.sanzu.eclass.commentdetails
import com.example.sanzu.eclass.discussionmodal
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.comment_comment.*
import kotlinx.android.synthetic.main.comment_row.view.*
import java.text.SimpleDateFormat
import java.util.*

class commentActivity : AppCompatActivity() {
     val commentadapter=GroupAdapter<ViewHolder>()
    val commentRef=FirebaseDatabase.getInstance().getReference()
    override fun onCreate(savedInstanceState: Bundle?) {
        val commentRef=FirebaseDatabase.getInstance().getReference()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.comment_comment)
        val discussionDetails=intent.getParcelableExtra<discussionmodal>("Discussion_key")

        supportActionBar?.title=discussionDetails.discussiontitle
        recyclerview_comment.adapter=commentadapter
        comment_discussion_body.text=discussionDetails.discussioncontent
        comment_discussion_title.text=discussionDetails.discussiontitle
        comment_discussion_time.text=discussionDetails.posttime
        comment_discussion_name.text=discussionDetails.postername

        send_comments_button.setOnClickListener {
            publishComment(discussionDetails.discussionid)
        }
        val commentnmgr= LinearLayoutManager(this)
        recyclerview_comment.layoutManager=commentnmgr
        fetchcomments(discussionDetails.discussionid)
    }

    private fun fetchcomments(dID: String){
        val commentsfetchref=commentRef.child("Discussions/Comments/$dID")
        commentsfetchref.keepSynced(true)
        commentsfetchref.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val commentsByuser=p0.getValue(commentdetails::class.java)
                if(commentsByuser!=null)
                {
                    commentadapter.add(commentItem(commentsByuser))
                }

            }
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val commentsByuser=p0.getValue(commentdetails::class.java)
                if(commentsByuser!=null)
                {
                    commentadapter.add(commentItem(commentsByuser))
                }
            }



            override fun onChildRemoved(p0: DataSnapshot) {

            }
        })



    }
    class commentItem(val commentfetchClass: commentdetails):Item<ViewHolder>(){
        override fun getLayout(): Int {
            return R.layout.comment_row
        }
        override fun bind(viewHolder: ViewHolder, position: Int) {
            viewHolder.itemView.show_comment_name.text=commentfetchClass.commentername
            viewHolder.itemView.show_comment_description.text=commentfetchClass.commentbody
            viewHolder.itemView.show_time_comment.text=commentfetchClass.commenttime
        }


    }


    private  fun publishComment( d_ID:String){
        Log.d("discussion","the discussion id is$d_ID")

        val formatter = SimpleDateFormat("MMM dd yyyy HH:mma")
        val timeofCommentPost: String = formatter.format(Date()).toString()
        val commentData:String=send_commments.text.toString()
        val uid:String=FirebaseAuth.getInstance().uid.toString()
        val ref=commentRef.child("/user/$uid")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(Uname: DataSnapshot) {
                val nameofuser= Uname.getValue(Users::class.java)
                Log.d("hactivity","special case:${nameofuser?.names.toString()} and :${nameofuser?.uid.toString()}")
                val uname=nameofuser?.names.toString()
             val commentpublish= commentdetails(uname, commentData, timeofCommentPost)
                val commentpublishData= commentRef.child("Discussions/Comments/$d_ID").push()
                commentpublishData.setValue(commentpublish)
                    .addOnCompleteListener {
                        Log.d("comment","Succesfully posted comment")
                        Toast.makeText(this@commentActivity,"Successfully Commented",Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Log.d("comment","failed to post comment")
                    }

            }
        }
        )

        send_commments.text.clear()

    }
}
