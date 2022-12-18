package com.example.a2paths

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.a2paths.databinding.FragmentSettingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SettingFragment : Fragment() {
    private var mBinding: FragmentSettingBinding? = null
    private val binding get() = mBinding!!
    private val user = Firebase.auth.currentUser

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentSettingBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        binding.btnLogout.setOnClickListener{
            auth.signOut()
            val intent = Intent(context, LoginActivity::class.java)
            Toast.makeText(context, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }

        binding.btnWithdraw.setOnClickListener {
            auth.currentUser?.delete()
            Firebase.firestore.collection("user").document(user?.email.toString()).delete()
                .addOnSuccessListener {
                    Toast.makeText(context, "회원 탈퇴 되었습니다.", Toast.LENGTH_SHORT).show()
                }
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }
        return binding.root


    }


}
/*
lateinit var prefs:SharedPreferences
private val KEY_THEME = "theme"
private val kTheme : ListPreference? = null



override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.fragment_setting, container, false)
    return view
}


override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
    setPreferencesFromResource(R.xml.preference,rootKey)
}

val prefListner =
    SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences:SharedPreferences?, key:String ->
        when(key){
            "theme_list" ->{
                val summary = prefs.getString("keyword_theme_list","")
            }
        }
    }
}
*/
