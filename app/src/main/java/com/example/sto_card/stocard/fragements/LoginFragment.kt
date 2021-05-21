package com.example.sto_card.stocard.fragements


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.sto_card.R
import com.example.sto_card.stocard.Response.DefaultResponse
import com.example.sto_card.stocard.activities.Home
import com.example.sto_card.stocard.base.BaseFragment
import com.example.sto_card.stocard.modals.SharedPrefManager
import com.example.sto_card.stocard.network.Api
import com.example.sto_card.stocard.network.ApiUtils
import com.example.sto_card.stocard.network.RetrofitClient
import com.example.sto_card.stocard.utils.Utils
import com.example.sto_card.stocard.utils.ValidationUtils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.fragment_login.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback


class LoginFragment : BaseFragment() {
    //variable declartion
    lateinit var email:String
    lateinit var password:String
    var F_token:String?=null

    val SHARED_PREF_NAME = "my_shared_preff"
    val sharedPreference =  context?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    val token = "Bearer " + sharedPreference?.getString("token", "defaultName")
    val tkn = FirebaseInstanceId.getInstance().token

    //layout set
    override fun setContentView(): Int=R.layout.fragment_login

    //initialization
    override fun initView(rootView: View?, savedInstanceState: Bundle?) {
        Log.d("FCM_Token///",tkn.toString())



    }

    override fun setListeners() {
        Sign_In.setOnClickListener(this)
        Forgot_Password.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.Forgot_Password->
            {
               LoadFragment(ForgotPasswordFragment())
            }
            R.id.Sign_In ->{

                Log.d("FCM_Token////",tkn.toString())

                var mAPIService: Api? = null
                mAPIService = ApiUtils.apiService


                email = L_Email.text.toString().trim()
                password = L_Password.text.toString().trim()
          //      Log.d("token........", F_token.toString())
                if (isCheckValidation()) {
                    if (email != null) {




                        val map: MutableMap<String, RequestBody> = HashMap()
                        map["email"] = toPart(email) as RequestBody
                        map["password"] = toPart(password)
                        map["device_id"] = toPart(tkn.toString())

                        mAPIService!!.loginUser(token!!, "login", map).enqueue(object :
                            Callback<DefaultResponse> {
                            override fun onResponse(
                                call: Call<DefaultResponse>,
                                response: retrofit2.Response<DefaultResponse>
                            ) {
                                try {

                                    // Toast.makeText(context, response.body()?.data!!.name, Toast.LENGTH_LONG).show()
                                    if (response.body()?.success == true) {
                                        Toast.makeText(
                                            context,
                                            response.body()?.message,
                                            Toast.LENGTH_LONG
                                        )
                                            .show()
                                        SharedPrefManager.getInstance(context)
                                            .saveUser(response.body()?.data!!)
                                        requireActivity().run {
                                            val i = (Intent(this, Home::class.java))
                                            i.putExtra("Username", response.body()?.data!!.name)
                                            startActivity(i)
                                        }
                                    } else {

                                        Toast.makeText(context, "Invalid USer", Toast.LENGTH_LONG)
                                            .show()
                                    }
                                } catch (e: Exception) {
                                    Log.d("error", response.toString())
                                }
                            }

                            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                            }

                        })
                    }
                }

            }
            }


        }

    fun toPart(data: String): RequestBody {
        return RequestBody.create("text/plain".toMediaTypeOrNull(), data)
    }
    override fun onStart() {
        super.onStart()
        if(SharedPrefManager.getInstance(context).isLoggedIn) {
            requireActivity().run {
                val i = (Intent(this, Home::class.java))
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            }
        }
    }
    fun isCheckValidation(): Boolean {
        if (L_Email.text.toString().isEmpty()) {
            Utils().showToast(activity, "Please enter email")
            return false
        }
        if (!ValidationUtils.isValidEmailAddress(L_Email.text.toString())) {
            Utils().showToast(activity, "Please enter valid email")
            return false
        }
        if (L_Password.text.toString().isEmpty()) {
            Utils().showToast(activity, "Please enter password")
            return false
        }
        return true
    }
    fun LoadFragment(fragment: Fragment)
    {
        val args = Bundle()
        val fragmentManager: FragmentManager? = fragmentManager
        fragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, fragment)
            ?.commit()
    }
}