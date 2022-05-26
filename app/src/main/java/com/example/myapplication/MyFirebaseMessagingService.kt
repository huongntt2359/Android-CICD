package com.example.myapplication

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private var broadcaster: LocalBroadcastManager? = null

    override fun onCreate() {
        super.onCreate()
        broadcaster = LocalBroadcastManager.getInstance(this)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.e("FBMessagingService", token)

        //sendRegistrationToServer(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        handleMessage(remoteMessage)
    }

    private fun handleMessage(remoteMessage: RemoteMessage) {
        val handler = Handler(Looper.getMainLooper())

        handler.post(Runnable {
            remoteMessage.notification?.let {
                //Toast.makeText(baseContext, "Notification: ${it.body}", Toast.LENGTH_LONG).show()
                val intent = Intent("MyData")
                intent.putExtra("message", it.body)
                broadcaster?.sendBroadcast(intent)
            }
        })

    }

}