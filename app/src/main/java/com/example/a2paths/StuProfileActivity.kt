package com.example.a2paths

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a2paths.databinding.ActivityStuProfileBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.jar.Attributes.Name

class StuProfileActivity : AppCompatActivity()  {

    private var mBinding: ActivityStuProfileBinding?=null
    private val binding get() =mBinding!!
    val firebase = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding= ActivityStuProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getStringExtra("stunum")

        firebase.collection("user")
            .get()
            .addOnCompleteListener { task ->
                var afound = false

                if (task.isSuccessful) {
                    for (i in task.result!!) {
                        if (i.id == data.toString()) {
                            val name = i.data["name"]
                            val stunum = i.data["stunum"]
                            val grade = i.data["grade"]
                            val field = i.data["field"]
                            val state = i.data["state"]
                            val uid = i.id
                            binding.tvName.text = name.toString()
                            binding.tvStunum.text = stunum.toString()
                            binding.tvGrade.text = grade.toString()
                            binding.tvField.text = field.toString()
                            binding.tvState.text = state.toString()
                            binding.btnChat.setOnClickListener{
                                val intent = Intent(this, ChatActivity::class.java)
                                intent.putExtra("name", binding.tvName.text)
                                intent.putExtra("uId", i.id.toString())

                                this.startActivity(intent)
                            }
                            afound = true
                            break
                        }
                    }
                }
            }

    }
}