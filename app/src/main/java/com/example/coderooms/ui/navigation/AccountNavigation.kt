package com.example.coderooms.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.coderooms.chat.ChatHelper
import com.example.coderooms.model.User
import com.example.coderooms.model.leetcode.groups
import com.example.coderooms.ui.screens.ChatScreen
import com.example.coderooms.ui.screens.GroupChatScreen
import com.example.coderooms.ui.screens.LeetCodeStatsScreen
import com.example.coderooms.ui.screens.LoginScreen
import com.example.coderooms.ui.screens.SignUpScreen
import com.example.coderooms.ui.screens.UserListScreen
import com.example.coderooms.ui.screens.UserProfileScreen
import com.example.coderooms.viewmodel.LeetCodeStatsViewModel
import com.example.coderooms.viewmodel.UserProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

@Composable
fun AccountNavigation() {
    val navController = rememberNavController()
    val db = FirebaseDatabase.getInstance()
    val userViewModel = UserProfileViewModel()
    val leetcodeViewModel = LeetCodeStatsViewModel()
    val userProfileViewModel = UserProfileViewModel()

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.SignUp.route) { SignUpScreen(navController) }
        composable(
            route = Screen.UserProfile.route,
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username")
            if (username != null) {
                UserProfileScreen(userViewModel, username)
            } else {
                Log.e("UserProfileScreen", "Username is null")
                // Handle error gracefully, maybe navigate back or show a placeholder
            }
        }
        composable(
            route = Screen.LeetCodeStats.route,
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val username = navBackStackEntry.arguments?.getString("username")
            if (username != null) {
                LeetCodeStatsScreen(viewModel = leetcodeViewModel, username = username, viewmodel = userProfileViewModel)
            } else {
                Log.e("LeetCodeStatsScreen", "Username is null")
            }
        }

        composable(Screen.Chat.route) {
            ChatScreen(
                chatHelper = ChatHelper(db, LocalContext.current),
                navController = navController
            )
        }
        composable(
            route = Screen.GroupChat.route,
            arguments = listOf(navArgument("groupName") { type = NavType.StringType })
        ) { backStackEntry ->
            val groupName = backStackEntry.arguments?.getString("groupName")
            groupName?.let {
                val chatHelper = ChatHelper(db, LocalContext.current)
                val selectedGroup = groups.find { it.name == groupName }
                if (selectedGroup != null) {
                    GroupChatScreen(group = selectedGroup, chatHelper = chatHelper)
                }
            }
        }
    }
}







