package com.example.firstproject.ui.compose.navScreens

import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.firstproject.di.DatabaseService
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.firstproject.R
import com.example.firstproject.models.FilmDataModel
import com.example.firstproject.utils.PathConstants
import com.example.firstproject.utils.ResManager
import com.example.firstproject.utils.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AddFilmScreen(
    navController: NavHostController,
    sessionManager: SessionManager,
    resManager: ResManager,
    coroutineScope: CoroutineScope
) {
    val repository = DatabaseService.getFilmRepository()
    val userId = sessionManager.getUserId()
    val context = LocalContext.current

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var tempImageUri by remember { mutableStateOf<Uri?>(null) }

    var error by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }

    val imagePickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.OpenDocument()
        ) { uri ->
            if (uri != null){
                tempImageUri = uri

                coroutineScope.launch {
                    imageUri = copyImageToAppStorage(context, uri, title)
                }

                error = null
            }
        }

    val displayImageUri = tempImageUri ?: imageUri

    AddFilmContent(
        title = title,
        description = description,
        year = year,
        genre = genre,
        country = country,
        imageUri = displayImageUri,
        imagePickerLauncher = imagePickerLauncher,
        onTitleValueChange = {
            title = it
            error = null
        },
        onDescriptionValueChange = {
            description = it
            error = null
        },
        onYearValueChange = {
            year = it.filter(Char::isDigit)
        },
        onGenreValueChange = {
            genre = it
            error = null
        },
        onCountryValueChange = {
            country = it
            error = null
        },
        error = error,
        loading = loading,
        onBtnClick = {
            when (
                val result = validateFilmInput(
                    title,
                    description,
                    year,
                    genre,
                    country,
                    imageUri,
                    resManager
                )
            ) {
                is ValidationResult.Error -> {
                    error = result.message
                }

                ValidationResult.Success -> {
                    coroutineScope.launch {
                        loading = true

                        if (repository.isFilmExists(title, userId)) {
                            error = resManager.getString(R.string.film_is_exists_error)
                            loading = false
                            return@launch
                        }

                        repository.createNewFilm(
                            FilmDataModel(
                                title = title,
                                description = description,
                                releaseYear = year.toInt(),
                                genre = genre,
                                country = country,
                                imageUrl = imageUri.toString(),
                                userId = userId
                            )
                        )

                        loading = false
                        navController.popBackStack()
                    }
                }
            }
        }
    )
}

@Composable
fun AddFilmContent(
    title: String,
    description: String,
    year: String,
    genre: String,
    country: String,
    imageUri: Uri?,
    imagePickerLauncher: ManagedActivityResultLauncher<Array<String>, Uri?>,
    onTitleValueChange: (String) -> Unit,
    onDescriptionValueChange: (String) -> Unit,
    onYearValueChange: (String) -> Unit,
    onGenreValueChange: (String) -> Unit,
    onCountryValueChange: (String) -> Unit,
    error: String?,
    loading: Boolean,
    onBtnClick: () -> Unit
) {
    Column (
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(stringResource(R.string.add_film_screen_title), style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = title,
            onValueChange = onTitleValueChange,
            label = { Text(stringResource(R.string.title_label)) },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionValueChange,
            label = { Text(stringResource(R.string.description_label)) },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = year,
            onValueChange = onYearValueChange,
            label = { Text(stringResource(R.string.year_label)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = country,
            onValueChange = onCountryValueChange,
            label = { Text(stringResource(R.string.country_label)) },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = genre,
            onValueChange = onGenreValueChange,
            label = { Text(stringResource(R.string.genre_label)) },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                imagePickerLauncher.launch(arrayOf("image/*"))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text( stringResource(R.string.select_img_btn) )
        }

        imageUri?.let {
            AsyncImage(
                model = it,
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(140.dp)
            )
        }

        error?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        Button(
            onClick = onBtnClick,
            enabled = !loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text(stringResource(R.string.save_btn_title))
            }
        }
    }
}

private suspend fun copyImageToAppStorage(
    context: Context,
    sourceUri: Uri,
    fileName: String
): Uri? = withContext(Dispatchers.IO) {
    try {
        val contentResolver = context.contentResolver

        val timeStamp = SimpleDateFormat(PathConstants.DATE_PATTERN, Locale.getDefault()).format(Date())
        val imageFileName = "IMG_${timeStamp}_${fileName.hashCode()}.jpg"

        val storageDir = File(context.filesDir, PathConstants.IMAGE_CACHE_DIR)
        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }

        val destinationFile = File(storageDir, imageFileName)

        contentResolver.openInputStream(sourceUri)?.use { input ->
            FileOutputStream(destinationFile).use { output ->
                input.copyTo(output)
            }
        }

        FileProvider.getUriForFile(
            context,
            "${context.packageName}.${PathConstants.FILE_PROVIDER}",
            destinationFile
        )
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

sealed class ValidationResult {
    data object Success : ValidationResult()
    data class Error(val message: String) : ValidationResult()
}

private fun validateFilmInput(
    title: String,
    description: String,
    year: String,
    genre: String,
    country: String,
    imageUri: Uri?,
    resManager: ResManager
): ValidationResult {

    if (
        title.isBlank() ||
        description.isBlank() ||
        year.isBlank() ||
        genre.isBlank() ||
        country.isBlank()
    ) {
        return ValidationResult.Error(resManager.getString(R.string.error_blank))
    }

    val releaseYear = year.toIntOrNull()
        ?: return ValidationResult.Error(resManager.getString(R.string.year_type_is_not_valid))

    if (releaseYear < 1888) {
        return ValidationResult.Error(resManager.getString(R.string.year_is_not_valid))
    }

    if (imageUri == null) {
        return ValidationResult.Error(resManager.getString(R.string.not_selected_img))
    }

    return ValidationResult.Success
}
