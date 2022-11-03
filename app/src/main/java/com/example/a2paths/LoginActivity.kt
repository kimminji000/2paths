package com.example.a2paths

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.a2paths.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {

    private var lBinding: ActivityLoginBinding?=null
    //private val binding get() =lBinding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)

        //auth 초기화
        auth = Firebase.auth

        lBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(lBinding!!.root)


        //로그인버튼 클릭이벤트
        lBinding!!.btnULogin.setOnClickListener {
            val email = lBinding!!.etEmail.text.toString()
            val password = lBinding!!.etPassword.text.toString()

            login(email,password)

        }

    }

    //로그인함수
    private fun login(email:String, password:String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    var intent = Intent(this@LoginActivity, SubActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this,"*** Welcome ***",Toast.LENGTH_SHORT).show()
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