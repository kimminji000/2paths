package com.example.a2paths

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.a2paths.databinding.ActivitySubBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SubActivity : AppCompatActivity() {

    private var mBinding: ActivitySubBinding? = null
    private val binding get() = mBinding!!

    val firebase = Firebase.firestore
    private val user = Firebase.auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_frame, HomeFragment()).commit()

        binding.bottomNavi.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.item_fragment1 -> {
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.main_frame, HomeFragment()).commit()
                }
                R.id.item_fragment2 -> {
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.main_frame, ChatFragment()).commit()
                }
                R.id.item_fragment3 -> {
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.main_frame, ConsultFragment()).commit()
                }
                R.id.item_fragment4 -> {
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.main_frame, SettingFragment()).commit()
                }
            }
        }
    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }
}