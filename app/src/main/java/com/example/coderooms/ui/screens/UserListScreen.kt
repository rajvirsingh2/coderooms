@file:Suppress("UNUSED_EXPRESSION")

package com.example.coderooms.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.coderooms.chat.ChatHelper
import com.example.coderooms.model.User
import com.example.coderooms.model.leetcode.groups
import com.example.coderooms.ui.navigation.Screen
import com.google.firebase.database.FirebaseDatabase

@Composable
fun UserListScreen(
    chatHelper: ChatHelper,
    currentUserId: String,
    currentUserAvatar: String?, // Add a parameter for the current user's avatar
    onUserClick: (User) -> Unit,
    navController: NavController
) {
    val gradient = Brush.linearGradient(
        colors = listOf(Color(0xFF2B5876), Color(0xFF4E4376)),
        start = Offset(0f, 0f),
        end = Offset(1000f, 1000f)
    )
    var users by remember { mutableStateOf<List<User>>(emptyList()) }
    val db = FirebaseDatabase.getInstance().getReference("users").child(currentUserId).child("username")
    var username by remember { mutableStateOf("") }

    LaunchedEffect(currentUserId) {
        db.get().addOnSuccessListener { snapshot->
            username = snapshot.getValue(String::class.java).toString()?: "Unknown"
        }
    }

    Column(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        LaunchedEffect(Unit) {
            chatHelper.fetchUser { fetchedUsers ->
                val filteredUsers = fetchedUsers.filter { it.userId != currentUserId }
                users = filteredUsers
            }
        }

        LazyColumn {
            items(users) { user ->
                UserItem(user = user, onClick = { onUserClick(user) })
            }
        }

        Spacer(Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            IconButton(onClick = {
                null
            }) {
                Icon(Icons.Default.ChatBubble, contentDescription = "Chat", Modifier.size(60.dp), tint = Color.White)
            }

            Spacer(modifier = Modifier.width(16.dp))

            IconButton(onClick = {
                val selectedGroup = groups.firstOrNull()
                selectedGroup?.let {
                    navController.navigate(Screen.GroupChat.createRoute(it.name))
                }
            }) {
                Icon(Icons.Default.Group, contentDescription = "Group", modifier = Modifier.size(60.dp), tint = Color.White)
            }

            Spacer(modifier = Modifier.width(16.dp))
            // Show the current user's avatar
            AsyncImage(
                model = currentUserAvatar, // Pass the current user's avatar
                contentDescription = "Current User Avatar",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
                    .clickable {
                        navController.navigate(Screen.LeetCodeStats.createRoute(username))
                    }
            )
        }
    }
}

@Composable
fun UserItem(user: User, onClick: () -> Unit) {
    // Elevated container with rounded corners
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = onClick)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
                .clickable(onClick = onClick)
                .background(Color.White) // Shadow for elevation
                .padding(12.dp) // Padding inside the row for spacing
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar (Image) with rounded corners
            AsyncImage(
                model = user.avatar,
                contentDescription = "",
                modifier = Modifier
                    .size(50.dp) // Slightly larger avatar
                    .clip(CircleShape) // Circle shape for avatar
                    .border(2.dp, Color.Gray, CircleShape) // Border around the avatar
            )

            // Spacer for spacing between the image and the text
            Spacer(Modifier.width(16.dp))

            // Vertical line between image and text
            Divider(
                color = Color.Gray,
                modifier = Modifier
                    .height(50.dp) // Match the height of the avatar
                    .width(1.dp)  // Line thickness
            )

            Spacer(Modifier.width(16.dp))

            Text(
                text = user.username,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth(0.9f)
                .align(Alignment.CenterHorizontally)
        )
    }
}





