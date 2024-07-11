package com.sonchan.dobakapppractice.presentation.dobak

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sonchan.dobakapppractice.data.RunningState
import com.sonchan.dobakapppractice.data.UserData
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DobakViewModel(val userData: UserData?) : ViewModel() {
    val db = Firebase.firestore

    private var _leftMoney = MutableLiveData<Long>()
    private var _showLackAlert = MutableLiveData<Boolean>()

    val leftMoney: LiveData<Long>
        get() = _leftMoney

    val showLackAlert: LiveData<Boolean>
        get() = _showLackAlert

    private var job: Job? = null

    private var _isRunning by mutableStateOf(RunningState.STOPPED)

    private var _minute by mutableStateOf(0)
    var minute: Int
        get() = _minute
        set(value) {
            _minute = value
        }

    private var _second by mutableStateOf(0)
    var second: Int
        get() = _second
        set(value) {
            _second = value
        }

    val leftTime = mutableStateOf(0)

    fun getTotalTimeInSeconds(): Int {
        return (minute * 60 + second)
    }

    private fun decreaseSecond(): Flow<Int> = flow {
        var i = leftTime.value
        while (i > 0) {
            delay(1000)
            emit(--i)
        }
    }

    fun startCountDownTimer() {
        leftTime.value = 1800
        if (leftTime.value <= 0) return
        _isRunning = RunningState.STARTED
        job = decreaseSecond().onEach {
            leftTime.value = it
        }.launchIn(viewModelScope)
    }


    init {
        _leftMoney.value = 0
        _showLackAlert.value = false
        dobakMoney()
    }

    fun dobakMoney(){
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

    fun dobakValue(input: Long) {
        dobakMoney()
        if (_leftMoney.value!! < input) {
            _showLackAlert.value = true  // LackAlert를 표시하기 위한 상태 변경
        } else {
            if(userData?.username != null) {
                _leftMoney.value = (_leftMoney.value ?: 0) - input

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
        dobakMoney()
        _leftMoney.value = (_leftMoney.value ?: 0) + 10000

        val data = hashMapOf(
            "money" to _leftMoney.value
        )

        if(userData?.username != null) {
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

    fun dismissLackAlert() {
        _showLackAlert.value = false  // LackAlert를 닫기 위한 상태 변경
    }
}
