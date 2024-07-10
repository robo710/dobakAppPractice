package com.sonchan.dobakapppractice.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sonchan.dobakapppractice.data.UserData

class MainViewModel(val userData: UserData?) : ViewModel() {

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

    fun dobakValue(input: Long) {
        val db = Firebase.firestore
        if (_leftMoney.value!! < input) {
            _showLackAlert.value = true  // LackAlert를 표시하기 위한 상태 변경
        } else {
            if(userData?.username != null) {
                _leftMoney.value = (_leftMoney.value ?: 0) - input

                val data = hashMapOf(
                    "money" to _leftMoney.value
                )

                db.collection("user").document(userData.userId)
                    .set(data)
                    .addOnSuccessListener {
                        println("DocumentSnapshot successfully written!")
                    }
                    .addOnFailureListener { e ->
                        println("Error writing document $e")
                    }
            }
        }
    }

    fun dismissLackAlert() {
        _showLackAlert.value = false  // LackAlert를 닫기 위한 상태 변경
    }
}
