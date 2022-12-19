package com.example.a2paths

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a2paths.databinding.ActivityStuProfileBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class StuProfileActivity : AppCompatActivity() {

    private var mBinding: ActivityStuProfileBinding? = null
    private val binding get() = mBinding!!

    val firebase = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityStuProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getStringExtra("number")

        firebase.collection("user")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (i in task.result!!) {
                        if (i.data["number"] == data.toString()) {
                            binding.tvName.text = i.data["name"].toString()
                            binding.tvGrade.text = i.data["grade"].toString()
                            binding.tvNumber.text = i.data["number"].toString().substring(2 until 4)
                            binding.tvState.text = i.data["state"].toString()
                            val email = i.data["id"].toString()
                            val uid = i.data["uid"].toString()

                            var field = ""
                            if (i.data["flight"].toString() == "true" && i.data["software"].toString() == "true") {
                                field += "항공우주 소프트웨어"
                            } else {
                                if (i.data["flight"].toString() == "true") {
                                    field += "항공우주"
                                }
                                if (i.data["software"].toString() == "true") {
                                    field += "소프트웨어"
                                }
                            }
                            binding.tvField.text = field

                            binding.btnClass.setOnClickListener {
                                val intent = Intent(this, ClassActivity::class.java)
                                intent.putExtra("email", email)
                                startActivity(intent)
                            }

                            binding.btnChat.setOnClickListener {
                                val intent = Intent(this, ChatActivity::class.java)
                                intent.putExtra("destinationUid", uid)
                                startActivity(intent)
                            }
                            break
                        }
                    }
                }
            }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }
}