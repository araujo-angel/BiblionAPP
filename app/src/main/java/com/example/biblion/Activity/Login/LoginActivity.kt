package com.example.biblion.Activity.Login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModelProvider
import com.example.biblion.Activity.Dashboard.MainActivity
import com.example.biblion.Domain.UserModel
import com.example.biblion.Helper.TinyDB
import com.example.biblion.ViewModel.UserViewModel
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import com.example.biblion.R

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[UserViewModel::class.java]
        setContent {
            LoginScreen(viewModel)
        }
    }
}

@Composable
fun LoginScreen(viewModel: UserViewModel) {
    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val user by viewModel.user.observeAsState()
    val error by viewModel.error.observeAsState()

    // Observa login bem-sucedido
    LaunchedEffect(user) {
        user?.let {
            TinyDB(context).putString("user_email", it.email)
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    // Observa erros
    LaunchedEffect(error) {
        error?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.blackPink)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RoundedTextField(
                value = username,
                onValueChange = { username = it },
                placeholder = "Username",
            )
            Spacer(modifier = Modifier.height(12.dp))
            RoundedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = "Email"
            )
            Spacer(modifier = Modifier.height(12.dp))
            RoundedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = "Password",
                isPassword = true
            )
            Spacer(modifier = Modifier.height(24.dp))
            RoundedButton(text = "Login" ) {
                if (email.isNotBlank() && password.isNotBlank()) {
                    viewModel.login(email.trim(), password.trim())
                } else {
                    Toast.makeText(context, "Please fill in Email and Password", Toast.LENGTH_SHORT).show()
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            RoundedButton(text = "Register", ) {
                if (username.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
                    viewModel.register(
                        UserModel(
                            username.trim(),
                            password.trim(),
                            email.trim()
                        )
                    )
                } else {
                    Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
fun RoundedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF2F2F2), shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF2F2F2),
            unfocusedContainerColor = Color(0xFFF2F2F2)
        )
    )
}

@Composable
fun RoundedButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.pink)
        )
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}