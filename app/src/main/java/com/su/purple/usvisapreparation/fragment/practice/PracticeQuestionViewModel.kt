package com.su.purple.usvisapreparation.fragment.practice

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.su.purple.usvisapreparation.model.PopularQuestion
import com.su.purple.usvisapreparation.util.Constants
import com.su.purple.usvisapreparation.util.firebase.FirebaseUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PracticeQuestionViewModel: ViewModel() {
    val questionsList = MutableLiveData<List<PopularQuestion>>()

    fun getFireStoreData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                var list = arrayListOf<PopularQuestion>()
                var question = PopularQuestion()
                FirebaseUtil().fireStoreDatabase
                    .collection(Constants.POPULAR_QUESTION_COLLECTION)
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        querySnapshot.forEach { document ->
                            question = document.toObject(PopularQuestion::class.java)
                            question.id = document.id.toInt()
                            list.add(question)
                        }

                        viewModelScope.launch {
                            withContext(Dispatchers.Main) {
                                list.sortBy { x -> x.id }
                                questionsList.value = list
                            }
                        }

                    }
                    .addOnFailureListener { e ->
                        Log.e(Constants.FIRESTORE_TAG, "Error reading document", e)
                    }
            }
        }
    }
}