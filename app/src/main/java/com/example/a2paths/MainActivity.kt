package com.example.a2paths

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.a2paths.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    val firebase = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    private val user = Firebase.auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        val pref = getSharedPreferences("other", 0)
        val email = pref.getString("email", "").toString()
        if (email != "") {
            val password = pref.getString("password", "").toString()
            Toast.makeText(this, "자동로그인", Toast.LENGTH_SHORT).show()
            login(email, password)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (binding.cbLogin.isChecked) {
                val sharedPreference = getSharedPreferences("other", 0)
                val editor = sharedPreference.edit()
                editor.putString("email", email)
                editor.putString("password", password)
                editor.apply()
            }

            login(email, password)
        }

        binding.textView5.setOnClickListener {
            Toast.makeText(this@MainActivity, "지원예정중인 기능입니다.", Toast.LENGTH_SHORT).show()
        }

        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnGoogle.setOnClickListener {
            val intent = Intent(this, SubActivity::class.java)
            startActivity(intent)
        }
    }

    //로그인함수
    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    firebase.collection("user").document(user?.email.toString())
                        .get()
                        .addOnSuccessListener { document ->
                            val uid = document["uid"].toString()
                            if (uid == "") {
                                val data = hashMapOf(
                                    "uid" to user!!.uid,
                                )
                                firebase.collection("user").document(user.email.toString()).set(data, SetOptions.merge())
                            }
                        }

                    val intent = Intent(this@MainActivity, SubActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "*** Welcome ***", Toast.LENGTH_SHORT).show()
                    finish()//액티비티 종료기능
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "로그인 실패. 다시 시도하세요.", Toast.LENGTH_SHORT).show()
                    Log.d("Login", "Error:${task.exception}")
                }
            }//10:53
    }
}