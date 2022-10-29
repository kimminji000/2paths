package com.example.a2paths

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.a2paths.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var mBinding: FragmentHomeBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //val view = inflater.inflate(R.layout.fragment_home, container, false) //기존 프레그먼트연결
        mBinding = FragmentHomeBinding.inflate(inflater, container, false) //바인딩사용


        binding.btnProf.setOnClickListener {
            setFrag(0)
        }

        binding.btnStu.setOnClickListener {
            setFrag(1)
        }

        return binding.root
        //return view
    }

    private fun setFrag(fragNum : Int) {
        val ft = childFragmentManager.beginTransaction()
        when(fragNum){
            0 -> {
                ft.replace(R.id.home_frame, ProfListFragment()).commit()
            }
            1 -> {
                ft.replace(R.id.home_frame, StuListFragment()).commit()
            }
        }

    }

    override fun onDestroyView() {
            // onDestroyView 에서 binding class 인스턴스 참조를 정리해주어야 한다.
            mBinding = null
            super.onDestroyView()
        }
}