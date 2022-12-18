package com.example.a2paths

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a2paths.databinding.ActivityConsultBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ConsultActivity : AppCompatActivity() {

    private var mBinding: ActivityConsultBinding? = null
    private val binding get() = mBinding!!

    val firebase = Firebase.firestore
    private val user = Firebase.auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityConsultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var profName = "null"
        profName = intent.getStringExtra("prof").toString()

        if (profName != "null") {
            binding.tvProf.text = intent.getStringExtra("prof")
            binding.tvProf.setTextColor(Color.parseColor("#FF000000"))
            binding.layoutProf.isEnabled = false
            binding.ivDown1.visibility = View.GONE
        }

        binding.layoutProf.setOnClickListener {
            binding.layout02.visibility = View.GONE
            binding.layout03.visibility = View.GONE
            binding.layout04.visibility = View.GONE
            if (binding.layout01.visibility == View.VISIBLE) {
                binding.layout01.visibility = View.GONE
            } else {
                binding.layout01.visibility = View.VISIBLE
            }
        }

        binding.layoutGroup.setOnClickListener {
            binding.layout01.visibility = View.GONE
            binding.layout03.visibility = View.GONE
            binding.layout04.visibility = View.GONE
            if (binding.layout02.visibility == View.VISIBLE) {
                binding.layout02.visibility = View.GONE
            } else {
                binding.layout02.visibility = View.VISIBLE
            }
        }

        binding.layoutDay.setOnClickListener {
            binding.layout01.visibility = View.GONE
            binding.layout02.visibility = View.GONE
            binding.layout04.visibility = View.GONE
            if (binding.layout03.visibility == View.VISIBLE) {
                binding.layout03.visibility = View.GONE
            } else {
                binding.layout03.visibility = View.VISIBLE
            }
        }

        binding.layoutTime.setOnClickListener {
            binding.layout01.visibility = View.GONE
            binding.layout02.visibility = View.GONE
            binding.layout03.visibility = View.GONE
            if (binding.layout04.visibility == View.VISIBLE) {
                binding.layout04.visibility = View.GONE
            } else {
                binding.layout04.visibility = View.VISIBLE
            }
        }

        choiceProf()

        choiceGroup()

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val day = "${year}년 ${month + 1}월 ${dayOfMonth}일"
            binding.tvCalender.text = day
            binding.tvCalender.setTextColor(Color.parseColor("#FF000000"))
            binding.layout03.visibility = View.GONE
            binding.layout04.visibility = View.VISIBLE
        }

        binding.timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            if (hourOfDay >= 12) {
                val time = "오후 ${hourOfDay % 12}시 ${minute}분"
                binding.tvTime.text = time
                binding.tvTime.setTextColor(Color.parseColor("#FF000000"))
            } else {
                val time = "오전 ${hourOfDay}시 ${minute}분"
                binding.tvTime.text = time
                binding.tvTime.setTextColor(Color.parseColor("#FF000000"))
            }
        }

        binding.btnCompletion.setOnClickListener {
            val prof = binding.tvProf.text.toString()
            val group = binding.tvGroup.text.toString()
            val detail = binding.etDetail.text.toString()
            val day = binding.tvCalender.text.toString()
            val time = binding.tvTime.text.toString()
            val state = "대기중"

            firebase.collection("user").document(user?.email.toString())
                .get()
                .addOnSuccessListener { document ->
                    val name = document["name"].toString()
                    val id = name + day + time

                    val data = hashMapOf(
                        "name" to name,
                        "email" to user?.email.toString(),
                        "prof" to prof,
                        "group" to group,
                        "detail" to detail,
                        "day" to day,
                        "time" to time,
                        "state" to state,
                    )

                    firebase.collection("consult").document(id).set(data)
                }

            Toast.makeText(this, "상담 신청되었습니다", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun choiceProf() {
        binding.btn1.setOnClickListener {
            binding.tvProf.text = binding.btn1.text
            binding.tvProf.setTextColor(Color.parseColor("#FF000000"))
            binding.layout01.visibility = View.GONE
            binding.layout02.visibility = View.VISIBLE
        }
        binding.btn2.setOnClickListener {
            binding.tvProf.text = binding.btn2.text
            binding.tvProf.setTextColor(Color.parseColor("#FF000000"))
            binding.layout01.visibility = View.GONE
            binding.layout02.visibility = View.VISIBLE
        }
        binding.btn3.setOnClickListener {
            binding.tvProf.text = binding.btn3.text
            binding.tvProf.setTextColor(Color.parseColor("#FF000000"))
            binding.layout01.visibility = View.GONE
            binding.layout02.visibility = View.VISIBLE
        }
        binding.btn4.setOnClickListener {
            binding.tvProf.text = binding.btn4.text
            binding.tvProf.setTextColor(Color.parseColor("#FF000000"))
            binding.layout01.visibility = View.GONE
            binding.layout02.visibility = View.VISIBLE
        }
        binding.btn5.setOnClickListener {
            binding.tvProf.text = binding.btn5.text
            binding.tvProf.setTextColor(Color.parseColor("#FF000000"))
            binding.layout01.visibility = View.GONE
            binding.layout02.visibility = View.VISIBLE
        }
        binding.btn6.setOnClickListener {
            binding.tvProf.text = binding.btn6.text
            binding.tvProf.setTextColor(Color.parseColor("#FF000000"))
            binding.layout01.visibility = View.GONE
            binding.layout02.visibility = View.VISIBLE
        }
        binding.btn7.setOnClickListener {
            binding.tvProf.text = binding.btn7.text
            binding.tvProf.setTextColor(Color.parseColor("#FF000000"))
            binding.layout01.visibility = View.GONE
            binding.layout02.visibility = View.VISIBLE
        }
        binding.btn8.setOnClickListener {
            binding.tvProf.text = binding.btn8.text
            binding.tvProf.setTextColor(Color.parseColor("#FF000000"))
            binding.layout01.visibility = View.GONE
            binding.layout02.visibility = View.VISIBLE
        }
        binding.btn9.setOnClickListener {
            binding.tvProf.text = binding.btn9.text
            binding.tvProf.setTextColor(Color.parseColor("#FF000000"))
            binding.layout01.visibility = View.GONE
            binding.layout02.visibility = View.VISIBLE
        }
        binding.btn10.setOnClickListener {
            binding.tvProf.text = binding.btn10.text
            binding.tvProf.setTextColor(Color.parseColor("#FF000000"))
            binding.layout01.visibility = View.GONE
            binding.layout02.visibility = View.VISIBLE
        }
        binding.btn11.setOnClickListener {
            binding.tvProf.text = binding.btn11.text
            binding.tvProf.setTextColor(Color.parseColor("#FF000000"))
            binding.layout01.visibility = View.GONE
            binding.layout02.visibility = View.VISIBLE
        }
        binding.btn12.setOnClickListener {
            binding.tvProf.text = binding.btn12.text
            binding.tvProf.setTextColor(Color.parseColor("#FF000000"))
            binding.layout01.visibility = View.GONE
            binding.layout02.visibility = View.VISIBLE
        }
        binding.btn13.setOnClickListener {
            binding.tvProf.text = binding.btn13.text
            binding.tvProf.setTextColor(Color.parseColor("#FF000000"))
            binding.layout01.visibility = View.GONE
            binding.layout02.visibility = View.VISIBLE
        }
        binding.btn14.setOnClickListener {
            binding.tvProf.text = binding.btn14.text
            binding.tvProf.setTextColor(Color.parseColor("#FF000000"))
            binding.layout01.visibility = View.GONE
            binding.layout02.visibility = View.VISIBLE
        }
        binding.btn15.setOnClickListener {
            binding.tvProf.text = binding.btn15.text
            binding.tvProf.setTextColor(Color.parseColor("#FF000000"))
            binding.layout01.visibility = View.GONE
            binding.layout02.visibility = View.VISIBLE
        }
        binding.btn16.setOnClickListener {
            binding.tvProf.text = binding.btn16.text
            binding.tvProf.setTextColor(Color.parseColor("#FF000000"))
            binding.layout01.visibility = View.GONE
            binding.layout02.visibility = View.VISIBLE
        }
        binding.btn17.setOnClickListener {
            binding.tvProf.text = binding.btn17.text
            binding.tvProf.setTextColor(Color.parseColor("#FF000000"))
            binding.layout01.visibility = View.GONE
            binding.layout02.visibility = View.VISIBLE
        }
        binding.btn18.setOnClickListener {
            binding.tvProf.text = binding.btn18.text
            binding.tvProf.setTextColor(Color.parseColor("#FF000000"))
            binding.layout01.visibility = View.GONE
            binding.layout02.visibility = View.VISIBLE
        }
    }

    private fun choiceGroup() {
        binding.checkBox1.setOnClickListener {
            binding.checkBox2.isChecked = false
            binding.checkBox3.isChecked = false
            binding.tvGroup.text = binding.checkBox1.text
            binding.tvGroup.setTextColor(Color.parseColor("#FF000000"))
        }

        binding.checkBox2.setOnClickListener {
            binding.checkBox1.isChecked = false
            binding.checkBox3.isChecked = false
            binding.tvGroup.text = binding.checkBox2.text
            binding.tvGroup.setTextColor(Color.parseColor("#FF000000"))
        }

        binding.checkBox3.setOnClickListener {
            binding.checkBox1.isChecked = false
            binding.checkBox2.isChecked = false
            binding.tvGroup.text = binding.checkBox3.text
            binding.tvGroup.setTextColor(Color.parseColor("#FF000000"))
        }
    }
}