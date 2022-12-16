package com.example.a2paths

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a2paths.databinding.FragmentHomeProfBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class HomeProfFragment : Fragment() {

    private var mfBinding: FragmentHomeProfBinding? = null
    private val binding get() = mfBinding!!

    val firebase = Firebase.firestore
    private val prof = Firebase.auth.currentUser


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mfBinding = FragmentHomeProfBinding.inflate(inflater, container, false)

        getProfMyProfile()

        binding.btnProf2.setOnClickListener {
            val transaction = childFragmentManager.beginTransaction()
            transaction.replace(R.id.home_prof_frame, ProfListFragment()).commit()
        }

        binding.btnStu2.setOnClickListener {
            val transaction = childFragmentManager.beginTransaction()
            transaction.replace(R.id.home_prof_frame, StuListFragment()).commit()
        }
        return binding.root
    }

    private fun getProfMyProfile() { //교수진 내 프로필 가져오기
        firebase.collection("prof").document(prof?.email.toString())
            .get()
            .addOnSuccessListener { document ->
                binding.tvName2.text = document["name"].toString()
                binding.tvOffice.text = document["office"].toString()
                binding.tvMajor.text = document["major"].toString()
                binding.tvLink.text = document["link"].toString()
            }
    }

    override fun onDestroyView() {
        mfBinding = null
        super.onDestroyView()
    }
}