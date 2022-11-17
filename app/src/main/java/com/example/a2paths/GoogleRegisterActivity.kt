package com.example.a2paths

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.a2paths.databinding.ActivityGoogleRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GoogleRegisterActivity : AppCompatActivity() {

    private var mBinding: ActivityGoogleRegisterBinding? = null
    private val binding get() = mBinding!!

    private val firebase = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    private val user = Firebase.auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityGoogleRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.etNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (binding.etNumber.length() < 10) {
                    binding.tvChecknumber.setTextColor(Color.parseColor("#ff0000"))
                } else {
                    binding.tvChecknumber.setTextColor(Color.parseColor("#ffffff"))
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.etNumber.length() < 10) {
                    binding.tvChecknumber.setTextColor(Color.parseColor("#ff0000"))
                } else {
                    binding.tvChecknumber.setTextColor(Color.parseColor("#ffffff"))
                }
            }
        })

        binding.btnNext.setOnClickListener {
            val email = user!!.email.toString()
            val name = binding.etName.text.toString()
            val number = binding.etNumber.text.toString()
            val grade = binding.etGrade.text.toString()
            val state = ""
            val uid = user.uid

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

            val data = hashMapOf(
                "id" to email,
                "name" to name,
                "number" to number,
                "grade" to grade,
                "flight" to flight,
                "software" to software,
                "state" to state,
                "uid" to uid,
            )

            firebase.collection("user").document(user.email.toString()).set(data)
            Toast.makeText(this, "가입을 축하합니다", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SubActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}