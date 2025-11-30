package com.example.firstproject

import android.app.Application
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.firstproject.exceptions.ResetSettingException
import com.example.firstproject.exceptions.ShowSnackBarException
import com.example.firstproject.exceptions.ShowToastException
import com.example.firstproject.ui.compose.CoroutineLauncherScreen
import com.example.firstproject.ui.theme.FirstProjectTheme
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import com.example.firstproject.models.AppDispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    private val appContext: Application get() = application;

    var coroutinesCount by mutableStateOf(10)
    var selectedDispatcher by mutableStateOf(AppDispatchers.DEFAULT)
    var sequential by mutableStateOf(true)
    var parallel by mutableStateOf(false)
    var delayStart by mutableStateOf(false)
    var loading by mutableStateOf(false)
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private var operationScope: CoroutineScope? = null

    private var completedCoroutines = 0


    private val snackbarHostState by lazy { SnackbarHostState() }
    private val uiExceptionHandler by lazy { createExceptionHandler() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirstProjectTheme {

                CoroutineLauncherScreen(
                    coroutinesCount = coroutinesCount,
                    selectedDispatcher = selectedDispatcher,
                    sequential = sequential,
                    parallel = parallel,
                    delayStart = delayStart,
                    loading = loading,
                    snackbarHostState = snackbarHostState,
                    onCountChange = {coroutinesCount = it},
                    onDispatcherChange = {selectedDispatcher = it},
                    onSequentialChange = { enableSequential() },
                    onParallelChange = { enableParallel() },
                    onDelayStartChange = { delayStart = it},
                    onStart = {
                        startCoroutines(uiExceptionHandler)
                    },
                    onCancel = { cancelCoroutines() }
                )
            }
        }
    }

    private fun createExceptionHandler(): CoroutineExceptionHandler{
        return CoroutineExceptionHandler { _, exception ->
            when(exception){
                is ShowSnackBarException -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        snackbarHostState.showSnackbar(
                            message = exception.message ?: appContext.getString(R.string.snackbar_exception_text),
                            duration = SnackbarDuration.Long
                        )
                    }
                }
                else -> {
                    CoroutineScope(Dispatchers.Main).launch(Dispatchers.Main) {
                        handleException(exception)
                    }
                }
            }
        }
    }

    private fun handleException(exception: Throwable){
        when(exception){
            is ShowToastException -> {
                Toast.makeText(
                    appContext,
                    exception.message ?: appContext.getString(R.string.toast_exception_text),
                    Toast.LENGTH_LONG
                ).show()
            }
            is ShowSnackBarException -> {
                Toast.makeText(
                    appContext,
                    exception.message ?: appContext.getString(R.string.snackbar_exception_text),
                    Toast.LENGTH_LONG
                ).show()
            }
            is ResetSettingException -> {
                Toast.makeText(
                    appContext,
                    exception.message ?: appContext.getString(R.string.reset_exception_text),
                    Toast.LENGTH_LONG
                ).show()
                resetToDefaults()
            }
            else -> {
                Toast.makeText(
                    appContext,
                    exception.message ?: appContext.getString(R.string.unknown_exception_text),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun resetToDefaults(){
        coroutinesCount = 10
        selectedDispatcher = AppDispatchers.DEFAULT
        sequential = true
        parallel = false
        delayStart = false
    }

    private fun enableSequential(){
        sequential = true
        parallel = false
    }

    private fun enableParallel(){
        sequential = false
        parallel = true
    }

    private fun startCoroutines(uiExceptionHandler: CoroutineExceptionHandler){
        loading = true
        completedCoroutines = 0

        scope.launch(uiExceptionHandler){
            if(delayStart){
                delay(2000L)
            }

            operationScope = CoroutineScope(
                SupervisorJob() + selectedDispatcher.dispatchers + uiExceptionHandler
            )

            val opScope = operationScope!!

            try{
                if(sequential){
                    runSequential(opScope)
                } else{
                    runParallel(opScope)
                }
            } catch (e: Exception){
                withContext(Dispatchers.Main) {
                    handleException(e)
                }
            } finally {
                withContext(Dispatchers.Main){
                    loading = false
                }
            }
        }
    }

    private suspend fun runSequential(operationScope: CoroutineScope){
        repeat(coroutinesCount){ index ->

            try{
                launchTask(operationScope, index + 1).join()
            } catch (e: Exception){
                println("Error joining task ${index + 1}: ${e.message}")
            }
        }
    }

    private suspend fun runParallel(operationScope: CoroutineScope){
        val jobs = List(coroutinesCount) {index ->
            launchTask(operationScope, index + 1)
        }

        jobs.joinAll()
    }

    private fun launchTask(operationScope: CoroutineScope, taskNumber: Int): Job =
        operationScope.launch {
            val delayTime = Random.nextLong(1000L, 10000L)

            delay(delayTime)

            if (delayTime >= 7000L && Random.nextInt(100) < 30){
                when (Random.nextInt(3)){
                    0 -> throw ShowToastException(
                        appContext.getString(
                            R.string.toast_exception_pattern,
                            taskNumber,
                            delayTime)
                    )
                    1 -> throw ShowSnackBarException(
                        appContext.getString(
                            R.string.snackbar_exception_pattern,
                            taskNumber,
                            delayTime)
                    )
                    2 -> throw ResetSettingException(
                        appContext.getString(
                            R.string.reset_exception_pattern,
                            taskNumber,
                            delayTime)
                    )
                }
            }

            println("Задача $taskNumber успешно завершена за ${delayTime}ms")
            completedCoroutines++
        }

    private fun cancelCoroutines(){
        val scopeToCancel = operationScope ?: return

        val total = coroutinesCount
        val completed = completedCoroutines
        val cancelled = total - completed

        scopeToCancel.cancel()

        Toast.makeText(
            appContext,
            appContext.getString(R.string.cancelled_count, cancelled),
            Toast.LENGTH_LONG
        ).show()

        loading = false
        operationScope = null

    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}