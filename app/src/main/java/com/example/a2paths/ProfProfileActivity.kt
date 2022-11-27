package com.example.a2paths

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a2paths.databinding.ActivityProfProfileBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfProfileActivity : AppCompatActivity() {

    private var mBinding: ActivityProfProfileBinding? = null
    private val binding get() = mBinding!!

    val firebase = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityProfProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getProfile()
    }

    private fun getProfile() {
        val data = intent.getStringExtra("name")

        firebase.collection("prof")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (i in task.result!!) {
                        if (i.id == data.toString()) {
                            val name = i.data["name"]
                            val office = i.data["office"]
                            val major = i.data["major"]
                            val link = i.data["link"]
                            binding.tvName.text = name.toString()
                            binding.tvOffice.text = office.toString()
                            binding.tvMajor.text = major.toString()
                            binding.tvLink.text = link.toString()
                        }
                    }
                }
            }
    }
}