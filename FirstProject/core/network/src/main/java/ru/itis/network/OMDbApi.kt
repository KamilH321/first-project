package ru.itis.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.itis.network.pojo.OMDbResponse

interface OMDbApi {

    @GET("/")
    suspend fun getDataByQuery(
        @Query(value = "s") query: String
    ) : OMDbResponse
}