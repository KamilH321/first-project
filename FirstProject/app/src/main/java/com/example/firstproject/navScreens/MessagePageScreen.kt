package com.example.firstproject.navScreens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.firstproject.R
import com.example.firstproject.models.Message
import com.example.firstproject.repository.MessageRepository
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MessagesScreen() {
    var newMessage by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(MessageRepository.getAll()) }

    MessagesContent(
        newMessage = newMessage,
        textFieldLabel = stringResource(R.string.new_message),
        onValueChange = { newMessage = it },
        onButtonClick = {
            if (newMessage.isNotBlank()) {
                MessageRepository.addMessage(
                    Message(
                        text = newMessage,
                        timestamp = System.currentTimeMillis()
                    )
                )
                newMessage = ""
                messages = MessageRepository.getAll()
            }
        },
        lazyColumnScope = {
            items(messages.sortedBy { it.timestamp }) { message ->
                MessageItem(message)
                Spacer(Modifier.height(8.dp))
            }
        }
    )
}

@Composable
fun MessagesContent(
    newMessage: String,
    onValueChange: (String) -> Unit,
    textFieldLabel: String,
    onButtonClick: () -> Unit,
    lazyColumnScope: LazyListScope.() -> Unit
){
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        OutlinedTextField(
            value = newMessage,
            onValueChange = onValueChange,
            label = { Text(textFieldLabel) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onButtonClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.add_message))
        }

        Spacer(Modifier.height(24.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            lazyColumnScope()
        }
    }
}

@Composable
fun MessageItem(message: Message) {
    val dateFormat = SimpleDateFormat(stringResource(R.string.time_format), Locale.getDefault())

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = message.text,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = dateFormat.format(Date(message.timestamp)),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}