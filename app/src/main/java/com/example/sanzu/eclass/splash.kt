package com.example.sanzu.eclass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.example.sanzu.eclass.LoginFIles.Login_Activity

class splash : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val bottomanim=AnimationUtils.loadAnimation(this,R.anim.frombottom)
        val fadeanim=AnimationUtils.loadAnimation(this,R.anim.from_top)

        val logo:ImageView =findViewById(R.id.logo_view)
        val nameeclass:ImageView=findViewById(R.id.name_view)
        val quote:ImageView=findViewById(R.id.quote_view)
nameeclass.startAnimation(bottomanim)
        quote.startAnimation(bottomanim)
        logo.startAnimation(fadeanim)


Handler().postDelayed({  val intent = Intent(this@splash, Login_Activity::class.java)
    intent.putExtra("key", "Kotlin")
    startActivity(intent)},1500)
    }
}
