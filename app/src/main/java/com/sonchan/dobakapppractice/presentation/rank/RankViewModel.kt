package com.sonchan.dobakapppractice.presentation.rank

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.sonchan.dobakapppractice.data.UserRanking

class RankViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _rankings = MutableLiveData<List<UserRanking>>()
    val rankings: LiveData<List<UserRanking>>
        get() = _rankings

    init {
        updateRankings()
    }

    fun updateRankings() {
        // Firestore에서 사용자들의 점수를 내림차순으로 정렬하여 가져오는 쿼리
        db.collection("user")
            .orderBy("money", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val rankings = mutableListOf<UserRanking>()
                var rank = 1

                for (document in querySnapshot.documents) {
                    val username = document.getString("username")
                    val money = document.getLong("money")
                    Log.d("로그", " username: $username")
                    Log.d("로그", " money: $money")

                    if (username != null && money != null) {
                        val userRanking = UserRanking(rank, username, money)
                        rankings.add(userRanking)
                        rank++
                    }
                }

                _rankings.value = rankings // LiveData에 데이터 업데이트
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error getting documents.", e)
            }
    }

    companion object {
        private const val TAG = "RankViewModel"
    }
}