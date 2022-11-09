package com.example.a2paths

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.a2paths.databinding.ActivityInputBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class InputActivity : AppCompatActivity() {

    private var mBinding: ActivityInputBinding?=null
    private val binding get() =mBinding!!

    private val firebase = Firebase.firestore
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding=ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //auth 초기화
        auth = Firebase.auth

        binding.btnInput.setOnClickListener {
            //데이터 저장
            val email = binding.etSEmail.text.toString().trim()
            val password = binding.etSPassword.text.toString().trim()

            val aUserName = binding.etName.text
            val aUserStunum = binding.etStunum.text
            val aUserGrade=binding.etGrade.text
            var aUserField = ""
            val aUserState=binding.etState.text
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
                "state" to aUserState.toString(),
            )

            firebase.collection("user").document(aUserStunum.toString()).set(aUserdata)
            /*
            var intent = Intent(this, SubActivity::class.java)
            startActivity(intent)
            */

            
            signUp(email,password)

        }
    }
    
    //회원가입함수
    private fun signUp(email:String, password:String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this,"가입을 축하합니다",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@InputActivity,LoginActivity::class.java)
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this,"다시 시도해주세요",Toast.LENGTH_SHORT).show()
                }
            }
        
    }
    

    override fun onDestroy() {
        mBinding=null
        super.onDestroy()
    }
}