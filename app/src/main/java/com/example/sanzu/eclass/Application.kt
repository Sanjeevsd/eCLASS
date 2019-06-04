package com.example.sanzu.eclass

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class Application:Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}