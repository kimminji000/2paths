package com.example.a2paths

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a2paths.databinding.FragmentProfListBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfListFragment : Fragment() {
    private var mBinding: FragmentProfListBinding? = null
    private val binding get() = mBinding!!

    val firebase = Firebase.firestore
    val itemList = arrayListOf<ProfProfiles>()
    val adapter = ProfProfileAdapter(itemList)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = FragmentProfListBinding.inflate(inflater, container, false)
        firebase.collection("prof")   // 작업할 컬렉션
            .get()      // 문서 가져오기
            .addOnSuccessListener { result ->
                // 성공할 경우
                itemList.clear()
                for (document in result) {  // 가져온 문서들은 result에 들어감
                    val item = ProfProfiles(document["name"] as String, document["office"] as String, document["major"] as String)
                    itemList.add(item)
                }
                adapter.notifyDataSetChanged()  // 리사이클러 뷰 갱신
            }
            .addOnFailureListener { exception ->
                // 실패할 경우
                Log.w("ProfListFragment", "Error getting documents: $exception")
            }

        binding.rvProfprofile.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProfprofile.setHasFixedSize(true)
        binding.rvProfprofile.adapter = adapter

        return binding.root
    }
}