package com.example.httptest

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext


@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
) {
    val username by viewModel.username.collectAsState()
    val password by viewModel.password.collectAsState()
    val success by viewModel.successState.collectAsState()
    val message by viewModel.message.collectAsState()
    val context = LocalContext.current

    var count by  remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = count, block = {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    })

        Column {
            OutlinedTextField(value = username, onValueChange = { viewModel.setUsername(it) })
            OutlinedTextField(value = password, onValueChange = { viewModel.setPassword(it) })
            TextButton(onClick = {
                viewModel.login()
                count +=  1
            }) {
                Text("Login")
            }

        }
}








