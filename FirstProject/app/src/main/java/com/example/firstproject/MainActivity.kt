package com.example.firstproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import ru.itis.navigation.CommonInfo
import com.example.firstproject.navigation.NavigatorImpl
import ru.itis.navigation.Search
import com.example.firstproject.ui.theme.FirstProjectTheme
import ru.itis.detail_info.DetailInfoScreen
import ru.itis.search.ui.SearchScreen
import ru.itis.search.viewmodel.SearchViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: SearchViewModel by viewModels { SearchViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            FirstProjectTheme {
                AppNavGraph(
                    viewModel,
                    NavigatorImpl()
                )
            }
        }
    }
}


@Composable
fun AppNavGraph(
    viewModel: SearchViewModel,
    navigator: NavigatorImpl
) {

    NavDisplay(
        backStack = navigator.getBackStack(),
        onBack = { navigator.popEntry() },
        entryProvider = entryProvider {
            entry<Search> {
                SearchScreen(
                    viewModel,
                    navigator)
            }
            entry<CommonInfo> {
                DetailInfoScreen()
            }
        }

    )
}
