package com.example.coderooms

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coderooms.chat.ChatHelper
import com.example.coderooms.ui.navigation.AccountNavigation
import com.example.coderooms.ui.screens.LeetCodeStatsScreen
import com.example.coderooms.ui.screens.UserListScreen
import com.example.coderooms.ui.screens.UserProfileScreen
import com.example.coderooms.viewmodel.LeetCodeStatsViewModel
import com.example.coderooms.viewmodel.UserProfileViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {
    private val userViewModel: UserProfileViewModel by viewModels()
    private val leetCodeViewModel:LeetCodeStatsViewModel by viewModels()
    private lateinit var auth: FirebaseAuth
    private lateinit var chatHelper: ChatHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
        AccountNavigation()
//        LeetCodeProfileScreen(username = "vir_s_ingh")
//        UserProfileScreen(userViewModel, "raghavvbagdi")
        }
    }
}