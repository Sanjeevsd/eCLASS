package com.example.sanzu.eclass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import com.example.sanzu.eclass.notice_announcements.NoticeItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_assignment.*
import kotlinx.android.synthetic.main.activity_notice.*
import kotlinx.android.synthetic.main.assignment_row.view.*
import kotlinx.android.synthetic.main.notice_row.view.*

class AssignmentActivity : AppCompatActivity() {
  val ref=FirebaseDatabase.getInstance().getReference()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assignment)
        supportActionBar?.title="Assignment"
        val assignmentmgr= androidx.recyclerview.widget.LinearLayoutManager(this)
        assignment_rv.layoutManager=assignmentmgr
        fetchassignment()

    }
    private fun fetchassignment(){
       val uid=FirebaseAuth.getInstance().uid
        val sectionselect=ref.child("/user/$uid/section")
        sectionselect.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
            val section=p0.value.toString()
                Log.d("assignment","the section is::$section")

        val Aref= ref.child("/Assignments/$section")
        Aref.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(AssignData: DataSnapshot) {
                val adapter= GroupAdapter<ViewHolder>()
                AssignData.children.forEach {
                    val Assignmentdb=it.getValue(assignmentModal::class.java)
                    if (Assignmentdb!=null)
                    {
                        adapter.add(AssignItem(Assignmentdb))
                    }
                }
                assignment_rv.adapter=adapter
            }
        })
            }
        })
    }
}
class AssignItem(val assignvalue:assignmentModal): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.assignment_title.text=assignvalue.title
        viewHolder.itemView.assignment_body.text=assignvalue.body
        Log.d("notoce","title is:${assignvalue.title},\n body is:${assignvalue.body},\n time is:${assignvalue.dateofsubmission}")



    }
    override fun getLayout(): Int {
        return R.layout.assignment_row
    }
}
