package com.deletech.malakoff.storage

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager ( context:Context){
    var pref: SharedPreferences
    var editor: SharedPreferences.Editor
    var PRIVATE_MODE = 0
    companion object {
        private val ACCESS_TOKEN = "ACCESS_TOKEN"
        private val PREF_NAME = "malakoff_preferences"
    }
    init {
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }
    fun clearUser() {
        editor.clear()
        editor.commit()
    }
    fun setAccessToken(accessToken:String){
        editor.putString(ACCESS_TOKEN,accessToken)
        editor.commit()
    }

    fun getAccessToken(): String? {
        return  pref.getString(ACCESS_TOKEN,"")

    }

}
