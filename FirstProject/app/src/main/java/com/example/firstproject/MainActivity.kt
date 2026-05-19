package com.example.firstproject

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import ru.itis.navigation.CommonInfo
import ru.itis.navigation.Search
import com.example.firstproject.ui.theme.FirstProjectTheme
import ru.itis.analytics.api.Analytics
import ru.itis.detail_info.ui.DetailInfoScreen
import ru.itis.navigation.Navigator
import ru.itis.search.ui.SearchScreen
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var analytics: Analytics

    @Inject
    lateinit var navigator: Navigator

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            println("TEST TAG - Разрешение на уведомления получено!")
        } else {
            println("TEST TAG - Разрешение на уведомления не было получено")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent().inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        setContent {

            FirstProjectTheme {
                AppNavGraph(
                    factory = viewModelFactory,
                    navigator = navigator,
                    analytics = analytics
                )
            }
        }
    }
}


@Composable
fun AppNavGraph(
    factory: ViewModelProvider.Factory,
    navigator: Navigator,
    analytics: Analytics
) {

    val context = LocalContext.current
    val appComponent = remember { context.appComponent() }

    val backStack = navigator.getBackStack()
    val currentScreen = backStack.lastOrNull()

    LaunchedEffect(currentScreen) {
        currentScreen?.let { screen ->
            val screenName = screen::class.simpleName ?: "Unknown"
            analytics.trackEvent(screenName)
        }
    }

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
