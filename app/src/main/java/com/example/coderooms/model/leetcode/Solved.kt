package com.example.coderooms.model.leetcode

data class Solved(
    val acSubmissionNum: List<AcSubmissionNum>,
    val easySolved: Int,
    val hardSolved: Int,
    val mediumSolved: Int,
    val solvedProblem: Int,
    val totalSubmissionNum: List<TotalSubmission>
)