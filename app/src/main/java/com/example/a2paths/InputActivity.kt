package com.example.a2paths

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a2paths.databinding.ActivityInputBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class InputActivity : AppCompatActivity() {

    private var mBinding: ActivityInputBinding?=null
    private val binding get() =mBinding!!

    val firebase = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding=ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnInput.setOnClickListener {

            val aUserName=binding.etName.text
            val aUserStunum=binding.etStunum.text
            val aUserGrade=binding.etGrade.text
            var aUserField = ""

            if(binding.cbFlight.isChecked){
                aUserField += "항공우주"
            }
            if(binding.cbSoftware.isChecked){
                aUserField += "소프트웨어"
            }

            val aUserdata=hashMapOf(
                "name" to aUserName.toString(),
                "stunum" to aUserStunum.toString(),
                "grade" to aUserGrade.toString(),
                "field" to aUserField,
            )

            firebase.collection("user").document(aUserStunum.toString()).set(aUserdata)

            var intent = Intent(this, SubActivity::class.java)
            startActivity(intent)

        }
    }

    override fun onDestroy() {
        mBinding=null
        super.onDestroy()
    }
}