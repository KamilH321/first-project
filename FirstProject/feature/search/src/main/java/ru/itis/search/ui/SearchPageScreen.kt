package ru.itis.search.ui

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.itis.search.viewmodel.SearchViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
) {

    var input by remember { mutableStateOf("") }


    SearchContent(
        viewModel = viewModel,
        input = input,
        onInputValueChange = { input = it}
    )
}

@Composable
private fun SearchContent(
    viewModel: SearchViewModel,
    input: String,
    onInputValueChange: (String) -> Unit
) {

    val dataList = viewModel.filmList.collectAsStateWithLifecycle()


    Scaffold(
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 56.dp),
                onClick = {
                    viewModel.getFilmList(input)
                }
            ) {
                Text(text = "Search film")
            }
        },
        topBar = {
            TextField(
                onValueChange = onInputValueChange,
                value = input,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 56.dp)
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(count = dataList.value.size, key = { position ->
                dataList.value[position].id
            }) { position ->
                Text(
                    text = dataList.value[position].title,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.padding(4.dp))

                Text(
                    text = dataList.value[position].year,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.padding(4.dp))

                Text(
                    text = dataList.value[position].type,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.padding(4.dp))

                Text(
                    text = dataList.value[position].poster,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.padding(16.dp))
            }
        }
    }
}