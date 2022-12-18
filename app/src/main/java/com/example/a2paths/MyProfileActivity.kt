package com.example.a2paths

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.a2paths.databinding.ActivityMyProfileBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import kotlin.experimental.or

class MyProfileActivity : AppCompatActivity() {

    private var mBinding: ActivityMyProfileBinding? = null
    private val binding get() = mBinding!!

    val firebase = Firebase.firestore
    private val user = Firebase.auth.currentUser

    val REQ_STORAGE = 101


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)


        getProfile()

        binding.btnClass.setOnClickListener {
            val intent = Intent(this, MyClassActivity::class.java)
            startActivity(intent)
        }
        binding.btnAcImage.setOnClickListener {
            fromGallery()
            imageUpload()
        }

        binding.btnSave.setOnClickListener {
            saveProfile()
            Toast.makeText(this, "저장되었습니다", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnCancel.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    private fun getProfile() { //프로필 가져오기
        firebase.collection("user").document(user?.email.toString())
            .get()
            .addOnSuccessListener { document ->
                binding.tvName.text = document["name"].toString()
                binding.tvGrade.text = document["grade"].toString()
                binding.tvNumber.text = document["number"].toString()
                binding.cbFlight.isChecked = document["flight"].toString() == "true"
                binding.cbSoftware.isChecked = document["software"].toString() == "true"
                binding.etState.text = Editable.Factory.getInstance().newEditable(document["state"].toString())
            }
    }

    private fun saveProfile() { //프로필 저장
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


        val data = hashMapOf(
            "flight" to flight,
            "software" to software,
            "state" to state,
        )

        firebase.collection("user").document(user?.email.toString()).set(data, SetOptions.merge())
    }

    private fun imageUpload() {
        var databaseRef : DatabaseReference = FirebaseDatabase.getInstance().reference

        val image : Drawable = binding.ivProfile.drawable
        val bitmap : Bitmap = (image as BitmapDrawable).bitmap

        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)

        val uploadImage = stream.toByteArray()
        val svimage = byteArrayToBinaryString(uploadImage)

        val key : String = databaseRef.child("/images").push().key!!
        val childUpdates : MutableMap<String, Any> = HashMap()
        childUpdates["/images/$key"] = svimage.toString()
        databaseRef.updateChildren(childUpdates)

        val data = hashMapOf(
            "image" to svimage.toString()
        )

        firebase.collection("user").document(user?.email.toString()).set(data, SetOptions.merge())

    }

    private fun byteArrayToBinaryString(b: ByteArray) : StringBuilder {
        var sb  : StringBuilder = StringBuilder()

        for (element in b){
            sb.append(byteToBinaryString(element))
        }
        return sb
    }

    private fun byteToBinaryString(n : Byte) : String{
        var sb : StringBuilder = StringBuilder("00000000")
        for (bit in 0 until 8) {
            if((n.toInt().shr(bit) and 1) > 0){
                sb.setCharAt(7-bit, '1')
            }
        }
        return sb.toString()
    }
    private fun fromGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, REQ_STORAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQ_STORAGE -> {
                    data?.data?.let { uri ->
                        binding.ivProfile.setImageURI(uri)
                    }
                }
            }
        }
    }

    private fun imageDownload() {
        var databaseRef : DatabaseReference =
            FirebaseDatabase.getInstance().getReference("/images")

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot: DataSnapshot in snapshot.children){
                val image: String = dataSnapshot.getValue().toString()

                val b: ByteArray = binaryStringToByteArray(image)
                val stream = ByteArrayInputStream(b)
                val down_image = Drawable.createFromStream(stream,"image")
                binding.ivProfile.setImageDrawable(down_image)
                }
            }
        })
    }

    private fun binaryStringToByteArray(s : String)  :ByteArray {
        val count : Int = s.length / 8
        val b = ByteArray(count)

        for(i in 1 until count) {
            val t = s.substring((i-1)*8, i*8)
            b[i-1] = binaryStringToByte(t)
        }
        return b
    }

    private fun binaryStringToByte(s:String) : Byte {
        var ret : Byte
        var total  : Byte = 0

        for(i in 0 until 8) {
            ret = if(s.get(7-i)=='1')
                    (1.shl(i)).toByte()
                  else 0
            total = (ret.or(total))
        }
        return total
    }
}