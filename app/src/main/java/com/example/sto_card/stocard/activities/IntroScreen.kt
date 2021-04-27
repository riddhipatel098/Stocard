package com.example.sto_card.stocard.activities

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.example.sto_card.stocard.fragements.IntroSlideFragment
import com.example.sto_card.R


class IntroScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_screen)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, IntroSlideFragment())
        transaction.disallowAddToBackStack()
        transaction.commit()


}
//    override fun onSupportNavigateUp() =
//        findNavController(this, R.id.navHostFragment).navigateUp()
}