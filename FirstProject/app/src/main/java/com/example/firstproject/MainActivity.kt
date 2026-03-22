package com.example.firstproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.firstproject.ui.theme.FirstProjectTheme
import kotlinx.coroutines.launch
import ru.itis.di.ServiceLocator
import ru.itis.domain.model.FilmModel
import ru.itis.domain.usecase.SearchFilmByQueryUseCase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val searchUseCase = SearchFilmByQueryUseCase(
            filmRepository = ServiceLocator.getFilmRepository()
        )

        setContent {

            val scope = rememberCoroutineScope()

            FirstProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        var films by remember { mutableStateOf<List<FilmModel>>(emptyList()) }

                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                scope.launch {
                                    films = searchUseCase("Drive")
                                }
                            }
                        ) {
                            Text(text = "Search film")
                        }

                        LazyColumn(
                            contentPadding = PaddingValues(
                                start = 8.dp,
                                end = 8.dp,
                                bottom = 80.dp
                            )
                        ) {
                            items(films){ film ->

                                Text(text = film.title)

                                Spacer(modifier = Modifier.padding(4.dp))

                                Text(text = film.year)

                                Spacer(modifier = Modifier.padding(4.dp))

                                Text(text = film.type)

                                Spacer(modifier = Modifier.padding(4.dp))

                                Text(text = film.id)

                                Spacer(modifier = Modifier.padding(4.dp))

                                Text(text = film.poster)

                                Spacer(modifier = Modifier.padding(4.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}
