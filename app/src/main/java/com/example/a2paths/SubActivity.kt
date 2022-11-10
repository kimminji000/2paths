package com.example.a2paths

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a2paths.databinding.ActivitySubBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class SubActivity : AppCompatActivity() {

    private var mBinding: ActivitySubBinding?=null
    private val binding get() =mBinding!!

    lateinit var mmbinding:ActivitySubBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding= ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_frame,HomeFragment()).commit()

        binding.bottomNavi.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.item_fragment1 -> {
                    transaction.replace(R.id.main_frame,HomeFragment()).commit()
                }
                R.id.item_fragment2 -> {
                    transaction.replace(R.id.main_frame,ChatFragment()).commit()
                }
                R.id.item_fragment3 -> {
                    transaction.replace(R.id.main_frame,ConsultFragment()).commit()
                }
                R.id.item_fragment4 -> {
                    transaction.replace(R.id.main_frame,SettingFragment()).commit()
                }
            }
            true
        }
    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }
}