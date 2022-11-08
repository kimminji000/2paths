package com.example.a2paths

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.a2paths.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding?=null
    private val binding get() =mBinding!!

    private val firebase = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        var actionBar : ActionBar?
        actionBar = supportActionBar
        actionBar?.hide()

        binding.btnLogin.setOnClickListener{
            val email = binding!!.etEmail.text.toString()
            val password = binding!!.etPassword.text.toString()

            login(email,password)
        }

        binding.textView4.setOnClickListener{
            Toast.makeText(this@MainActivity, "토스트 메세지 띄우기 입니다.", Toast.LENGTH_SHORT).show()
        }

        binding.textView5.setOnClickListener{
            Toast.makeText(this@MainActivity, "토스트 메세지 띄우기 입니다.", Toast.LENGTH_SHORT).show()
        }

        binding.tvRegister.setOnClickListener{
            var intent = Intent(this,InputActivity::class.java)
            startActivity(intent)
        }

        binding.btnGoogle.setOnClickListener {
            var intent = Intent(this,SubActivity::class.java)
            startActivity(intent)
        }
    }

    //로그인함수
    private fun login(email:String, password:String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    var intent = Intent(this@MainActivity, SubActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this,"*** Welcome ***", Toast.LENGTH_SHORT).show()
                    finish()//액티비티 종료기능
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "로그인 실패. 다시 시도하세요.",
                        Toast.LENGTH_SHORT).show()
                    Log.d("Login","Error:${task.exception}")
                }
            }//10:53
    }
}