package com.sonchan.dobakapppractice.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainViewModel : ViewModel() {
    private var _leftMoney = MutableLiveData<Long>()
    private var _showLackAlert = MutableLiveData<Boolean>()

    val leftMoney: LiveData<Long>
        get() = _leftMoney

    val showLackAlert: LiveData<Boolean>
        get() = _showLackAlert

    init {
        _leftMoney.value = 0
        _showLackAlert.value = false
    }

    fun updateValue(input: Long) {
        val db = Firebase.firestore
        if (_leftMoney.value!! < input) {
            _showLackAlert.value = true  // LackAlert를 표시하기 위한 상태 변경
        } else {
            _leftMoney.value = (_leftMoney.value ?: 0) - input

            val data = hashMapOf(
                "money" to _leftMoney.value
            )

            db.collection("messages")
                .add(data)
                .addOnSuccessListener { documentReference ->
                    println("DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    println("Error adding document $e")
                }
        }
    }

    fun dismissLackAlert() {
        _showLackAlert.value = false  // LackAlert를 닫기 위한 상태 변경
    }
}
