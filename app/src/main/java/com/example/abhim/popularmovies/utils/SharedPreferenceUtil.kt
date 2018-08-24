package com.example.abhim.popularmovies.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class SharedPreferenceUtil {

    companion object {

        fun getSharedPrefsInstance(context: Context?): SharedPreferences {

            val mSettings = PreferenceManager.getDefaultSharedPreferences(context)
            val mEditor = mSettings.edit()
            mEditor.apply()

            return mSettings
        }
    }
}