package com.example.a2paths

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a2paths.databinding.FragmentStuListBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class StuListFragment : Fragment() {

    private var mBinding: FragmentStuListBinding? = null
    private val binding get() = mBinding!!

    val firebase = Firebase.firestore
    private val user = Firebase.auth.currentUser
    private val itemList = arrayListOf<StuProfiles>()
    private val adapter = StuProfileAdapter(itemList) //학생 리스트를 보여주기 위한 어댑터
    private val filterAdapter = StuFilterAdapter(itemList)  //서치뷰 사용하는 경우의 필터 어댑터

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = FragmentStuListBinding.inflate(inflater, container, false)
        // DB에서 학생 데이터 불러오기
        firebase.collection("user")
            .get()
            .addOnSuccessListener { result ->
                itemList.clear()
                for (document in result) {
                    if (document["id"] == user?.email) continue
                    val item = StuProfiles(
                        document["name"] as String,
                        document["number"] as String,
                        document["grade"] as String,
                        document["state"] as String
                    )
                    itemList.add(item)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w("StuListFragment", "Error getting documents: $exception")
            }

        binding.rvStuprofile.layoutManager = LinearLayoutManager(requireContext())
        binding.rvStuprofile.setHasFixedSize(true)
        binding.rvStuprofile.adapter = adapter //학생 리스트 어댑터 연결
        binding.svStulist.setOnQueryTextListener(searchViewTextListener)  //서치뷰 리스너 호출

        return binding.root
    }

    var tagStuList = "StuListFragment"

    //서치뷰 리스너(검색 필터)
    var searchViewTextListener: SearchView.OnQueryTextListener =
        object : SearchView.OnQueryTextListener {
            //검색버튼을 누를 때 호출(검색 버튼이 없으니 사용하지 않음)
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            // 서치뷰에 문자를 입력하거나 수정할 때 호출
            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText != null){
                    binding.rvStuprofile.adapter = filterAdapter   //필터 어댑터 연결
                    filterAdapter.getFilter().filter(newText)
                    Log.d(tagStuList, "SearchView Text is changed:$newText")
                }
                return false
            }
        }
}