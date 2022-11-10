package com.example.a2paths

import android.content.Intent
import android.graphics.Color
import android.graphics.Color.BLACK
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.example.a2paths.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private var mBinding: ActivityRegisterBinding?=null
    private val binding get() =mBinding!!

    private val firebase = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding= ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnCheck.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            var checkemail : Boolean = false
            firebase.collection("user").get().addOnSuccessListener { result ->
                for (document in result) {
                    if (email == document["id"]) {
                        checkemail = true
                    }
                }
                if (checkemail) {
                    binding.tvCheckemail.text = "이미 사용중인 메일입니다."
                    binding.tvCheckemail.setTextColor(Color.parseColor("#ff0000"))
                }
                else {
                    binding.tvCheckemail.text = "사용할 수 있는 메일입니다."
                    binding.tvCheckemail.setTextColor(Color.parseColor("#008000"))
                }
            }
        }

        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(binding.etPassword.text.toString().trim() == binding.etPassword2.text.toString().trim()){
                    binding.tvCheckpassword.text = "비밀번호가 일치합니다."
                    binding.tvCheckpassword.setTextColor(Color.parseColor("#008000"))
                }
                else {
                    binding.tvCheckpassword.text = "비밀번호가 일치하지 않습니다."
                    binding.tvCheckpassword.setTextColor(Color.parseColor("#ff0000"))
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(binding.etPassword.text.toString().trim() == binding.etPassword2.text.toString().trim()){
                    binding.tvCheckpassword.text = "비밀번호가 일치합니다."
                    binding.tvCheckpassword.setTextColor(Color.parseColor("#008000"))
                }
                else {
                    binding.tvCheckpassword.text = "비밀번호가 일치하지 않습니다."
                    binding.tvCheckpassword.setTextColor(Color.parseColor("#ff0000"))
                }
            }
        })

        binding.etPassword2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(binding.etPassword.text.toString().trim() == binding.etPassword2.text.toString().trim()){
                    binding.tvCheckpassword.text = "비밀번호가 일치합니다."
                    binding.tvCheckpassword.setTextColor(Color.parseColor("#008000"))
                }
                else {
                    binding.tvCheckpassword.text = "비밀번호가 일치하지 않습니다."
                    binding.tvCheckpassword.setTextColor(Color.parseColor("#ff0000"))
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(binding.etPassword.text.toString().trim() == binding.etPassword2.text.toString().trim()){
                    binding.tvCheckpassword.text = "비밀번호가 일치합니다."
                    binding.tvCheckpassword.setTextColor(Color.parseColor("#008000"))
                }
                else {
                    binding.tvCheckpassword.text = "비밀번호가 일치하지 않습니다."
                    binding.tvCheckpassword.setTextColor(Color.parseColor("#ff0000"))
                }
            }
        })

        binding.btnNext.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val aUserName = binding.etName.text
            val aUserStunum = binding.etStunum.text
            val aUserGrade=binding.etGrade.text
            var aUserField = ""

            if(binding.cbFlight.isChecked){
                aUserField += "항공우주"
            }

            if(binding.cbSoftware.isChecked){
                aUserField += "소프트웨어"
            }

            val aUserdata=hashMapOf(
                "id" to email,
                "password" to password,
                "name" to aUserName.toString(),
                "stunum" to aUserStunum.toString(),
                "grade" to aUserGrade.toString(),
                "field" to aUserField,
            )

            firebase.collection("user").document(email).set(aUserdata)
            signUp(email, password)
        }
    }

    private fun signUp(email:String, password:String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this,"가입을 축하합니다", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                } else {
                    Toast.makeText(this,"다시 시도해주세요", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onDestroy() {
        mBinding=null
        super.onDestroy()
    }
}