package com.example.sanzu.eclass.AdmiinChanges
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.util.Log
import com.example.sanzu.eclass.R
import com.example.sanzu.eclass.hactivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class MyFirebaseMessagingService : FirebaseMessagingService() {
    var CHANNEL_ID="ECLASS"
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
   val TAG="FCM"
        if (remoteMessage!!.notification != null) {
            Log.e(TAG, "Title: " + remoteMessage.notification?.title!!)
            Log.e(TAG, "Body: " + remoteMessage.notification?.body!!)
            notifyforothers(remoteMessage.notification?.title!!,remoteMessage.notification?.body!!)
        }
        if (remoteMessage.data.isNotEmpty()) {
            Log.e(TAG, "Data: " + remoteMessage.data)
        }

    }

    private  fun notifyforothers(title:String, Body:String){
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
        val notificationId = 10
        lateinit var channel:NotificationChannel
        val notificationManager1 = getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val description = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
            notificationManager1.createNotificationChannel(channel)
        }
        val mBuilder = NotificationCompat.Builder(this,CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setColor(Color.RED)
            .setContentText(Body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
        notificationManager1.notify(notificationId, mBuilder.build())
    }
        }
