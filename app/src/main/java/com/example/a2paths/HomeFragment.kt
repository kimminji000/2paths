package com.example.a2paths

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.a2paths.databinding.FragmentHomeBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.ByteArrayInputStream
import kotlin.experimental.or

class HomeFragment : Fragment() {

    private var mBinding: FragmentHomeBinding? = null
    private val binding get() = mBinding!!

    val firebase = Firebase.firestore
    private val user = Firebase.auth.currentUser

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        mBinding = FragmentHomeBinding.inflate(inflater, container, false)

        getMyProfile()
        //imageDownload()

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

    private fun getMyProfile() { //내 프로필 가져오기
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
    /*
    private fun imageDownload() {

        firebase.collection("user").document(user?.email.toString())
            .get()
            .addOnSuccessListener { document ->
                val image : String = document["image"].toString()
                val b: ByteArray = binaryStringToByteArray(image)
                val stream = ByteArrayInputStream(b)
                val downImage = Drawable.createFromStream(stream, "image")

                binding.ivProfile.setImageDrawable(downImage)
                }



        var databaseRef : DatabaseReference =
            FirebaseDatabase.getInstance().getReference("/images")

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot: DataSnapshot in snapshot.children){
                    val image: String = dataSnapshot.getValue().toString()

                    val b: ByteArray = binaryStringToByteArray(image)
                    val stream = ByteArrayInputStream(b)
                    val down_image = Drawable.createFromStream(stream,"image")
                    binding.ivProfile.setImageDrawable(down_image)
                }
            }
        })
    }*/
    /*
    private fun binaryStringToByteArray(s : String)  :ByteArray {
        val count : Int = s.length / 8
        val b = ByteArray(count)

        for(i in 1 until count) {
            val t = s.substring((i-1)*8, i*8)
            b[i-1] = binaryStringToByte(t)
        }
        return b
    }

    private fun binaryStringToByte(s:String) : Byte {
        var ret : Byte
        var total  : Byte = 0

        for(i in 0 until 8) {
            ret = if(s.get(7-i)=='1')
                (1.shl(i)).toByte()
            else 0
            total = (ret.or(total))
        }
        return total
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }*/
}