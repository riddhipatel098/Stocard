package com.example.sto_card.stocard.activities

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.sto_card.R
import com.example.sto_card.stocard.fragements.CardFragment
import com.example.sto_card.stocard.fragements.IntroSlideFragment
import com.example.sto_card.stocard.modals.SharedPrefManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging


class IntroScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_screen)


        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, IntroSlideFragment())
        transaction.disallowAddToBackStack()
        transaction.commit()


}
    override fun onStart() {
        super.onStart()
        if(SharedPrefManager.getInstance(this).isLoggedIn)
        {
            val intent = Intent(applicationContext, Home::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }
//    override fun onSupportNavigateUp() =
//        findNavController(this, R.id.navHostFragment).navigateUp()
}