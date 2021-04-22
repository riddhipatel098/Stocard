package com.example.sto_card.Activities.Fragements

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.sto_card.Activities.Activities.Home
import com.example.sto_card.R

class LoginFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View= inflater.inflate(R.layout.fragment_login, container, false)
        val signin=view.findViewById<Button>(R.id.Sign_In)
        signin.setOnClickListener(){
            requireActivity().run{
                startActivity(Intent(this, Home::class.java))
                finish()
            }
        }
        return view
    }


}