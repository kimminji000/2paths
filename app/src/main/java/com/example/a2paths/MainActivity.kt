package com.example.a2paths

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a2paths.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(binding.root)
        val mmbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mmbinding.root)
        mmbinding.btnEmpty.setOnClickListener{
            var intent = Intent(this,SubActivity::class.java)
            startActivity(intent)
        }
    }
}