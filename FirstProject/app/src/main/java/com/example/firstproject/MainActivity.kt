package com.example.firstproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
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
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent().inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            FirstProjectTheme {
                AppNavGraph(
                    factory = viewModelFactory,
                    navigator = NavigatorImpl()
                )
            }
        }
    }
}


@Composable
fun AppNavGraph(
    factory: ViewModelProvider.Factory,
    navigator: NavigatorImpl
) {

    val context = LocalContext.current
    val appComponent = remember { context.appComponent() }

    NavDisplay(
        backStack = navigator.getBackStack(),
        onBack = { navigator.popEntry() },
        entryProvider = entryProvider {
            entry<Search> {
                SearchScreen(
                    factory,
                    navigator)
            }
            entry<CommonInfo> { backStackEntry ->
                val commonInfo = backStackEntry as? CommonInfo
                DetailInfoScreen(
                    filmId = commonInfo?.filmId ?:"",
                    viewModelFactoryAssisted = appComponent.detailInfoViewModelFactory(),
                    navigator = navigator
                )
            }
        }

    )
}
