package com.example.sanzu.eclass.RoutineActivitities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.sanzu.eclass.R
import com.example.sanzu.eclass.User_details
import com.example.sanzu.eclass.Users
import com.example.sanzu.eclass.routinesModelClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_routdetail.*

class routdetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routdetail)
        val Uid=FirebaseAuth.getInstance().uid.toString()
        val uref=FirebaseDatabase.getInstance().getReference("/user/$Uid")
        Log.d("routine","the id is: $Uid")
        uref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                val sect =p0.getValue(Users::class.java)
                val section=sect?.section.toString()
                Log.d("routine"," the section of user is::$section")

        val dayselected = intent.getStringExtra("day")
        Log.d("routine","the sec is::$section")
        if (dayselected == "Sunday") {
            supportActionBar?.title=dayselected
            val sunrout= FirebaseDatabase.getInstance().getReference("/Routine/$section/$dayselected")
            sunrout.keepSynced(true)
                sunrout.addListenerForSingleValueEvent(object:ValueEventListener{
                    override fun onDataChange(p0: DataSnapshot) {
                        val routday=p0.getValue(routinesModelClass::class.java)
                            if (routday!=null)  {
                                //subject set
                                val sub1=routday.p1
                                val sub2=routday.p2
                                val sub3=routday.p3
                                val sub4=routday.p4
                                val sub5=routday.p5
                                val sub6=routday.p6
                                val sub7=routday.p7
                                tvsp1.text=sub1
                                tvsp2.text=sub2
                                tvsp3.text=sub3
                                tvsp4.text=sub4
                                tvsp5.text=sub5
                                tvsp6.text=sub6
                                tvsp7.text=sub7
                                //now time set
                                val time1s="Start\n" + routday.time1s
                                val time1e="End\n" + routday.time1e
                                val time2s=routday.time2s
                                val time2e=routday.time2e
                                val time3s=routday.time3s
                                val time3e=routday.time3e
                                val time4s=routday.time4s
                                val time4e=routday.time4e
                                val time5s=routday.time5s
                                val time5e=routday.time5e
                                val time6s=routday.time6s
                                val time6e=routday.time6e
                                val time7s=routday.time7s
                                val time7e=routday.time7e
                                    timev1s.text=time1s
                                    timev1e.text=time1e
                                timev2s.text=time2s
                                timev2e.text=time2e
                                timev3s.text=time3s
                                timev3e.text=time3e
                                timev4s.text=time4s
                                timev4e.text=time4e
                                timev5s.text=time5s
                                timev5e.text=time5e
                                timev6s.text=time6s
                                timev6e.text=time6e
                                timev7s.text=time7s
                                timev7e.text=time7e
                                }
                           Log.d("Routdetail","Succesfull entry sunday routine")
                    }
                    override fun onCancelled(p0: DatabaseError) {
                    }
                })

        } else if (dayselected == "Monday") {
            supportActionBar?.title=dayselected
            val sunrout= FirebaseDatabase.getInstance().getReference("/Routine/$section/$dayselected")

            sunrout.keepSynced(true)
            sunrout.addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {

                        val routday=p0.getValue(routinesModelClass::class.java)
                        if (routday!=null)  {
                            //subject set
                            val sub1=routday.p1
                            val sub2=routday.p2
                            val sub3=routday.p3
                            val sub4=routday.p4
                            val sub5=routday.p5
                            val sub6=routday.p6
                            val sub7=routday.p7
                            tvsp1.text=sub1
                            tvsp2.text=sub2
                            tvsp3.text=sub3
                            tvsp4.text=sub4
                            tvsp5.text=sub5
                            tvsp6.text=sub6
                            tvsp7.text=sub7
                            //now time set
                            val time1s="Start\n" + routday.time1s
                            val time1e="End\n" + routday.time1e
                            val time2s=routday.time2s
                            val time2e=routday.time2e
                            val time3s=routday.time3s
                            val time3e=routday.time3e
                            val time4s=routday.time4s
                            val time4e=routday.time4e
                            val time5s=routday.time5s
                            val time5e=routday.time5e
                            val time6s=routday.time6s
                            val time6e=routday.time6e
                            val time7s=routday.time7s
                            val time7e=routday.time7e
                            timev1s.text=time1s
                            timev1e.text=time1e
                            timev2s.text=time2s
                            timev2e.text=time2e
                            timev3s.text=time3s
                            timev3e.text=time3e
                            timev4s.text=time4s
                            timev4e.text=time4e
                            timev5s.text=time5s
                            timev5e.text=time5e
                            timev6s.text=time6s
                            timev6e.text=time6e
                            timev7s.text=time7s
                            timev7e.text=time7e
                        }
                    Log.d("Routdetail","Succesfull entry sunday routine")
                }
                override fun onCancelled(p0: DatabaseError) {
                }
            })

        } else if (dayselected == "Tuesday") {
            supportActionBar?.title=dayselected
            val sunrout= FirebaseDatabase.getInstance().getReference("/Routine/$section/$dayselected")
            sunrout.keepSynced(true)
            sunrout.addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {

                        val routday=p0.getValue(routinesModelClass::class.java)
                        if (routday!=null)  {
                            //subject set
                            val sub1=routday.p1
                            val sub2=routday.p2
                            val sub3=routday.p3
                            val sub4=routday.p4
                            val sub5=routday.p5
                            val sub6=routday.p6
                            val sub7=routday.p7
                            tvsp1.text=sub1
                            tvsp2.text=sub2
                            tvsp3.text=sub3
                            tvsp4.text=sub4
                            tvsp5.text=sub5
                            tvsp6.text=sub6
                            tvsp7.text=sub7
                            //now time set
                            val time1s="Start\n" + routday.time1s
                            val time1e="End\n" + routday.time1e
                            val time2s=routday.time2s
                            val time2e=routday.time2e
                            val time3s=routday.time3s
                            val time3e=routday.time3e
                            val time4s=routday.time4s
                            val time4e=routday.time4e
                            val time5s=routday.time5s
                            val time5e=routday.time5e
                            val time6s=routday.time6s
                            val time6e=routday.time6e
                            val time7s=routday.time7s
                            val time7e=routday.time7e
                            timev1s.text=time1s
                            timev1e.text=time1e
                            timev2s.text=time2s
                            timev2e.text=time2e
                            timev3s.text=time3s
                            timev3e.text=time3e
                            timev4s.text=time4s
                            timev4e.text=time4e
                            timev5s.text=time5s
                            timev5e.text=time5e
                            timev6s.text=time6s
                            timev6e.text=time6e
                            timev7s.text=time7s
                            timev7e.text=time7e
                        }
                    Log.d("Routdetail","Succesfull entry sunday routine")
                }
                override fun onCancelled(p0: DatabaseError) {
                }
            })

        } else if (dayselected == "Wednesday") {
            supportActionBar?.title=dayselected
            val sunrout= FirebaseDatabase.getInstance().getReference("/Routine/$section/$dayselected")
            sunrout.keepSynced(true)
            sunrout.addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {

                        val routday=p0.getValue(routinesModelClass::class.java)
                        if (routday!=null)  {
                            //subject set
                            val sub1=routday.p1
                            val sub2=routday.p2
                            val sub3=routday.p3
                            val sub4=routday.p4
                            val sub5=routday.p5
                            val sub6=routday.p6
                            val sub7=routday.p7
                            tvsp1.text=sub1
                            tvsp2.text=sub2
                            tvsp3.text=sub3
                            tvsp4.text=sub4
                            tvsp5.text=sub5
                            tvsp6.text=sub6
                            tvsp7.text=sub7
                            //now time set
                            val time1s="Start\n" + routday.time1s
                            val time1e="End\n" + routday.time1e
                            val time2s=routday.time2s
                            val time2e=routday.time2e
                            val time3s=routday.time3s
                            val time3e=routday.time3e
                            val time4s=routday.time4s
                            val time4e=routday.time4e
                            val time5s=routday.time5s
                            val time5e=routday.time5e
                            val time6s=routday.time6s
                            val time6e=routday.time6e
                            val time7s=routday.time7s
                            val time7e=routday.time7e
                            timev1s.text=time1s
                            timev1e.text=time1e
                            timev2s.text=time2s
                            timev2e.text=time2e
                            timev3s.text=time3s
                            timev3e.text=time3e
                            timev4s.text=time4s
                            timev4e.text=time4e
                            timev5s.text=time5s
                            timev5e.text=time5e
                            timev6s.text=time6s
                            timev6e.text=time6e
                            timev7s.text=time7s
                            timev7e.text=time7e
                        }
                    Log.d("Routdetail","Succesfull entry sunday routine")
                }
                override fun onCancelled(p0: DatabaseError) {
                }
            })

        } else if (dayselected == "Thursday") {
            supportActionBar?.title=dayselected
            val sunrout= FirebaseDatabase.getInstance().getReference("/Routine/$section/$dayselected")
            sunrout.keepSynced(true)
            sunrout.addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {

                        val routday=p0.getValue(routinesModelClass::class.java)
                        if (routday!=null)  {
                            //subject set
                            val sub1=routday.p1
                            val sub2=routday.p2
                            val sub3=routday.p3
                            val sub4=routday.p4
                            val sub5=routday.p5
                            val sub6=routday.p6
                            val sub7=routday.p7
                            tvsp1.text=sub1
                            tvsp2.text=sub2
                            tvsp3.text=sub3
                            tvsp4.text=sub4
                            tvsp5.text=sub5
                            tvsp6.text=sub6
                            tvsp7.text=sub7
                            //now time set
                            val time1s="Start\n" + routday.time1s
                            val time1e="End\n" + routday.time1e
                            val time2s=routday.time2s
                            val time2e=routday.time2e
                            val time3s=routday.time3s
                            val time3e=routday.time3e
                            val time4s=routday.time4s
                            val time4e=routday.time4e
                            val time5s=routday.time5s
                            val time5e=routday.time5e
                            val time6s=routday.time6s
                            val time6e=routday.time6e
                            val time7s=routday.time7s
                            val time7e=routday.time7e
                            timev1s.text=time1s
                            timev1e.text=time1e
                            timev2s.text=time2s
                            timev2e.text=time2e
                            timev3s.text=time3s
                            timev3e.text=time3e
                            timev4s.text=time4s
                            timev4e.text=time4e
                            timev5s.text=time5s
                            timev5e.text=time5e
                            timev6s.text=time6s
                            timev6e.text=time6e
                            timev7s.text=time7s
                            timev7e.text=time7e
                        }
                    Log.d("Routdetail","Succesfull entry sunday routine")
                }
                override fun onCancelled(p0: DatabaseError) {
                }
            })

        }

            }

        }) }
}