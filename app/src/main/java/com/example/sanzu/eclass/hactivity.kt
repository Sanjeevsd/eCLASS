package com.example.sanzu.eclass

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import com.example.sanzu.eclass.AdmiinChanges.AdminActivity
import com.example.sanzu.eclass.Discussion.DiscussionActivity
import com.example.sanzu.eclass.Discussion.DiscussionPostActivity
import com.example.sanzu.eclass.LoginFIles.Login_Activity
import com.example.sanzu.eclass.Notes.NotesActivity

import com.example.sanzu.eclass.RoutineActivitities.RoutineActivity
import com.example.sanzu.eclass.notice_announcements.NoticeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_hactivity.*
import kotlinx.android.synthetic.main.app_bar_hactivity.*
import kotlinx.android.synthetic.main.content_hactivity.*
import kotlinx.android.synthetic.main.nav_header_hactivity.*
import kotlinx.android.synthetic.main.notice_row.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.util.*

class hactivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    val uid=FirebaseAuth.getInstance().uid.toString()

    val databaseref=FirebaseDatabase.getInstance().reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_hactivity)
        setSupportActionBar(toolbar)
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
        tv_body_notice_home.movementMethod=ScrollingMovementMethod()
        selectname()

        cardview_home_notice.setOnClickListener {
            opennotice()
        }
        cardview_routine_home.setOnClickListener {
            openRoutine()
        }


        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            drawer_layout.closeDrawer(GravityCompat.START)
            super.onBackPressed()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.hactivity, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks (logout)

        when (item.itemId) {
            R.id.action_logout -> {
              FirebaseAuth.getInstance().signOut()
                val logout = Intent(this,Login_Activity::class.java)
                startActivity(logout)
                finish()
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_routine -> {
                // Handle the Routine Action
                val inten = Intent(this, RoutineActivity::class.java)
                startActivity(inten)
                drawer_layout.closeDrawers()
            }
            R.id.nav_home ->{
                // handles Homepage operation
                checkAdminautho()
                drawer_layout.closeDrawers()
            }
            R.id.nav_notice ->{
                val noticestart=Intent(this,NoticeActivity::class.java)
                startActivity(noticestart)
                drawer_layout.closeDrawers()
            }
            R.id.nav_assignment ->{
                val assignmentstart=Intent(this,AssignmentActivity::class.java)
                startActivity(assignmentstart)
                drawer_layout.closeDrawers()
            }
            R.id.nav_notes ->{
                val notesstart=Intent(this,NotesActivity::class.java)
                startActivity(notesstart)
                drawer_layout.closeDrawers()
            }
            R.id.nav_Discussion->{
                val discussionactivity=Intent(this, DiscussionActivity::class.java)
                startActivity(discussionactivity)
                drawer_layout.closeDrawers()
            }
            R.id.nav_DiscussionPost->{
                val discussionactivity=Intent(this, DiscussionPostActivity::class.java)
                startActivity(discussionactivity)
                drawer_layout.closeDrawers()
            }

        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

private fun checkAdminautho(){
    val adminref=databaseref.child("/user/$uid/adminautho")
    adminref.keepSynced(true)
    adminref.addListenerForSingleValueEvent(object: ValueEventListener {

        override fun onDataChange(Admindata: DataSnapshot) {
            val getAdminauth=Admindata.value.toString()
            Log.d("hactivity","adminauth:${getAdminauth}")
            if (getAdminauth=="true"){
                Log.d("hactivity","Opening admin drawer")
                val adintent=Intent(this@hactivity,AdminActivity::class.java)
                startActivity(adintent)
            }
            else {
                Log.d("hactivity", "NO admin")
                Toast.makeText(this@hactivity,"Only For Admin",Toast.LENGTH_LONG).show()
            }
        }
        override fun onCancelled(p0: DatabaseError) {
        }
    } )
}
     fun selectname(){
        val ref=databaseref.child("/user/$uid")
       ref.keepSynced(true)
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(Uname: DataSnapshot) {
              val nameofuser= Uname.getValue(Users::class.java)
                Log.d("hactivity","special case:${nameofuser?.names.toString()} and :${nameofuser?.uid.toString()}")
              val uname=nameofuser?.names.toString()
                navigation_name?.text = uname
                val rollno:String=nameofuser?.roll.toString()
                roll_no_navigation.text = rollno
            }
        }
        )
        recentnoticehome()
  }
