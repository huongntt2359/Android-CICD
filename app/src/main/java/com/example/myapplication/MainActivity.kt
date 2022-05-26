package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private var textTitle: AppCompatTextView? = null

    private val messageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val textNotification = intent?.extras?.getString("message")
            textTitle?.text = textNotification
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textTitle = findViewById<AppCompatTextView>(R.id.title)

        val bundle = intent.extras
        if (bundle != null) textTitle?.text = bundle.getString("text")

        val button = findViewById<AppCompatButton>(R.id.btn_retrieve_token)
        button.setOnClickListener {
            if (checkGooglePlayServices()) {
                FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.e(
                            "FirebaseMessage: ",
                            "Fetching FCM registration token failed",
                            task.exception
                        )
                    }

                    val token = task.result
                    Log.e("FirebaseMessage: ", token)
                })
            } else {
                Log.e("FirebaseMessage: ", "Device doesn't have google play services")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(messageReceiver, IntentFilter("MyData"))
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver)
    }

    private fun checkGooglePlayServices(): Boolean {
//        val status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
//        return if (status != ConnectionResult.SUCCESS) {
//            Log.e("FirebaseMessage", "Error")
//            GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this)
//            false
//        } else {
//            Log.e("FirebaseMessage", "Google play service updated")
//            true
//        }
        return true
    }


}