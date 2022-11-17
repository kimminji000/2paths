package com.example.a2paths


import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Contacts.Photo
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.a2paths.databinding.ActivityMyProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class MyProfileActivity : AppCompatActivity() {

    private var mBinding: ActivityMyProfileBinding? = null
    private val binding get() = mBinding!!

    val firebase = Firebase.firestore
    private val user = Firebase.auth.currentUser

    lateinit var imgIv : ImageView
    //lateinit var addImgBtn : Button
    private var selectimgUri: Uri?= null

    lateinit var storage: FirebaseStorage

    val IMAGE_PICK=1111

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storage = FirebaseStorage.getInstance()


        firebase.collection("user").document(user?.email.toString())
            .get()
            .addOnSuccessListener { document ->
                binding.tvName.text = document["name"].toString()
                binding.tvGrade.text = document["grade"].toString()
                binding.tvNumber.text = document["number"].toString()
                binding.cbFlight.isChecked = document["flight"].toString() == "true"
                binding.cbSoftware.isChecked = document["software"].toString() == "true"
                binding.etState.text = Editable.Factory.getInstance().newEditable(document["state"].toString())
                //binding.ivProfile.setImageURI(selectimgUri)
            }


        binding.btnSave.setOnClickListener {
            val flight = if (binding.cbFlight.isChecked) {
                "true"
            } else {
                "false"
            }
            val software = if (binding.cbSoftware.isChecked) {
                "true"
            } else {
                "false"
            }

            val state = binding.etState.text.toString()
            /*
            var imgUrl: String? = null
            var photo: Photoinfo? = null

            if(selectimgUri != null){
                var filename = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
                storage.getReference().child("image").child(filename)
                    .putFile(selectimgUri!!)
                    .addOnSuccessListener {
                        taskSnapshot -> taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener {
                            it ->
                                  imgUrl = it.toString()
                                  photo = Photoinfo(imgUrl!!)
                        }
                    }
            }*/

            val data = hashMapOf(
                "flight" to flight,
                "software" to software,
                "state" to state,
                //"photo" to photo
            )
            /*
            binding.btnAddImage.setOnClickListener{
                //gotoCamera()
            }
            */

            firebase.collection("user").document(user?.email.toString()).set(data, SetOptions.merge())
            Toast.makeText(this, "저장되었습니다", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SubActivity::class.java)
            startActivity(intent)
        }

        binding.btnCancel.setOnClickListener {
            val intent = Intent(this, SubActivity::class.java)
            startActivity(intent)
        }

    }
   /*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == IMAGE_PICK && resultCode == RESULT_OK){
            selectimgUri = data?.data
            imgIv.setImageURI(selectimgUri)
        }
    }*/




    /*
    private fun gotoCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            packageManager?.let{
                intent.resolveActivity(it).also {
                    startActivityForResult(intent, REQ_CAM)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CAM && requestCode == RESULT_OK){
            val imgBitmap = data?.extras?.get("data") as Bitmap
            uploadImgToFirebase(imgBitmap)
        }
    }

    private fun uploadImgToFirebase(imgBitmap : Bitmap) {
        val baos = ByteArrayOutputStream()
        val ref = FirebaseStorage.getInstance().reference.child("img_user/${FirebaseAuth.getInstance().currentUser?.email}")
        imgBitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)

        val image = baos.toByteArray()
        ref.putBytes(image)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    ref.downloadUrl.addOnCompleteListener { Task->
                        Task.result.let { Uri->
                            imgUri = Uri
                            binding.ivProfile.setImageBitmap(imgBitmap)
                        }
                    }
                }
            }
    }


    companion object{
        const val REQ_CAM = 100
    }*/
    //https://velog.io/@galaxy/registerForActivityResult-Firebase-Storage%EB%A5%BC-%ED%99%9C%EC%9A%A9%ED%95%B4-%EC%9D%B4%EB%AF%B8%EC%A7%80-%EC%97%85%EB%A1%9C%EB%93%9C-%ED%95%98%EA%B8%B0

}