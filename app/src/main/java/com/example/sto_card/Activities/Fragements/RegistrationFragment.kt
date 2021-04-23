package com.example.sto_card.Activities.Fragements



import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.sto_card.R
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView


class RegistrationFragment : Fragment() {

    lateinit var uri:Uri
    lateinit var imageview:ImageView
    lateinit var plus:ImageView
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View =inflater.inflate(R.layout.fragment_registration, container, false)
        imageview=view.findViewById<ImageView>(R.id.imageView)
         plus = view.findViewById<ImageView>(R.id.Plus)
        plus.setOnClickListener(View.OnClickListener {
            onChooseFile(imageview)
        }

        )


        return view
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri = result.uri
                imageview.setImageURI(resultUri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val e:Exception=result.error
             Toast.makeText(context,"profile Error"+e,Toast.LENGTH_LONG).show()
            }
        }
    }

}