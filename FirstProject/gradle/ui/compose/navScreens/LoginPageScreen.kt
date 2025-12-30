package com.example.firstproject.ui.compose.navScreens

import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.firstproject.db.AppDatabase
import com.example.firstproject.utils.SessionManager
import kotlinx.coroutines.CoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.firstproject.R
import com.example.firstproject.di.DatabaseService
import com.example.firstproject.navigation.HomeScreenObject
import com.example.firstproject.navigation.LoginScreenObject
import com.example.firstproject.navigation.RegisterScreenObject
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt

@Composable
fun LoginScreen(
    navController: NavController,
    sessionManager: SessionManager,
    coroutineScope: CoroutineScope
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var isPasswordHidden by remember { mutableStateOf(true) }
    var commonError by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }

    LoginContent(
        email = email,
        password = password,
        emailError = emailError,
        passwordError = passwordError,
        isPasswordHidden = isPasswordHidden,
        onEmailChange = { email = it; emailError = null },
        onPasswordChange = { password = it; passwordError = null },
        onButtonVisibility = { isPasswordHidden = !isPasswordHidden },
        onSignUpClick = {
            validate(
                email,
                password,
                "Email empty",
                "Password empty",
                "Email invalid",
                "Password too short",
                onEmailError = { emailError = it },
                onPasswordError = { passwordError = it }
            ) {
                coroutineScope.launch {
                    loading = true

                    val user = DatabaseService.getUserRepository().login(email)
                    loading = false
                    if (user != null && BCrypt.checkpw(password, user.password)) {
                        sessionManager.saveSession(user.id, user.email)
                        navController.navigate(HomeScreenObject) {
                            popUpTo(LoginScreenObject) { inclusive = true }
                        }
                    } else {
                        commonError = "Invalid credentials"
                    }
                }
            }
        },
        onRegisterClick = {
            navController.navigate(RegisterScreenObject)
        },
        modifier = Modifier,
        commonError = commonError,
        isLoading = loading
    )
}


@Composable
fun LoginContent(
    email: String,
    password: String,
    emailError: String?,
    passwordError: String?,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    isPasswordHidden: Boolean,
    onButtonVisibility: () -> Unit,
    onSignUpClick: () -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier,
    commonError: String? = null,
    isLoading: Boolean = false
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .imePadding()
            .navigationBarsPadding(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

                Text(
                    text = stringResource(R.string.login_title),
                    style = MaterialTheme.typography.headlineMedium
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    label = { Text(stringResource(R.string.email)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    isError = emailError != null,
                    supportingText = emailError?.let {
                        { Text(it, color = MaterialTheme.colorScheme.error) }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    label = { Text(stringResource(R.string.password)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation =
                        if (isPasswordHidden)
                            PasswordVisualTransformation()
                        else
                            VisualTransformation.None,
                    trailingIcon = {
                        IconButton(onClick = onButtonVisibility) {
                            Icon(
                                imageVector = if (isPasswordHidden)
                                    Icons.Filled.Visibility
                                else
                                    Icons.Filled.VisibilityOff,
                                contentDescription = null
                            )
                        }
                    },
                    isError = passwordError != null,
                    supportingText = passwordError?.let {
                        { Text(it, color = MaterialTheme.colorScheme.error) }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                commonError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            Button(
                onClick = onSignUpClick,
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(stringResource(R.string.sign_in))
                }
            }

            TextButton(
                onClick = onRegisterClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.create_account),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

private fun validate(
    email: String,
    password: String,
    errorEmailIsBlank: String,
    errorPasswordIsBlank: String,
    errorEmailIsNotValid: String,
    errorPasswordIsNotValid: String,
    onEmailError: (String?) -> Unit,
    onPasswordError: (String?) -> Unit,
    successData: () -> Unit
) {
    val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isPasswordValid = password.length >= 8

    val emailError = when {
        email.isBlank() -> errorEmailIsBlank
        !isEmailValid -> errorEmailIsNotValid
        else -> null
    }

    val passwordError = when {
        password.isBlank() -> errorPasswordIsBlank
        !isPasswordValid -> errorPasswordIsNotValid
        else -> null
    }

    onEmailError(emailError)
    onPasswordError(passwordError)

    if (emailError == null && passwordError == null) {
        successData()
    }
}
