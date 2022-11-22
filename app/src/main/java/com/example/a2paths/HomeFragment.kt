package com.example.a2paths

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.a2paths.databinding.FragmentHomeBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private var mBinding: FragmentHomeBinding? = null
    private val binding get() = mBinding!!

    val firebase = Firebase.firestore
    private val user = Firebase.auth.currentUser

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {

        mBinding = FragmentHomeBinding.inflate(inflater, container, false)

        getMyProfile()

        binding.constraintLayout2.setOnClickListener {
            val intent = Intent(activity, MyProfileActivity::class.java)
            startActivity(intent)
        }

        binding.btnProf.setOnClickListener {
            val transaction = childFragmentManager.beginTransaction()
            transaction.replace(R.id.home_frame, ProfListFragment()).commit()
        }

        binding.btnStu.setOnClickListener {
            val transaction = childFragmentManager.beginTransaction()
            transaction.replace(R.id.home_frame, StuListFragment()).commit()
        }

        return binding.root
    }

    private fun getMyProfile() {
        firebase.collection("user").document(user?.email.toString())
            .get()
            .addOnSuccessListener { document ->
                binding.tvName.text = document["name"].toString()
                binding.tvGrade.text = document["grade"].toString()
                binding.tvNumber.text = document["number"].toString()
                binding.tvState.text = document["state"].toString()

                var field = ""
                if (document["flight"].toString() == "true" && document["software"].toString() == "true") {
                    field += "항공우주 소프트웨어"
                } else {
                    if (document["flight"].toString() == "true") {
                        field += "항공우주"
                    }
                    if (document["software"].toString() == "true") {
                        field += "소프트웨어"
                    }
                }
                binding.tvField.text = field
            }
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}