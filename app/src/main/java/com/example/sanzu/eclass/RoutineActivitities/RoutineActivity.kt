package com.example.sanzu.eclass.RoutineActivitities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.FrameLayout
import android.widget.TextView
import com.example.sanzu.eclass.R
import com.example.sanzu.eclass.hactivity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_routine.*
import kotlinx.android.synthetic.main.app_bar_hactivity.*

 class RoutineActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
setContentView(R.layout.activity_routine)
        setContentView(R.layout.activity_routine)
        checkroutine()

    }
//check onclicklistener and open respective activity
fun checkroutine(){
    val sun :TextView=findViewById(R.id.tvsun)
    sun.setText("Sunday")
    //for sunday routine
    sun_card.setOnClickListener {
        val intent = Intent(this@RoutineActivity, routdetail::class.java)
        intent.putExtra("day","Sunday")
        startActivity(intent)

    }
    val mon :TextView=findViewById(R.id.tvmon)
    mon.setText("Monday")
    //for monday routine
    mon_card.setOnClickListener {

        val mond = Intent(this@RoutineActivity, routdetail::class.java)
        mond.putExtra("day","Monday")
        startActivity(mond)

    }
    val tue :TextView=findViewById(R.id.tvtue)
    tue.setText("Tuesday")
    //for tuesday routine
    tue_card.setOnClickListener {
        val tues = Intent(this@RoutineActivity, routdetail::class.java)
        tues.putExtra("day","Tuesday")
        startActivity(tues)

    }
    val wed :TextView=findViewById(R.id.tvwed)
    wed.setText("Wednesday")
    //for wednesday routine
    wed_card.setOnClickListener {
        val wedn = Intent(this@RoutineActivity, routdetail::class.java)
        wedn.putExtra("day","Wednesday")
        startActivity(wedn)
    }
    val thu :TextView=findViewById(R.id.tvthu)
    thu.setText("Thursday")
    //for thursday routine
    thu_card.setOnClickListener {
        val thurs = Intent(this@RoutineActivity, routdetail::class.java)
        thurs.putExtra("day","Thursday")
        startActivity(thurs)

    }
}
}

