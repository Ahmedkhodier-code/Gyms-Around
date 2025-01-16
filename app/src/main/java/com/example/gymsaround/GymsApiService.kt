package com.example.gymsaround

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GymsApiService {

    @GET("gyms.json")
    suspend fun getGyms(): List<Gym>

    @GET("gyms.json?orderBy=\"id\"")
    suspend fun getGym(@Query("equalTo") id: Int): Map<String, Gym>
}

val retrofit = Retrofit.Builder()
    .baseUrl("https://cairo-gyms-76382-default-rtdb.firebaseio.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

