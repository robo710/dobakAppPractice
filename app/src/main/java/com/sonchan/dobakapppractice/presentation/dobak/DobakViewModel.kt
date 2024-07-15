package com.sonchan.dobakapppractice.presentation.dobak

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sonchan.dobakapppractice.data.UserData
import kotlin.random.Random

class DobakViewModel(val userData: UserData?) : ViewModel() {
    val db = Firebase.firestore

    private var _leftMoney = MutableLiveData<Long>()
    private var _showLackAlert = MutableLiveData<Boolean>()
    private var _showSuccessAlert = MutableLiveData<Boolean>()
    private var _showFailAlert = MutableLiveData<Boolean>()
    private var _showLegendAlert = MutableLiveData<Boolean>()

    val leftMoney: LiveData<Long>
        get() = _leftMoney

    val showLackAlert: LiveData<Boolean>
        get() = _showLackAlert

    val showSuccessAlert: LiveData<Boolean>
        get() = _showSuccessAlert

    val showFailAlert: LiveData<Boolean>
        get() = _showFailAlert

    val showLegendAlert: LiveData<Boolean>
        get() = _showLegendAlert

    init {
        _leftMoney.value = (_leftMoney.value ?: 0)
        _showLackAlert.value = false
        _showSuccessAlert.value = false
        _showFailAlert.value = false
        _showLegendAlert.value = false
        loadUserData()
    }

    private fun loadUserData(){
        if(userData?.username != null) {
            db.collection("user").document(userData.userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        if (document.exists()) {
                            val data = document.data // 문서의 데이터를 Map 형태로 가져옵니다
                            val money = data?.get("money") as Long?
                            money?.let {
                                _leftMoney.value = it
                            }
                            Log.d("로그", "DocumentSnapshot data: $data")
                            Log.d("로그", "DocumentSnapshot money: $money")
                            Log.d("로그", "DocumentSnapshot _leftMoney: ${_leftMoney.value}")
                            println("DocumentSnapshot data: $data")
                        } else {
                            println("No such document")
                        }
                    } else {
                        println("Document does not exist")
                    }
                }
                .addOnFailureListener { e ->
                    println("Error getting document: $e")
                }
        }
    }

    fun dobak(input: Long) {
        loadUserData()
        val random = Random.nextInt(101)
        var success: Long = 0

        if (_leftMoney.value!! < input) {
            _showLackAlert.value = true  // LackAlert를 표시하기 위한 상태 변경
        } else {
            if (random < 50) {
                _showSuccessAlert.value = true
                success = input * 2
            } else if (random in 50..99) {
                _showFailAlert.value = true
            } else if (random == 100) {
                _showLegendAlert.value = true
                success = input * 10
                Log.d("랜덤확인", "DobakViewModel - random : ${random}")
            }
            if (userData?.username != null) {
                _leftMoney.value = (_leftMoney.value ?: 0) - input + success

                val data = hashMapOf(
                    "money" to _leftMoney.value,
                    "username" to userData.username
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

    fun addMoney() {
        loadUserData()
        _leftMoney.value = (_leftMoney.value ?: 0) + 10000

        if(userData?.username != null) {
            val data = hashMapOf(
                "money" to _leftMoney.value,
                "username" to userData.username
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

    fun dismissAlert() { // Alert를 닫기위한 상태변경
        _showLackAlert.value = false
        _showSuccessAlert.value = false
        _showFailAlert.value = false
        _showLegendAlert.value = false
    }
}
