package com.example.firstproject.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.firstproject.R
import com.example.firstproject.models.AppDispatchers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoroutineLauncherScreen(
    coroutinesCount: Int,
    selectedDispatcher: AppDispatchers,
    sequential: Boolean,
    parallel: Boolean,
    delayStart: Boolean,
    loading: Boolean,
    snackbarHostState: SnackbarHostState,
    onCountChange: (Int) -> Unit,
    onDispatcherChange: (AppDispatchers) -> Unit,
    onSequentialChange: () -> Unit,
    onParallelChange: () -> Unit,
    onDelayStartChange: (Boolean) -> Unit,
    onStart: () -> Unit,
    onCancel: () -> Unit
){
    var dropdownExpanded by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { snackbarData ->
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    snackbarData = snackbarData,
                    actionColor = MaterialTheme.colorScheme.inversePrimary
                )
            }
        }
    ){ contentPadding ->
        CoroutineLauncherContent(
            coroutinesCount = coroutinesCount,
            selectedDispatcher = selectedDispatcher,
            sequential = sequential,
            parallel = parallel,
            delayStart = delayStart,
            loading = loading,
            dropdownExpanded = dropdownExpanded,
            onDropdownDismiss = { dropdownExpanded = false },
            onDropdownExpand = {dropdownExpanded = true},
            onCountChange = onCountChange,
            onDispatcherChange = {
                onDispatcherChange(it)
                dropdownExpanded = false
            },
            onSequentialChange = onSequentialChange,
            onParallelChange = onParallelChange,
            onDelayStartChange = onDelayStartChange,
            onStart = onStart,
            onCancel = onCancel,
            modifier = Modifier.fillMaxSize()
                .padding(contentPadding)
        )
    }


}

@Composable
private fun CoroutineLauncherContent(
    coroutinesCount: Int,
    selectedDispatcher: AppDispatchers,
    sequential: Boolean,
    parallel: Boolean,
    delayStart: Boolean,
    loading: Boolean,
    dropdownExpanded: Boolean,
    onDropdownExpand: () -> Unit,
    onDropdownDismiss: () -> Unit,
    onCountChange: (Int) -> Unit,
    onDispatcherChange: (AppDispatchers) -> Unit,
    onSequentialChange: () -> Unit,
    onParallelChange: () -> Unit,
    onDelayStartChange: (Boolean) -> Unit,
    onStart: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier
){
    Surface(
        modifier = modifier.fillMaxSize()
            .statusBarsPadding()
            .imePadding()
            .navigationBarsPadding(),
        color = MaterialTheme.colorScheme.background,
    ) {
        val scrollState = rememberScrollState()

        Column(
            modifier = modifier.padding(20.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ){
            Text(stringResource(R.string.title, coroutinesCount), style = MaterialTheme.typography.titleMedium)

            Slider(
                value = coroutinesCount.toFloat(),
                onValueChange = {
                    val snapped = (it / 5f).toInt() * 5
                    onCountChange(snapped.coerceIn(10, 100))
                },
                valueRange = 10f..100f,
                steps = ((100 - 10) / 5) - 1,
                modifier = Modifier.fillMaxWidth()
            )

            Box{
                Button(onClick = onDropdownExpand) {
                    Text(stringResource(R.string.select_dispatcher_btn, selectedDispatcher.name))
                }
            }

            DropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = onDropdownDismiss
            ) {
                AppDispatchers.values().forEach { dispathcer ->
                    DropdownMenuItem(
                        onClick = { onDispatcherChange(dispathcer) },
                        text = {
                            Text(dispathcer.name, style = MaterialTheme.typography.bodyMedium)
                        }
                    )
                }
            }

            Card(){
                Column(modifier = Modifier.padding(16.dp)){
                    Text(stringResource(R.string.start_mode), style = MaterialTheme.typography.titleSmall)

                    Row(verticalAlignment = Alignment.CenterVertically){
                        Text(stringResource(R.string.sequential_mode), modifier = Modifier.weight(1f))

                        Switch(
                            checked = sequential,
                            onCheckedChange = { onSequentialChange() }
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically){
                        Text(stringResource(R.string.parallel_mode), modifier = Modifier.weight(1f))

                        Switch(
                            checked = parallel,
                            onCheckedChange = { onParallelChange() }
                        )
                    }
                }
            }

            Card(){
                Column(modifier = Modifier.padding(16.dp)){
                    Row(verticalAlignment = Alignment.CenterVertically){
                        Text(stringResource(R.string.delay_start), modifier = Modifier.weight(1f))

                        Switch(
                            checked = delayStart,
                            onCheckedChange = onDelayStartChange
                        )
                    }
                }
            }

            Card(){
                Column(modifier = Modifier.padding(16.dp)){
                    if(!loading){
                        Button(
                            onClick = onStart,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(stringResource(R.string.launch_coroutines_btn))
                        }
                    } else {
                        Button(
                            onClick = onCancel,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(stringResource(R.string.cancel_coroutines_btn))
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                            LinearProgressIndicator(Modifier.fillMaxWidth())

                            Text(stringResource(R.string.loading_text, coroutinesCount, selectedDispatcher.name))
                            Text(stringResource(R.string.mode_text) + " ${if (sequential) stringResource(R.string.sequential_mode_text) else stringResource(R.string.parallel_mode_text)}")

                            if (delayStart) {
                                Text(stringResource(R.string.delay_start_text), modifier = Modifier.fillMaxWidth())
                            }
                        }
                    }
                }
            }
        }
    }
}