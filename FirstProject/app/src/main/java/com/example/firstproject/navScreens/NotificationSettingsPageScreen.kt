package com.example.firstproject.navScreens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.firstproject.R
import com.example.firstproject.notifications.NotificationConfig
import com.example.firstproject.notifications.NotificationHandler
import com.example.firstproject.notifications.NotificationImportance
import kotlin.random.Random

@Composable
fun NotificationSettingsScreen(notificationHandler: NotificationHandler) {
    val context = LocalContext.current

    var title by remember { mutableStateOf("") }
    var titleError by remember { mutableStateOf(false) }
    var content by remember { mutableStateOf("") }
    var expandLongText by remember { mutableStateOf(false) }
    var importance by remember { mutableStateOf(NotificationImportance.DEFAULT) }
    var openMainOnClick by remember { mutableStateOf(true) }
    var enableInlineReply by remember { mutableStateOf(true) }

    val isExpandAvailable = content.isNotBlank()

    NotificationSettingsContent(
        title = title,
        titleError = titleError,
        content = content,
        expandLongText = expandLongText,
        importance = importance,
        openMainOnClick = openMainOnClick,
        enableInlineReply = enableInlineReply,
        isExpandAvailable = isExpandAvailable,
        onTitleChange = { newTitle ->
            title = newTitle
            titleError = false
        },
        onContentChange = { newContent ->
            content = newContent
            if (newContent.isBlank()) expandLongText = false
        },
        onExpandLongTextChange = { newValue ->
            if (isExpandAvailable) expandLongText = newValue
        },
        onImportanceChange = { newImportance ->
            importance = newImportance
        },
        onOpenMainOnClickChange = { newValue ->
            openMainOnClick = newValue
        },
        onEnableInlineReplyChange = { newValue ->
            enableInlineReply = newValue
        },
        onCreateNotification = {
            if (title.isBlank()) {
                titleError = true
                Toast.makeText(
                    context,
                    context.getString(R.string.empty_title_warning),
                    Toast.LENGTH_SHORT
                ).show()
                return@NotificationSettingsContent
            }

            val config = NotificationConfig(
                id = Random.nextInt(1, 10000),
                title = title,
                content = content.takeIf { it.isNotBlank() },
                isExpandLongText = expandLongText,
                importance = importance,
                openMainOnClick = openMainOnClick,
                enableInlineReply = enableInlineReply
            )

            notificationHandler.showNotification(config)
            Toast.makeText(
                context,
                context.getString(R.string.notification_created),
                Toast.LENGTH_SHORT
            ).show()
        }
    )
}

@Composable
fun NotificationSettingsContent(
    title: String,
    titleError: Boolean,
    content: String,
    expandLongText: Boolean,
    importance: NotificationImportance,
    openMainOnClick: Boolean,
    enableInlineReply: Boolean,
    isExpandAvailable: Boolean,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onExpandLongTextChange: (Boolean) -> Unit,
    onImportanceChange: (NotificationImportance) -> Unit,
    onOpenMainOnClickChange: (Boolean) -> Unit,
    onEnableInlineReplyChange: (Boolean) -> Unit,
    onCreateNotification: () -> Unit
) {
    val scrollState = rememberScrollState()
    var expanded by remember { mutableStateOf(false) }
    val importanceOptions = NotificationImportance.values()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            label = { Text(stringResource(R.string.notification_title)) },
            isError = titleError,
            modifier = Modifier.fillMaxWidth()
        )
        if (titleError) {
            Text(
                stringResource(R.string.empty_title_warning),
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = content,
            onValueChange = onContentChange,
            label = { Text(stringResource(R.string.notification_content)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(R.string.share_text))
            Switch(
                checked = expandLongText,
                onCheckedChange = onExpandLongTextChange,
                enabled = isExpandAvailable
            )
        }
        if (!isExpandAvailable) {
            Text(
                stringResource(R.string.add_text_to_enable),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(
            stringResource(R.string.notification_priority),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true },
                shape = MaterialTheme.shapes.small,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                color = MaterialTheme.colorScheme.surface
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        when (importance) {
                            NotificationImportance.MIN -> stringResource(R.string.priority_min)
                            NotificationImportance.LOW -> stringResource(R.string.priority_low)
                            NotificationImportance.DEFAULT -> stringResource(R.string.priority_default)
                            NotificationImportance.HIGH -> stringResource(R.string.priority_high)
                        },
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                importanceOptions.forEach { imp ->
                    DropdownMenuItem(
                        onClick = {
                            onImportanceChange(imp)
                            expanded = false
                        },
                        text = {
                            Text(
                                when (imp) {
                                    NotificationImportance.MIN -> stringResource(R.string.priority_min)
                                    NotificationImportance.LOW -> stringResource(R.string.priority_low)
                                    NotificationImportance.DEFAULT -> stringResource(R.string.priority_default)
                                    NotificationImportance.HIGH -> stringResource(R.string.priority_high)
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(R.string.open_app_on_click))
            Switch(
                checked = openMainOnClick,
                onCheckedChange = onOpenMainOnClickChange
            )
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(R.string.add_reply_action))
            Switch(
                checked = enableInlineReply,
                onCheckedChange = onEnableInlineReplyChange
            )
        }

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = onCreateNotification,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.create_notification))
        }
    }
}