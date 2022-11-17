package com.example.a2paths

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.a2paths.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
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

    private var googleSignInClient: GoogleSignInClient? = null
    private var GOOGLE_LOGIN_CODE = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        //autoLogin()

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

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.btnGoogle.setOnClickListener {
            googleLogin()
        }
    }

    private fun autoLogin() {
        val pref = getSharedPreferences("other", 0)
        val email = pref.getString("email", "").toString()
        if (email != "") {
            val password = pref.getString("password", "").toString()
            Toast.makeText(this, "자동로그인", Toast.LENGTH_SHORT).show()
            login(email, password)
        }
    }

    private fun googleLogin() {
        val signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, GOOGLE_LOGIN_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_LOGIN_CODE) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data!!)!!

            if (result.isSuccess) {
                val account = result.signInAccount
                firebaseAuthWithGoogle(account)
                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
            }
            /*
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
            }*/
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, SubActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    firebase.collection("user").document(user?.email.toString())
                        .get()
                        .addOnSuccessListener { document ->
                            val uid = document["uid"].toString()
                            val intent = Intent(this, ChatActivity::class.java)
                            intent.putExtra("uid", uid)
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
                    finish()
                } else {
                    Toast.makeText(baseContext, "로그인 실패. 다시 시도하세요.", Toast.LENGTH_SHORT).show()
                    Log.d("Login", "Error:${task.exception}")
                }
            }
    }
}