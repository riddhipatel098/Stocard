package com.example.sto_card.stocard.fragements

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.sto_card.R
import com.example.sto_card.stocard.utils.Utils
import com.google.firebase.messaging.FirebaseMessaging


class IntroSlideFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_intro_slide, container, false)


//
//        //firebase
//        FirebaseMessaging.getInstance().subscribeToTopic("weather")
//            .addOnCompleteListener { task ->
//                var msg = "Done"
//                if (!task.isSuccessful) {
//                    msg = "Failed"
//                }
//                Log.d("notification", msg!!)
//              Utils().showToast(context,msg)
//            }





        val signup = view.findViewById<Button>(R.id.Sign_up_btn)
        val signin = view.findViewById<Button>(R.id.Sign_in_btn)
      //  val nav = Navigation.findNavController(view)
        signup.setOnClickListener {
            val fragment: Fragment = RegistrationFragment()
            val fragmentManager: FragmentManager? = fragmentManager
            fragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, fragment)
                ?.commit()
        }
        signin.setOnClickListener {
            val fragment: Fragment = LoginFragment()
            val fragmentManager: FragmentManager? = fragmentManager
            fragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, fragment)
                ?.commit()
        }
        return view
    }




}