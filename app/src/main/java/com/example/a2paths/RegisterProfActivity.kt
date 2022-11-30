package com.example.a2paths

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.a2paths.databinding.ActivityRegisterProfBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterProfActivity : AppCompatActivity() {

    private var mBinding: ActivityRegisterProfBinding? = null
    private val binding get() = mBinding!!

    private val firebase = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    private val user = Firebase.auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityRegisterProfBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnCheck.setOnClickListener { //메일주소 중복 확인
            val email = binding.etEmail.text.toString().trim()
            var check = false

            firebase.collection("prof").get().addOnSuccessListener { result ->
                for (document in result) {
                    if (email == document["id"]) {
                        check = true
                    }
                }
                if (check) {
                    binding.tvCheckemail.text = "이미 사용중인 메일입니다."
                    binding.tvCheckemail.setTextColor(Color.parseColor("#ff0000"))
                } else {
                    binding.tvCheckemail.text = "사용할 수 있는 메일입니다."
                    binding.tvCheckemail.setTextColor(Color.parseColor("#008000"))
                }
            }
        }

        binding.etPassword.addTextChangedListener(object : TextWatcher { //비밀번호 일치여부 확인
            override fun afterTextChanged(p0: Editable?) {
                if (binding.etPassword.text.toString().trim() == binding.etPassword2.text.toString().trim()) {
                    binding.tvCheckpassword.text = "비밀번호가 일치합니다."
                    binding.tvCheckpassword.setTextColor(Color.parseColor("#008000"))
                } else {
                    binding.tvCheckpassword.text = "비밀번호가 일치하지 않습니다."
                    binding.tvCheckpassword.setTextColor(Color.parseColor("#ff0000"))
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.etPassword.text.toString().trim() == binding.etPassword2.text.toString().trim()) {
                    binding.tvCheckpassword.text = "비밀번호가 일치합니다."
                    binding.tvCheckpassword.setTextColor(Color.parseColor("#008000"))
                } else {
                    binding.tvCheckpassword.text = "비밀번호가 일치하지 않습니다."
                    binding.tvCheckpassword.setTextColor(Color.parseColor("#ff0000"))
                }
            }
        })

        binding.etPassword2.addTextChangedListener(object : TextWatcher { //비밀번호 일치여부 확인
            override fun afterTextChanged(p0: Editable?) {
                if (binding.etPassword.text.toString().trim() == binding.etPassword2.text.toString().trim()) {
                    binding.tvCheckpassword.text = "비밀번호가 일치합니다."
                    binding.tvCheckpassword.setTextColor(Color.parseColor("#008000"))
                } else {
                    binding.tvCheckpassword.text = "비밀번호가 일치하지 않습니다."
                    binding.tvCheckpassword.setTextColor(Color.parseColor("#ff0000"))
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.etPassword.text.toString().trim() == binding.etPassword2.text.toString().trim()) {
                    binding.tvCheckpassword.text = "비밀번호가 일치합니다."
                    binding.tvCheckpassword.setTextColor(Color.parseColor("#008000"))
                } else {
                    binding.tvCheckpassword.text = "비밀번호가 일치하지 않습니다."
                    binding.tvCheckpassword.setTextColor(Color.parseColor("#ff0000"))
                }
            }
        })

        binding.btnNext.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val name = binding.etName.text.toString()
            val major = binding.etMajor.text.toString()
            val link = binding.etLink.text.toString()
            val uid = ""
            //DB에서 저장 및 관리되는 유저(교수) 데이터
            val data = hashMapOf(
                "id" to email,
                "password" to password,
                "name" to name,
                "major" to major,
                "link" to link,
                "uid" to uid,
            )

            firebase.collection("prof").document(name).set(data, SetOptions.merge())
            signUp(email, password)
        }
    }

    private fun signUp(email: String, password: String) { //교수진 회원가입
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    firebase.collection("prof").document(binding.etName.text.toString())
                        .get()
                        .addOnSuccessListener { document ->
                            val uid = document["uid"].toString()
                            if (uid == "") {
                                val data = hashMapOf(
                                    "uid" to user!!.uid,
                                )
                                firebase.collection("prof").document(binding.etName.text.toString())
                                    .set(data, SetOptions.merge())
                            }
                        }

                    Toast.makeText(this, "가입을 축하합니다", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "다시 시도해주세요", Toast.LENGTH_SHORT).show()
                }
            }
    }
}