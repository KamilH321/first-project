package ru.itis.buildconfig.api

interface BuildConfigProvider {

    fun getOMDbApiBaseUrl(): String

    fun getOMDbApiKey(): String
}