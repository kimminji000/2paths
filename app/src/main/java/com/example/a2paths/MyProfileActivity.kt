package com.example.a2paths

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import com.example.a2paths.databinding.ActivityMyProfileBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MyProfileActivity : AppCompatActivity() {

    private var mBinding: ActivityMyProfileBinding? = null
    private val binding get() = mBinding!!

    val firebase = Firebase.firestore
    private val user = Firebase.auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getProfile()

        binding.btnClass.setOnClickListener {
            val intent = Intent(this, MyClassActivity::class.java)
            startActivity(intent)
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

    private fun getProfile() {
        firebase.collection("user").document(user?.email.toString())
            .get()
            .addOnSuccessListener { document ->
                binding.tvName.text = document["name"].toString()
                binding.tvGrade.text = document["grade"].toString()
                binding.tvNumber.text = document["number"].toString()
                binding.cbFlight.isChecked = document["flight"].toString() == "true"
                binding.cbSoftware.isChecked = document["software"].toString() == "true"
                binding.etState.text =
                    Editable.Factory.getInstance().newEditable(document["state"].toString())
            }
    }

    private fun saveProfile() {
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
}