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
import kotlinx.android.synthetic.main.activity_recommended_discussion.*
import kotlinx.android.synthetic.main.discussion_single_content.view.*
import java.lang.Math.sqrt

class RecommendedDiscussionActivity : AppCompatActivity() {
    val ref= FirebaseDatabase.getInstance().getReference()


    val dadapter= GroupAdapter<ViewHolder>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommended_discussion)

        supportActionBar?.title="Discussions"
        val topic=intent.getStringExtra("topic")?.toString()
        val discussionmgr= LinearLayoutManager(this)
        discussionmgr.reverseLayout=true
        discussionmgr.stackFromEnd=true
        recycler_recommendation.layoutManager=discussionmgr
        recycler_recommendation.adapter=dadapter

        dadapter.setOnItemClickListener { item, view ->
            val intent = Intent(view.context,commentActivity::class.java)
            val itemd= item as RNoticeItem
            intent.putExtra("Discussion_key",itemd.dnoticetitle)
            startActivity(intent)
        }
        Log.d("topichactivity","is :::$topic")
        fetchdiscussion(topic)

    }
    private fun fetchdiscussion(usertopics:String?){
        val auth= FirebaseAuth.getInstance().uid

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
                        var commentPassing=  p0.getValue(discussionmodal::class.java)
                        Log.d("discussion","trying to fetch database:${commentPassing?.discussiontitle}")
                        if (commentPassing!=null)
                        {
                            val recommendationvalue:Double=RecommendationCheck(commentPassing.discussioncontent,usertopics)
                            if (recommendationvalue>0.5){dadapter.add(RNoticeItem(commentPassing))}
                        }

                    }
                    override fun onCancelled(p0: DatabaseError) {}

                    override fun onChildMoved(p0: DataSnapshot, p1: String?) {}

                    override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                        val commentPass=  p0.getValue(discussionmodal::class.java)
                        Log.d("discussion","trying to fetch database:${commentPass?.discussiontitle}")
                        if (commentPass!=null)
                        {
                            val recommendationvalue:Double=RecommendationCheck(commentPass.discussioncontent,usertopics)
                            if (recommendationvalue>0.5){dadapter.add(RNoticeItem(commentPass))}

                        }
                    }
                    override fun onChildRemoved(p0: DataSnapshot) {}
                })
            }
        })
    }
    private fun RecommendationCheck(Discussioncontent:String?, usertopics: String?): Double {
        val userid=FirebaseAuth.getInstance().uid
        Log.d("recommendation","the passed topic are::$usertopics")
        val usertopics1=usertopics
        val Discussioncontent1=Discussioncontent
        val splittertopics= usertopics1!!.split(" ")
        val splitteddiscussion= Discussioncontent1!!.split(" ")
        val newdisct=splitteddiscussion.groupingBy { it }.eachCount().filter { it.value > 0 }
        val count: MutableList<Int> = mutableListOf<Int>()
        val count1: MutableList<Int> = mutableListOf<Int>()

        var index1=0
        for (values in newdisct) {

            val word2=values.key
            if (word2 in splittertopics)
            {
                count1.add(1)
                count.add(values.value)
                index1 += 1
            }
            else{
                count1.add(0)
                count.add(values.value)
                index1 += 1
            }

        }

        var u=0.0
        var d1=0.0
        var d2=0.0
        var similarity=0.0

        for (iterate in 0..index1-1 )
        {
            u += (count1[iterate] * count[iterate])
            d1 += (count1[iterate] * count1[iterate])
            d2 += (count[iterate] * count[iterate])
        }

        val D1=Math.sqrt(d1)
        val D2=Math.sqrt(d2)
        val D=D1*D2
        similarity=u/D

        Log.d("recommendation","the similarity is:$similarity")
        return similarity

    }

}

class RNoticeItem(val dnoticetitle: discussionmodal): Item<ViewHolder>(){
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