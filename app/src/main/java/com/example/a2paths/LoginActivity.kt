package com.example.a2paths

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.a2paths.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {

    private var mBinding: ActivityLoginBinding? = null
    private val binding get() = mBinding!!

    val firebase = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    private val user = Firebase.auth.currentUser
    private val prof = Firebase.auth.currentUser

    private var googleSignInClient: GoogleSignInClient? = null
    private var googleLoginCode = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        deleteAuthLogin()

        autoLogin()

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

        binding.tvPassword.setOnClickListener {
            Toast.makeText(this, "지원예정중인 기능입니다.", Toast.LENGTH_SHORT).show()
        }

        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.tvProf.setOnClickListener {
            val intent = Intent(this, RegisterProfActivity::class.java)
            startActivity(intent)
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.btnGoogle.setOnClickListener {
            googleLogin()
        }
    }

    private fun autoLogin() { //자동로그인
        val pref = getSharedPreferences("other", 0)
        val email = pref.getString("email", "").toString()
        if (email != "") {
            val password = pref.getString("password", "").toString()
            Toast.makeText(this, "자동로그인", Toast.LENGTH_SHORT).show()
            login(email, password)
        }
    }

    private fun deleteAuthLogin() { //자동로그인 지우기
        val pref = getSharedPreferences("other", 0)
        val editor = pref.edit()
        editor.clear()
        editor.apply()
    }

    private fun googleLogin() { //구글로그인
        val signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, googleLoginCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == googleLoginCode) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data!!)!!
            if (result.isSuccess) {
                val account = result.signInAccount
                firebaseAuthWithGoogle(account)
            } else {
                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebase.collection("user").document(user?.email.toString())
                        .get()
                        .addOnSuccessListener { document ->
                            if (document["name"] == null) {
                                val intent = Intent(this, RegisterGoogleActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                                finishAffinity()
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            }
                        }
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    // 기본로그인
    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    firebase.collection("user").document(user?.email.toString())
                        .get()
                        .addOnSuccessListener { document ->
                            Toast.makeText(this, document["number"].toString(), Toast.LENGTH_SHORT).show()
                            if (document["number"].toString() == null) {
                                val intent = Intent(this, MainProfActivity::class.java)
                                finishAffinity()
                                startActivity(intent)
                                Toast.makeText(this, "***  ***", Toast.LENGTH_SHORT).show()
                                finish()
                            } else {
                                val intent = Intent(this, MainActivity::class.java)
                                finishAffinity()
                                startActivity(intent)
                                Toast.makeText(this, "*** Welcome ***", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                        }
                } else {
                    Toast.makeText(baseContext, "로그인 실패. 다시 시도하세요.", Toast.LENGTH_SHORT).show()
                    Log.d("Login", "Error:${task.exception}")
                }
            }
    }

//    override fun onDestroy() {
//        mBinding = null
//        super.onDestroy()
//        auth.signOut()
//        googleSignInClient?.signOut()
//    }
}