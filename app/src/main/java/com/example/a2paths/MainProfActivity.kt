package com.example.a2paths

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.a2paths.databinding.ActivityMainProfBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainProfActivity : AppCompatActivity() {
//수정필요
    private var mBinding: ActivityMainProfBinding? = null
    private val binding get() = mBinding!!

    val firebase = Firebase.firestore
    private var mBackWait:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainProfBinding.inflate(layoutInflater)
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
                R.id.item_fragment3 -> { //수정필요
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.main_frame, ConsultProfFragment()).commit()
                }
                R.id.item_fragment4 -> {
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.main_frame, SettingFragment()).commit()
                }
            }
        }
    }
    // 뒤로가기
    override fun onBackPressed() {
        if(System.currentTimeMillis() - mBackWait >=2000 ) {
            mBackWait = System.currentTimeMillis()
            Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        } else {
            finish()
        }
    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }
}