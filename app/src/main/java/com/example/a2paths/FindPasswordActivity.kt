package com.example.a2paths


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a2paths.databinding.ActivityFindPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_find_password.*


class FindPasswordActivity : AppCompatActivity(){
    private lateinit var auth: FirebaseAuth
    private var mBinding: ActivityFindPasswordBinding? = null
    private val binding get() = mBinding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityFindPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mBinding!!.btnCheck.setOnClickListener{
            val emailAddress = et_email.text.toString()
            if (emailAddress.isEmpty()){
                Toast.makeText(this, "이메일을 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            }
            else{
                Firebase.auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "메일을 전송했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
        mBinding!!.imageButton1.setOnClickListener{
            onBackPressed()
        }
    }



}
