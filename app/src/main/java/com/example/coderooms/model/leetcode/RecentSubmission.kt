package com.example.coderooms.model.leetcode

data class RecentSubmission(
    val __typename: String,
    val lang: String,
    val statusDisplay: String,
    val timestamp: String,
    val title: String,
    val titleSlug: String
)