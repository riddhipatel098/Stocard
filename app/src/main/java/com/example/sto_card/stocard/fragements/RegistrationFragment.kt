package com.example.sto_card.stocard.fragements



import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.sto_card.R
import com.example.sto_card.stocard.Response.DefaultResponse
import com.example.sto_card.stocard.modals.URIPathHelper
import com.example.sto_card.stocard.network.Api
import com.example.sto_card.stocard.network.ApiUtils
import com.example.sto_card.stocard.utils.Utils
import com.google.firebase.iid.FirebaseInstanceId
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_registration.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback

import java.io.File


class RegistrationFragment : Fragment() {

    lateinit var resultUri:Uri
    lateinit var imageview:ImageView
    lateinit var plus:ImageView
    lateinit var Sign_Up:Button
    lateinit var already_acc:TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

    }
    public fun onChooseFile(v: View)
    {
        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(
            context as Activity,
            this
        )
    }


    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View =inflater.inflate(R.layout.fragment_registration, container, false)
        imageview=view.findViewById<ImageView>(R.id.imageView)
         plus = view.findViewById<ImageView>(R.id.Plus)
        Sign_Up = view.findViewById<Button>(R.id.Sign_up)
        already_acc = view.findViewById<Button>(R.id.already_have_Account)
        already_acc.setOnClickListener{
            LoadFragment(LoginFragment())
        }
        plus.setOnClickListener(View.OnClickListener {
            onChooseFile(imageview)
        }

        )
        Sign_Up.setOnClickListener{
            val name=Name.text.toString().trim()
            val email=Email.text.toString().trim()
            val phone=Phone.text.toString().trim()
            val password=Password.text.toString().trim()
            val pin=ConfirmPassword.text.toString().trim()

            if(name.isEmpty())
            {
                Name.error="Name Required"
                Name.requestFocus()
                return@setOnClickListener
            }
            if(email.isEmpty())
            {
                Email.error="Email Required"
                Email.requestFocus()
                return@setOnClickListener
            }
            if(phone.isEmpty())
            {
                Phone.error="Phone Required"
                Phone.requestFocus()
                return@setOnClickListener
            }
            if(password.isEmpty())
            {
                Password.error="Password Required"
                Password.requestFocus()
                return@setOnClickListener
            }
            if(pin.isEmpty())
            {
                ConfirmPassword.error="Password Required"
                ConfirmPassword.requestFocus()
                return@setOnClickListener
            }
            val tkn = FirebaseInstanceId.getInstance().token
            //Response start
            Log.d("FCM_Token////",tkn.toString())
            //set Image
            val file: File = File(URIPathHelper.getPath(requireContext(), resultUri!!))
            val requestFile = RequestBody.create(
                requireActivity().contentResolver.getType(resultUri!!)?.toMediaTypeOrNull(),
                file
            )
            val body = MultipartBody.Part.createFormData("user_img", file.name, requestFile)
            //set Image finish

            var mAPIService: Api? = null
            mAPIService = ApiUtils.apiService


            val deviceId = Settings.Secure.getString(
                requireActivity().contentResolver,
                Settings.Secure.ANDROID_ID
            )
            Log.d("deviceid", deviceId)

            //set other data
            val map: MutableMap<String, RequestBody> = HashMap()
            map["name"] = toPart(name) as RequestBody
            map["email"] = toPart(email)
            map["password"] = toPart(password)
            map["phone"] = toPart(phone)
            map["pin"] = toPart(pin)
            map["device_id"] = toPart(tkn.toString())
            //set other data finish

            //send request
            mAPIService!!.createUser("",body,"register",map).enqueue(object :
                Callback<DefaultResponse> {
                override fun onResponse(
                        call: Call<DefaultResponse>,
                        response: retrofit2.Response<DefaultResponse>
                ) {

                    Log.e("macro", "responseA->" + response)
                    Log.d("hell............", response.body()?.message.toString())
                      Utils().showToast(context,response.body()?.message.toString())
                      LoadFragment(LoginFragment())


                }

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Log.e("macro", "failureA->" + t);
                }

            })
        //finish
        }


        return view
    }
    fun toPart(data: String): RequestBody {
        return RequestBody.create("text/plain".toMediaTypeOrNull(), data)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                resultUri = result.uri
                imageview.setImageURI(resultUri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val e:Exception=result.error
             Toast.makeText(context,"profile Error"+e,Toast.LENGTH_LONG).show()

            }
        }
    }
    fun LoadFragment(fragment:Fragment)
    {
        val fragmentManager: FragmentManager? = fragmentManager
        fragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, fragment)
                ?.commit()

    }
}