package com.example.a2paths

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a2paths.databinding.ActivityProfProfileBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfProfileActivity : AppCompatActivity() {

    private var mBinding: ActivityProfProfileBinding? = null
    private val binding get() = mBinding!!

    val firebase = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityProfProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getProfile()

        binding.btnConsult.setOnClickListener {
            val intent = Intent(this, ConsultActivity::class.java)
            intent.putExtra("prof", binding.tvName.text.toString())
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getProfile() {
        val data = intent.getStringExtra("name")

        firebase.collection("prof")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (i in task.result!!) {
                        if (i.data["name"] == data.toString()) {
                            val name = i.data["name"]
                            val office = i.data["office"]
                            val major = i.data["major"]
                            val link = i.data["link"]
                            binding.tvName.text = name.toString()
                            binding.tvOffice.text = office.toString()
                            binding.tvMajor.text = major.toString()
                            binding.tvLink.text = link.toString()
                            binding.ivProfile.setImageResource(R.drawable.kjh)
//                            if (i.data["name"] == "곽병수") {
//                                binding.ivProfile.setImageResource(R.drawable.kbs)
//                            } else if (i.data["name"] == "권진회") {
//                                binding.ivProfile.setImageResource(R.drawable.kjh)
//                            } else if (i.data["name"] == "김동현") {
//                                binding.ivProfile.setImageResource(R.drawable.kdh)
//                            } else if (i.data["name"] == "김병수") {
//                                binding.ivProfile.setImageResource(R.drawable.kbs2)
//                            } else if (i.data["name"] == "김윤수") {
//                                binding.ivProfile.setImageResource(R.drawable.kys)
//                            } else if (i.data["name"] == "김재호") {
//                                binding.ivProfile.setImageResource(R.drawable.kjh2)
//                            } else if (i.data["name"] == "김해동") {
//                                binding.ivProfile.setImageResource(R.drawable.khd)
//                            } else if (i.data["name"] == "남영우") {
//                                binding.ivProfile.setImageResource(R.drawable.nyw)
//                            } else if (i.data["name"] == "명노신") {
//                                binding.ivProfile.setImageResource(R.drawable.mns)
//                            } else if (i.data["name"] == "문용호") {
//                                binding.ivProfile.setImageResource(R.drawable.myh)
//                            } else if (i.data["name"] == "박재현") {
//                                binding.ivProfile.setImageResource(R.drawable.bjh)
//                            } else if (i.data["name"] == "슈스미트박취") {
//                                binding.ivProfile.setImageResource(R.drawable.bagchi)
//                            } else if (i.data["name"] == "아말렌두사우") {
//                                binding.ivProfile.setImageResource(R.drawable.sau)
//                            } else if (i.data["name"] == "이선아") {
//                                binding.ivProfile.setImageResource(R.drawable.lsa)
//                            } else if (i.data["name"] == "이성진") {
//                                binding.ivProfile.setImageResource(R.drawable.lsj)
//                            } else if (i.data["name"] == "이학진") {
//                                binding.ivProfile.setImageResource(R.drawable.lhj)
//                            } else if (i.data["name"] == "전용기") {
//                                binding.ivProfile.setImageResource(R.drawable.jyg)
//                            } else if (i.data["name"] == "정필수") {
//                                binding.ivProfile.setImageResource(R.drawable.jps)
//                            }
                        }
                    }
                }
            }
    }
}