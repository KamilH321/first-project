package com.example.firstproject.ui.compose.navScreens

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.example.firstproject.di.DatabaseService
import com.example.firstproject.utils.SessionManager
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.firstproject.R
import com.example.firstproject.models.FilmDataModel
import com.example.firstproject.navigation.AddFilmScreenObject
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import android.net.Uri
import com.example.firstproject.navigation.ProfileScreenObject
import com.example.firstproject.utils.PathConstants
import java.io.File


enum class SortType { TITLE, YEAR, COUNTRY, GENRE }

@Composable
fun HomeScreen(
    navController: NavHostController,
    sessionManager: SessionManager,
) {
    val userId = sessionManager.getUserId()
    val repository = DatabaseService.getFilmRepository()

    var films by remember { mutableStateOf<List<FilmDataModel>>(emptyList()) }
    var sort by remember { mutableStateOf(SortType.TITLE) }
    var showSheet by remember { mutableStateOf(false) }

    LaunchedEffect(sort) {
        films = repository.getFilms().let {
            when(sort) {
                SortType.TITLE -> it.sortedBy { film -> film.title }
                SortType.YEAR -> it.sortedBy { film -> film.releaseYear }
                SortType.COUNTRY -> it.sortedBy { film -> film.country }
                SortType.GENRE -> it.sortedBy { film -> film.genre }
            }
        }
    }

    HomeContent(
        navController = navController,
        userId = userId,
        films = films,
        showSheet = showSheet,
        onShowSheetChange = {showSheet = !showSheet},
        onSortBtnByTitleClick = {
            sort = SortType.TITLE
            showSheet = false
        },
        onSortBtnByYearClick = {
            sort = SortType.YEAR
            showSheet = false
        },
        onSortBtnByCountryClick =  {
            sort = SortType.COUNTRY
            showSheet = false
        },
        onSortBtnByGenreClick = {
            sort = SortType.GENRE
            showSheet = false
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    navController: NavController,
    userId: Int,
    films: List<FilmDataModel>,
    showSheet: Boolean,
    onShowSheetChange: () -> Unit,
    onSortBtnByTitleClick: () -> Unit,
    onSortBtnByYearClick: () -> Unit,
    onSortBtnByCountryClick: () -> Unit,
    onSortBtnByGenreClick: () -> Unit
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.screen_title)) },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(ProfileScreenObject)
                            print("TAG - TEST")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = stringResource(R.string.profile_label)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(AddFilmScreenObject(userId))
                }
            ) {
                Text(stringResource(R.string.add_btn_label))
            }
        }
    ) { padding ->
        Column (
            Modifier.padding(padding)
        ) {
            Button(
                onClick = onShowSheetChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(stringResource(R.string.sort_btn_label))
            }

            LazyColumn (
                contentPadding = PaddingValues(
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 80.dp
                )
            ) {
                items(films) { film ->
                    FilmItem(film, LocalContext.current)
                }
            }
        }
    }

    if (showSheet){
        ModalBottomSheet(
            onDismissRequest = { onShowSheetChange() }
        ) {
            Button(
                onClick = onSortBtnByTitleClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(stringResource(R.string.sort_by_title))
            }

            Button(
                onClick = onSortBtnByYearClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(stringResource(R.string.sort_by_year))
            }

            Button(
                onClick = onSortBtnByCountryClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(stringResource(R.string.sort_by_country))
            }

            Button(
                onClick = onSortBtnByGenreClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(stringResource(R.string.sort_by_genre))
            }
        }
    }
}

@Composable
private fun FilmItem(
    film: FilmDataModel,
    ctx: Context
) {
    var expanded by remember { mutableStateOf(false) }

    val imageUri = remember(film.imageUrl) {
        parseImageUri(film.imageUrl, ctx)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium

    ) {
        Row (
            modifier =  Modifier
                .padding(8.dp)
                .heightIn(min = 120.dp)
        ) {
            AsyncImage(
                model = film.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(MaterialTheme.shapes.small)
            )

            Spacer(Modifier.width(12.dp))

            Column (
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = film.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.width(4.dp))

                Text(
                    text = stringResource(R.string.year_text, film.releaseYear, film.genre, film.country),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.width(6.dp))

                Text(
                    text = film.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = if(expanded) Int.MAX_VALUE else 2,
                    overflow = TextOverflow.Ellipsis
                )

                TextButton(
                    onClick = {
                        expanded = !expanded
                    },
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = if (expanded) {
                            stringResource(R.string.collapse)
                        } else {
                            stringResource(R.string.expand)
                        }
                    )
                }

            }
        }
    }
}

private fun parseImageUri(imageUrl: String, context: Context): Any? {
    return when {
        imageUrl.startsWith(PathConstants.CONTENT_SCHEME) -> {
            try {
                val uri = Uri.parse(imageUrl)
                context.contentResolver.query(uri, null, null, null, null)?.use {
                    if (it.count > 0) uri else null
                }
            } catch (e: SecurityException) {
                null
            } catch (e: Exception) {
                null
            }
        }
        imageUrl.startsWith(PathConstants.FILE_SCHEME) -> {
            val file = File(imageUrl.removePrefix(PathConstants.FILE_SCHEME))
            if (file.exists()) Uri.fromFile(file) else null
        }
        imageUrl.startsWith(PathConstants.PREFIX) -> {
            val file = File(imageUrl)
            if (file.exists()) Uri.fromFile(file) else null
        }
        else -> {
            val file = File(context.filesDir, imageUrl)
            if (file.exists()) Uri.fromFile(file) else imageUrl
        }
    }
}

