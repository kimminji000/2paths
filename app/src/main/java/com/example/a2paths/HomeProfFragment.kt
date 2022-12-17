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
    private val user = Firebase.auth.currentUser


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mfBinding = FragmentHomeProfBinding.inflate(inflater, container, false)

        getProfMyProfile()

        binding.btnProf.setOnClickListener {
            val transaction = childFragmentManager.beginTransaction()
            transaction.replace(R.id.home_prof_frame, ProfListFragment()).commit()
        }

        binding.btnStu.setOnClickListener {
            val transaction = childFragmentManager.beginTransaction()
            transaction.replace(R.id.home_prof_frame, StuListFragment()).commit()
        }

        return binding.root
    }

    private fun getProfMyProfile() { //교수진 내 프로필 가져오기
        firebase.collection("prof")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (i in task.result!!) {
                        if (i.data["id"] == user?.email) {
                            binding.tvName.text = i.data["name"].toString()
                            binding.tvOffice.text = i.data["office"].toString()
                            binding.tvMajor.text = i.data["major"].toString()
                            binding.tvLink.text = i.data["link"].toString()
                        }
                    }
                }
            }
    }

    override fun onDestroyView() {
        mfBinding = null
        super.onDestroyView()
    }
}