package com.example.firstproject.repository

import androidx.compose.runtime.mutableStateListOf
import com.example.firstproject.models.Message

object MessageRepository{
    private val messages = mutableStateListOf<Message>()

    fun addMessage(message: Message){
        messages.add(message)
    }

    fun getAll(): List<Message> = messages.toList()

    fun clear(){
        messages.clear()
    }
}