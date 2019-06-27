package com.example.sanzu.eclass.Discussion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sanzu.eclass.R
import com.example.sanzu.eclass.discussionmodal
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.discussion_activity.*
import kotlinx.android.synthetic.main.discussion_single_content.view.*

class DiscussionActivity : AppCompatActivity() {
    val ref=FirebaseDatabase.getInstance().getReference()
    var commentPassing:discussionmodal?=null
    val dadapter=GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.discussion_activity)
        supportActionBar?.title="Discussions"
        val discussionmgr=LinearLayoutManager(this)
        discussionmgr.reverseLayout=true
        discussionmgr.stackFromEnd=true
        recycler_discussion.layoutManager=discussionmgr
        recycler_discussion.adapter=dadapter

        dadapter.setOnItemClickListener { item, view ->
            val intent = Intent(view.context,commentActivity::class.java)
            val itemd= item as dNoticeItem
            intent.putExtra("Discussion_key",itemd.dnoticetitle)
            startActivity(intent)
        }

        fetchdiscussion()
    }
    private fun fetchdiscussion(){
        val auth=FirebaseAuth.getInstance().uid

        val choosesection=ref.child("user/$auth/section")
        choosesection.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(p0: DataSnapshot) {
             val sectionofuser=p0.getValue().toString()
                Log.d("discussion","The section is $sectionofuser")
                val discussiondataerf=ref.child("Discussions/$sectionofuser")
                discussiondataerf.addChildEventListener(object : ChildEventListener {
                    override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                        commentPassing=  p0.getValue(discussionmodal::class.java)
                        Log.d("discussion","trying to fetch database:${commentPassing?.discussiontitle}")
                        if (commentPassing!=null)
                        {
                            dadapter.add(dNoticeItem(commentPassing!!))
                        }

                    }
                    override fun onCancelled(p0: DatabaseError) {}

                    override fun onChildMoved(p0: DataSnapshot, p1: String?) {}

                    override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                       val commentPass=  p0.getValue(discussionmodal::class.java)
                        Log.d("discussion","trying to fetch database:${commentPass?.discussiontitle}")
                        if (commentPass!=null)
                        {
                            dadapter.add(dNoticeItem(commentPass))
                        }
                    }
                    override fun onChildRemoved(p0: DataSnapshot) {}
                })
            }
        })
    }
}
class dNoticeItem(val dnoticetitle: discussionmodal):Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.discussion_title_show.text=dnoticetitle.discussiontitle
        viewHolder.itemView.discussion_content_show.text=dnoticetitle.discussioncontent
        viewHolder.itemView.discussion_time_show.text=dnoticetitle.posttime
        viewHolder.itemView.discussion_name.text=dnoticetitle.postername
        Log.d("discussion","title is:${dnoticetitle.discussiontitle},\n body is:${dnoticetitle.discussioncontent},\n time is:${dnoticetitle.posttime}")
    }
    override fun getLayout(): Int {
        return R.layout.discussion_single_content
    }
}
