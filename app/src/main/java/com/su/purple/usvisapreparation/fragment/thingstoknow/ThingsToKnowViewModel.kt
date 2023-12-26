package com.su.purple.usvisapreparation.fragment.thingstoknow

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.su.purple.usvisapreparation.model.ThingsToKnow
import com.su.purple.usvisapreparation.util.Constants
import com.su.purple.usvisapreparation.util.firebase.FirebaseUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ThingsToKnowViewModel: ViewModel() {
    val thingsToKnowList = MutableLiveData<List<ThingsToKnow>>()

    fun getFireStoreData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                var list = arrayListOf<ThingsToKnow>()
                var thingsToKnow = ThingsToKnow()
                FirebaseUtil().fireStoreDatabase
                    .collection(Constants.THINGS_TO_KNOW_COLLECTION)
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        querySnapshot.forEach { document ->
                            thingsToKnow = document.toObject(ThingsToKnow::class.java)
                            thingsToKnow.id = document.id.toInt()
                            list.add(thingsToKnow)
                        }

                        viewModelScope.launch {
                            withContext(Dispatchers.Main) {
                                thingsToKnowList.value = list
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