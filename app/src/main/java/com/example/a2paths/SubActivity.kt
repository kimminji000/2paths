package com.example.a2paths

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a2paths.databinding.ActivitySubBinding

class SubActivity : AppCompatActivity() {

    private var mBinding: ActivitySubBinding?=null
    private val binding get() =mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)

        //setContentView(R.layout.activity_main)
        mBinding= ActivitySubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFrag(0)

        binding.btnFragement0.setOnClickListener {
            setFrag(0)
        }

        binding.btnFragement1.setOnClickListener {
            setFrag(1)
        }

        binding.btnFragement2.setOnClickListener {
            setFrag(2)
        }

        binding.btnFragement3.setOnClickListener {
            setFrag(3)
        }
    }

    private fun setFrag(fragNum : Int) {
        val ft = supportFragmentManager.beginTransaction()
        when(fragNum){
            0 -> {
                ft.replace(R.id.main_frame, HomeFragment()).commit()
            }
            1 -> {
                ft.replace(R.id.main_frame, ChatFragment()).commit()
            }
            2 -> {
                ft.replace(R.id.main_frame, ConsultFragment()).commit()
            }
            3 -> {
                ft.replace(R.id.main_frame, SettingFragment()).commit()
            }
        }
    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }
}