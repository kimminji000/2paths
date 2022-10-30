package com.example.a2paths

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a2paths.databinding.ActivityProfileBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity()  {

    private var mBinding: ActivityProfileBinding?=null
    private val binding get() =mBinding!!
    val firebase = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getStringExtra("stunum")

        firebase.collection("user")
            .get()
            .addOnCompleteListener { task ->
                var afound = false

                if (task.isSuccessful) {
                    for(i in task.result!!) {
                        if (i.id == data.toString()) {
                            val name = i.data["name"]
                            val stunum = i.data["stunum"]
                            val grade = i.data["grade"]
                            val field = i.data["field"]
                            val state = i.data["state"]
                            binding.tvName.text = name.toString()
                            binding.tvStunum.text = stunum.toString()
                            binding.tvGrade.text = grade.toString()
                            binding.tvField.text = field.toString()
                            binding.tvState.text = state.toString()
                            afound = true
                            break
                        }
                    }
                }
            }

    }
}