private fun recentnoticehome(){
    val sectionchoose=databaseref.child("/user/$uid/section")
    sectionchoose.keepSynced(true)
    sectionchoose.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onCancelled(p0: DatabaseError) {
        }

        override fun onDataChange(p0: DataSnapshot) {
            val sectionIs=p0.value.toString()

    val lastQuery:Query = databaseref.child("/Notices/$sectionIs").orderByKey().limitToLast(1)
    lastQuery.keepSynced(true)
    lastQuery.addListenerForSingleValueEvent(object :ValueEventListener{
        override fun onCancelled(p0: DatabaseError) {
        }
        override fun onDataChange(noticesnapshot: DataSnapshot) {
            noticesnapshot.children.forEach{
            val bodyofnoice=it.child("/body").value.toString()
            val titleofnotice=it.child("/title").value.toString()
            val timeofposting=it.child("/timeofnotice").value.toString()
                val pname=it.child("/postername").value.toString()
            Log.d("hactivity","the details are: ${titleofnotice} and ${bodyofnoice} also ${timeofposting}")
                tv_body_notice_home.text = bodyofnoice
                tv_notice_uname_home.text=pname
                tv_date_notice_home.text = timeofposting
               tv_notice_home_title.text=titleofnotice
            }}
    })
        }
    })
    todayroutine()
}
   private  fun todayroutine(){

        val formatter= SimpleDateFormat("EEEE")
        val aday:String=formatter.format(Date()).toString()
        Log.d("hactivity","day is $aday")
       var day:String=""
       if (aday!="Friday" && aday!="Saturday"){
           day=aday
       }
       else{day="Thursday"}
       val selectsection=databaseref.child("/user/$uid/section")
       selectsection.keepSynced(true)
       selectsection.addListenerForSingleValueEvent(object : ValueEventListener {
           override fun onCancelled(p0: DatabaseError) {
           }

           override fun onDataChange(p0: DataSnapshot) {
            val sectionIs=p0.value.toString()
               Log.d("hactivity","the current section is::::$sectionIs")

       val routineref=databaseref.child("/Routine/$sectionIs/$day")
               routineref.keepSynced(true)
       routineref.addListenerForSingleValueEvent(object : ValueEventListener {
           override fun onCancelled(p0: DatabaseError) {
           }
           override fun onDataChange(routinesnapshot: DataSnapshot) {
                   val routine=routinesnapshot.getValue(routinesModelClass::class.java)
                   if (routine!=null){
                   routine_home_title.text="$day Routine"
                   val time1 =routine.time1e+"-"+routine.time1s
                       Log.d("hactivity","")
                       val time2=routine.time2s+"-"+routine.time2e
                       val time3=routine.time3s+"-"+routine.time3s
                       val time4=routine.time4s+"-"+routine.time4s
                       val time5=routine.time5s+"-"+routine.time5s
                       val time6=routine.time6s+"-"+routine.time6s
                       val time7=routine.time7s+"-"+routine.time7s
                           routine_table_time1.text=time1.toString()
                       routine_table_time2.text =time2.toString()
                       routine_table_time3.text=time3.toString()
                       routine_table_time4.text=time4.toString()
                       routine_table_time5.text=time5.toString()
                       routine_table_time6.text=time6.toString()
                       if (day=="Sunday"){routine_table_time7.text=time7.toString()}
                       routine_table_time7.text=""
                       rroutine_table_subect1.text=routine.p1.toString()
                       Log.d("hactivity","the subject is:${routine.p1.toString()}")
                       rroutine_table_subect2.text=routine.p2.toString()
                       rroutine_table_subect3.text=routine.p3.toString()
                       rroutine_table_subect4.text=routine.p4.toString()
                       rroutine_table_subect5.text=routine.p5.toString()
                       rroutine_table_subect6.text=routine.p6.toString()
                       if (day=="Sunday") {
                           rroutine_table_subect7.text = routine.p7.toString()
                       }
                       rroutine_table_subect7.text = ""
                   }
               }

       })
           }
       })
   }


    override fun onStart() {
        super.onStart()
        recentnoticehome()

    }

    private fun opennotice(){
    val noticeintent=Intent(this,NoticeActivity::class.java)
    startActivity(noticeintent)
    }
    private fun openRoutine(){
    val routineintent=Intent(this,RoutineActivity::class.java)
        startActivity(routineintent)
    }
}
