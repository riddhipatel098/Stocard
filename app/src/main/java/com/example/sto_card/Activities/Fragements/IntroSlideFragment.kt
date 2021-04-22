package com.example.sto_card.Activities.Fragements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import com.example.sto_card.R


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
        val signup = view.findViewById<Button>(R.id.Sign_up_btn)
        val signin = view.findViewById<Button>(R.id.Sign_in_btn)
      //  val nav = Navigation.findNavController(view)
        signup.setOnClickListener {
            val fragment: Fragment = LoginFragment()
            val fragmentManager: FragmentManager? = fragmentManager
            fragmentManager?.beginTransaction()
                ?.replace(R.id.FrameIntro, fragment)
                ?.commit()
        }
        signin.setOnClickListener {
            val fragment: Fragment = RegistrationFragment()
            val fragmentManager: FragmentManager? = fragmentManager
            fragmentManager?.beginTransaction()
                ?.replace(R.id.FrameIntro, fragment)
                ?.commit()
        }
        return view
    }




}