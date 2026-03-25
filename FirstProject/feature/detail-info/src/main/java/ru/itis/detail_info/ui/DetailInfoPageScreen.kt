package ru.itis.detail_info.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.itis.detail_info.viewmodel.DetailInfoViewModel
import ru.itis.navigation.CommonInfo
import ru.itis.navigation.Navigator

@Composable
fun DetailInfoScreen(
    filmId: String,
    viewModel: DetailInfoViewModel,
    navigator: Navigator
){

    val film = viewModel.film.collectAsStateWithLifecycle()

    viewModel.getFilm(filmId)

    Scaffold(
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 56.dp),
                onClick = {
                   navigator.popEntry()
                }
            ) {
                Text(text = "Go back")
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Text(
                text = film.value?.title ?: "",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.padding(4.dp))

            Text(
                text = film.value?.year ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.padding(4.dp))

            Text(
                text = film.value?.type ?: "",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.padding(4.dp))

            Text(
                text = film.value?.poster ?: "",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}