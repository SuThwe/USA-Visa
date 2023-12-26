package com.su.purple.usvisapreparation.fragment.popularquestions.explanation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.su.purple.usvisapreparation.helper.AppConfig
import com.su.purple.usvisapreparation.model.PopularQuestion
import com.su.purple.usvisapreparation.util.Constants
import com.su.purple.usvisapreparation.util.firebase.FirebaseUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExplanationQuestionViewModel: ViewModel() {
    val savedAnswer = MutableLiveData<Boolean>()

    fun saveUserAnswer(question: PopularQuestion) {
        AppConfig.savePopularQuestion(question)
        savedAnswer.value = true
    }

    fun getSavedAnswer(title: String): String {
        return AppConfig.getSavedAnswer(title)
    }
}