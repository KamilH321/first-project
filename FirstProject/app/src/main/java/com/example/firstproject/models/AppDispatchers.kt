package com.example.firstproject.models

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

enum class AppDispatchers (val dispatchers: CoroutineDispatcher){
    DEFAULT(Dispatchers.Default),
    IO(Dispatchers.IO),
    MAIN(Dispatchers.Main),
    UNCONFINED(Dispatchers.Unconfined)
}