package com.su.purple.usvisapreparation.helper

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.su.purple.usvisapreparation.helper.PreferenceHelper.popularQuestion
import com.su.purple.usvisapreparation.model.PopularQuestion
import com.su.purple.usvisapreparation.util.Constants

object AppConfig {

    var mPrefs: SharedPreferences? = null

    fun setPreferences(context: Context) {
        mPrefs = PreferenceHelper?.getSharedPreference(context, Constants.TAG)
    }

    fun savePopularQuestion(question: PopularQuestion) {
        val editor = mPrefs?.edit()
        editor?.putString(question.title, question.saved_answer)
        editor?.apply()
    }

    fun getSavedAnswer(title: String): String {
        return mPrefs?.getString(title, "")?: ""
    }
}