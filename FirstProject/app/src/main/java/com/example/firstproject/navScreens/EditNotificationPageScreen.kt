package com.example.firstproject.navScreens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.firstproject.notifications.NotificationHandler
import com.example.firstproject.R
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

@Composable
fun EditNotificationScreen(notificationHandler: NotificationHandler) {
    val context = LocalContext.current

    var notificationId by remember { mutableStateOf("") }
    var newContent by remember { mutableStateOf("") }

    EditNotificationContent(
        notificationId = notificationId,
        onValueChangeTitle = {
            notificationId = it.filter { ch -> ch.isDigit() }
        },
        labelTitle = stringResource(R.string.notification_id),
        onValueChangeText = { newContent = it },
        labelText = stringResource(R.string.new_text),
        textValue = newContent,
        onClickUpdateButton = {
            val id = notificationId.toIntOrNull()
            if (id == null) {
                Toast.makeText(
                    context,
                    context.getString(R.string.input_error_id_text),
                    Toast.LENGTH_SHORT
                ).show()
                return@EditNotificationContent
            }

            val updated = notificationHandler.updateContentIfExists(id, newContent)
            if (updated) {
                Toast.makeText(
                    context,
                    context.getString(R.string.message_is_update),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.notification_not_found),
                    Toast.LENGTH_SHORT
                ).show()
            }
        },
        onClickClearButton = {
            notificationHandler.cancelAll()
            Toast.makeText(
                context,
                context.getString(R.string.all_notifications_cleared),
                Toast.LENGTH_SHORT
            ).show()
        }
    )
}

@Composable
fun EditNotificationContent(
    notificationId: String,
    onValueChangeTitle: (String) -> Unit,
    labelTitle: String,
    onValueChangeText: (String) -> Unit,
    labelText: String,
    textValue: String,
    onClickUpdateButton: () -> Unit,
    onClickClearButton: () -> Unit

){
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        OutlinedTextField(
            value = notificationId,
            onValueChange = onValueChangeTitle,
            label = { Text(labelTitle) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = textValue,
            onValueChange = onValueChangeText,
            label = { Text(labelText) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onClickUpdateButton,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.update_button_text))
        }

        Button(
            onClick = onClickClearButton,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer)
        ) {
            Text(stringResource(R.string.clear_all_button))
        }
    }
}
