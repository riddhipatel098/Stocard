package com.example.sto_card.stocard.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sto_card.R
import com.example.sto_card.stocard.utils.Utils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging


class Splash : AppCompatActivity() {
    private val SPLASH_TIME_OUT = 3000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)





        //splash
        Handler().postDelayed(
                {
                    val i = Intent(this@Splash, IntroScreen::class.java)
                    startActivity(i)
                    finish()
                }, SPLASH_TIME_OUT)
    }
}