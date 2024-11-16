package com.example.coderooms.retrofit

import com.example.coderooms.model.leetcode.ContestDetails
import com.example.coderooms.model.leetcode.ContestRanking
import com.example.coderooms.model.leetcode.LeetCodeStats
import com.example.coderooms.model.leetcode.UserProfile

class LeetCodeRepository(private val api: LeetCodeApi) {
    suspend fun fetchUserProfile(username: String): UserProfile? {
        return try {
            api.userProfile(username)
        } catch (e: Exception) {
            e.printStackTrace()
            null // Handle the error or return a default value
        }
    }

    suspend fun fetchUserRating(username: String): ContestDetails?{
        return try{
            api.getUserContest(username)
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }
}
