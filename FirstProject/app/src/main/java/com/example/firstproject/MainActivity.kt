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
import ru.itis.detail_info.ui.DetailInfoScreen
import ru.itis.detail_info.viewmodel.DetailInfoViewModel
import ru.itis.search.ui.SearchScreen
import ru.itis.search.viewmodel.SearchViewModel

class MainActivity : ComponentActivity() {

    private val searchViewModel: SearchViewModel by viewModels { SearchViewModel.Factory }
    private val detailInfoViewModel: DetailInfoViewModel by viewModels { DetailInfoViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            FirstProjectTheme {
                AppNavGraph(
                    searchViewModel,
                    detailInfoViewModel,
                    NavigatorImpl()
                )
            }
        }
    }
}


@Composable
fun AppNavGraph(
    searchViewModel: SearchViewModel,
    detailInfoViewModel: DetailInfoViewModel,
    navigator: NavigatorImpl
) {

    NavDisplay(
        backStack = navigator.getBackStack(),
        onBack = { navigator.popEntry() },
        entryProvider = entryProvider {
            entry<Search> {
                SearchScreen(
                    searchViewModel,
                    navigator)
            }
            entry<CommonInfo> { backStackEntry ->
                val commonInfo = backStackEntry as? CommonInfo
                DetailInfoScreen(
                    filmId = commonInfo?.filmId ?:"",
                    viewModel = detailInfoViewModel,
                    navigator = navigator
                )
            }
        }

    )
}
