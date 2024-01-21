package com.ivanshalimov.practicecoroutinecoursesais.network

import retrofit2.http.GET
import retrofit2.http.Query

interface FreeService {

    @GET("activity")
    suspend fun getResponse(
        @Query("participants")
        participants: Int = 1
    ): Root
}