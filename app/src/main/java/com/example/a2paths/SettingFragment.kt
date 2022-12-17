package com.example.a2paths

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.work.impl.model.Preference

class SettingFragment : PreferenceFragmentCompat() {

    lateinit var prefs:SharedPreferences
    private val KEY_THEME = "theme"
    private val kTheme : ListPreference? = null


    /*
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        return view
    }*/

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