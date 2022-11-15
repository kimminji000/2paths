package com.example.a2paths

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a2paths.databinding.FragmentStuListBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class StuListFragment :Fragment() {
    private var mBinding: FragmentStuListBinding? = null
    private val binding get() = mBinding!!

    val firebase = Firebase.firestore
    val itemList = arrayListOf<StuProfiles>()
    val adapter = StuProfileAdapter(itemList)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = FragmentStuListBinding.inflate(inflater, container, false)
        firebase.collection("user")   // 작업할 컬렉션
            .get()      // 문서 가져오기
            .addOnSuccessListener { result ->
                // 성공할 경우
                itemList.clear()
                for (document in result) {  // 가져온 문서들은 result에 들어감
                    val item = StuProfiles(document["name"] as String, document["stunum"] as String, document["grade"] as String, document["state"] as String)
                    itemList.add(item)
                }
                adapter.notifyDataSetChanged()  // 리사이클러 뷰 갱신
            }
            .addOnFailureListener { exception ->
                // 실패할 경우
                Log.w("StuListFragment", "Error getting documents: $exception")
            }

        binding.rvStuprofile.layoutManager = LinearLayoutManager(requireContext())
        binding.rvStuprofile.setHasFixedSize(true)
        binding.rvStuprofile.adapter = adapter
        binding.svStulist.setOnQueryTextListener(searchViewTextListener)
        return binding.root
    }

    var TAG = "StuListFragment"

    var searchViewTextListener : SearchView.OnQueryTextListener =
        object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.getFilter().filter(newText)
                Log.d(TAG,"SearchView Text is changed:$newText")
                return false
            }
        }
}