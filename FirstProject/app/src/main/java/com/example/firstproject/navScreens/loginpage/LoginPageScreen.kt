package com.example.firstproject.navScreens.loginpage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.input.KeyboardType
import com.example.firstproject.R
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.firstproject.navigation.NotesPageDataObject


@Composable
fun LoginScreen(
    navController: NavController
) {
    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }

    var password by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf<String?>(null) }

    var isPasswordHidden by remember { mutableStateOf(true) }

    val errorEmailIsBlank = stringResource(R.string.error_email_is_blank)
    val errorPasswordIsBlank = stringResource(R.string.error_password_is_blank)
    val errorEmailIsNotValid = stringResource(R.string.error_email_not_valid)
    val errorPasswordIsNotValid = stringResource(R.string.error_password_length)

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
                email = email,
                password = password,
                errorEmailIsBlank = errorEmailIsBlank,
                errorPasswordIsBlank = errorPasswordIsBlank,
                errorEmailIsNotValid = errorEmailIsNotValid,
                errorPasswordIsNotValid = errorPasswordIsNotValid,
                onEmailError = { error -> emailError = error },
                onPasswordError = { error -> passwordError = error },
                successData = {
                    navController.navigate(
                        route = NotesPageDataObject(
                            email = email,
                            password = password,
                        )
                    )
                }
            )
        },
        modifier = Modifier
    )



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
){
    val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isPasswordValid = password.length > 8


    val emailError = when{
        email.isBlank() -> errorEmailIsBlank
        !isEmailValid -> errorEmailIsNotValid
        else -> null
    }

    val passwordError = when{
        password.isBlank() -> errorPasswordIsBlank
        !isPasswordValid -> errorPasswordIsNotValid
        else -> null
    }

    onEmailError(emailError)
    onPasswordError(passwordError)

    if(emailError == null && passwordError == null){
        successData()
    }

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
    onButtonVisibility: (() -> Unit) = { null },
    onSignUpClick: () -> Unit,
    modifier: Modifier
){
    Surface (
        modifier = modifier.fillMaxSize()
            .statusBarsPadding()
            .imePadding()
            .navigationBarsPadding(),
        color = MaterialTheme.colorScheme.background
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    label = {Text(stringResource(R.string.email))},
                    supportingText = emailError?.let {
                        { Text(it, color = MaterialTheme.colorScheme.error) }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier
                        .fillMaxWidth(),
                    )

                OutlinedTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    label = {Text(stringResource(R.string.password))},
                    supportingText = passwordError?.let {
                        { Text(it, color = MaterialTheme.colorScheme.error) }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (isPasswordHidden)
                        PasswordVisualTransformation() else VisualTransformation.None,
                    trailingIcon = {
                        IconButton(onClick = onButtonVisibility) {
                            Icon(
                                imageVector = if (isPasswordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = if (isPasswordHidden) stringResource(R.string.show_password) else stringResource(R.string.hide_password)
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }


            Button(
                onClick = onSignUpClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.sign_up_title))
            }
        }
    }
}


@Preview(showBackground = true,
    name = "Pixel 7 • Dark",
    showSystemUi = true,
    device = Devices.PIXEL_7)
@Composable
fun GreetingPreview() {
    LoginContent(
        email = "test@example.com",
        password = "password",
        emailError = null,
        passwordError = null,
        onEmailChange = { },
        onPasswordChange = { },
        isPasswordHidden = true,
        onButtonVisibility = { },
        onSignUpClick = { },
        modifier = Modifier
    )
}