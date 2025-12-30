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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.firstproject.R
import com.example.firstproject.di.DatabaseService
import com.example.firstproject.models.UserDataModel
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt

@Composable
fun RegisterScreen(
    navController: NavHostController,
    coroutineScope: CoroutineScope
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    var isPasswordHidden by remember { mutableStateOf(true) }
    var isLoading by remember { mutableStateOf(false) }

    val errorNameIsBlank = stringResource(R.string.error_name_is_blank)
    val errorEmailIsBlank = stringResource(R.string.error_email_is_blank)
    val errorPasswordIsBlank = stringResource(R.string.error_password_is_blank)
    val errorEmailIsNotValid = stringResource(R.string.error_email_not_valid)
    val errorPasswordIsNotValid = stringResource(R.string.error_password_length)

    RegisterContent(
        name = name,
        email = email,
        password = password,
        nameError = nameError,
        emailError = emailError,
        passwordError = passwordError,
        onNameChange = { name = it; nameError = null },
        onEmailChange = { email = it; emailError = null },
        onPasswordChange = { password = it; passwordError = null },
        isPasswordHidden = isPasswordHidden,
        onPasswordVisibilityClick = { isPasswordHidden = !isPasswordHidden },
        onRegisterClick = {
            validateRegister(
                name = name,
                email = email,
                password = password,
                errorNameIsBlank = errorNameIsBlank,
                errorEmailIsBlank = errorEmailIsBlank,
                errorPasswordIsBlank = errorPasswordIsBlank,
                errorEmailIsNotValid = errorEmailIsNotValid,
                errorPasswordIsNotValid = errorPasswordIsNotValid,
                onNameError = { nameError = it },
                onEmailError = { emailError = it },
                onPasswordError = { passwordError = it }
            ) {
                coroutineScope.launch {
                    isLoading = true
                    DatabaseService.getUserRepository().register(
                        UserDataModel(
                            name = name,
                            email = email,
                            password = BCrypt.hashpw(password,
                                BCrypt.gensalt(12))
                        )
                    )
                    isLoading = false
                    navController.popBackStack()
                }
            }
        }
    )
}


@Composable
fun RegisterContent(
    name: String,
    email: String,
    password: String,
    nameError: String?,
    emailError: String?,
    passwordError: String?,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    isPasswordHidden: Boolean,
    onPasswordVisibilityClick: () -> Unit,
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
                    text = stringResource(R.string.register_title),
                    style = MaterialTheme.typography.headlineMedium
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = onNameChange,
                    label = { Text(stringResource(R.string.name)) },
                    isError = nameError != null,
                    supportingText = nameError?.let {
                        { Text(it, color = MaterialTheme.colorScheme.error) }
                    },
                    modifier = Modifier.fillMaxWidth()
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
                        IconButton(onClick = onPasswordVisibilityClick) {
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
                onClick = onRegisterClick,
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(stringResource(R.string.register))
                }
            }
        }
    }
}

private fun validateRegister(
    name: String,
    email: String,
    password: String,
    errorNameIsBlank: String,
    errorEmailIsBlank: String,
    errorPasswordIsBlank: String,
    errorEmailIsNotValid: String,
    errorPasswordIsNotValid: String,
    onNameError: (String?) -> Unit,
    onEmailError: (String?) -> Unit,
    onPasswordError: (String?) -> Unit,
    successData: () -> Unit
) {
    val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isPasswordValid = password.length >= 8

    val nameError = if (name.isBlank()) errorNameIsBlank else null

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

    onNameError(nameError)
    onEmailError(emailError)
    onPasswordError(passwordError)

    if (nameError == null && emailError == null && passwordError == null) {
        successData()
    }
}
