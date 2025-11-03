package com.example.firstproject.navScreens.addnotepage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.firstproject.R
import com.example.firstproject.models.NoteDataModel
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun AddNoteScreen(
    navController: NavController,
    onNoteSave: (NoteDataModel) -> Unit
){
    var title by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }
    var titleError by remember { mutableStateOf(false) }
    val errorMessage = stringResource(R.string.error_message)

    AddNoteContent(
        title = title,
        text = text,
        titleError = titleError,
        onTitleChange = { newTitle ->
            title = newTitle
            if (newTitle.isNotBlank()) titleError = false
        },
        onTextChange = {newText ->
            text = newText
        },
        onButtonClick = {
            if(title.isBlank()){
                titleError = true
            } else{
                onNoteSave(NoteDataModel(title, text))
                navController.popBackStack()
            }
        },
        errorMessage,
        Modifier
    )

}

@Composable
fun AddNoteContent(
    title: String,
    text: String,
    titleError: Boolean,
    onTitleChange: (String) -> Unit,
    onTextChange: (String) -> Unit,
    onButtonClick: () -> Unit,
    errorMessage: String,
    modifier: Modifier
){
    Surface(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .imePadding()
            .navigationBarsPadding(),
        color = MaterialTheme.colorScheme.background
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ){
            Text(
                text = stringResource(R.string.new_note),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = title,
                onValueChange = onTitleChange,
                label = { Text(stringResource(R.string.note_title)) },
                isError = titleError,
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            if (titleError) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = text,
                onValueChange = onTextChange,
                label = { Text(stringResource(R.string.note_text)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onButtonClick,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(stringResource(R.string.button_save_text))
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
    AddNoteContent(
        "title",
        "text",
        false,
        {},
        {},
        {},
        "",
        Modifier
    )
}