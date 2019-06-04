package com.example.sanzu.eclass.notice_announcements

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import com.example.sanzu.eclass.R
import com.example.sanzu.eclass.noticeclass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_notice.*
import kotlinx.android.synthetic.main.notice_row.view.*

class NoticeActivity : AppCompatActivity() {
    val ref=FirebaseDatabase.getInstance().getReference()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)
        supportActionBar?.title="Notice"

        val noticemanager= androidx.recyclerview.widget.LinearLayoutManager(this)
        noticemanager.reverseLayout=true
        noticemanager.stackFromEnd=true
        recyclerview_notice.layoutManager=noticemanager//can use this statement in xml also
        fetchnotice()
    }
private fun fetchnotice(){
    val auth=FirebaseAuth.getInstance().uid
    val choosesection=ref.child("user/$auth/section")
    choosesection.addListenerForSingleValueEvent(object :ValueEventListener{
        override fun onCancelled(p0: DatabaseError) {
        }

        override fun onDataChange(p0: DataSnapshot) {

            val sectionofstudentis=p0.value.toString()
            Log.d("notice"," the section is:: $sectionofstudentis")
    val Nref=ref.child("/Notices/$sectionofstudentis/")
    Nref.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onCancelled(p0: DatabaseError) {
        }
        override fun onDataChange(p0: DataSnapshot) {
       val adapter=GroupAdapter<ViewHolder>()
            p0.children.forEach {
                val noticet=it.getValue(noticeclass::class.java)
                Log.d("notice","time is:${noticet?.timeofnotice}")
                if (noticet!=null)
                {
                    adapter.add(NoticeItem(noticet))
                }
            }
        recyclerview_notice.adapter=adapter
        }
    })  }

    })
}
}
class NoticeItem(val noticetitle: noticeclass):Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textView_notice_title.text=noticetitle.title
        viewHolder.itemView.textView_notice_body.text=noticetitle.body
        viewHolder.itemView.textView_notice_time.text=noticetitle.timeofnotice
        Log.d("notoce","title is:${noticetitle.title},\n body is:${noticetitle.body},\n time is:${noticetitle.timeofnotice}")
        Log.d("notice",noticetitle.urlimage)
        // viewHolder.itemView.imageview_notice.setImageDrawable(null)
        if (noticetitle.urlimage!=""){
            Picasso.get().load(noticetitle.urlimage).fit().into(viewHolder.itemView.imageview_notice)
        }
        else Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/eclass-cfe8b.appspot.com/o/images%2Flogo.png?alt=media&token=75612d8b-fd3f-493b-9b6c-b779f6594c44").fit().into(viewHolder.itemView.imageview_notice)
    }
    override fun getLayout(): Int {
        return R.layout.notice_row
    }
}
