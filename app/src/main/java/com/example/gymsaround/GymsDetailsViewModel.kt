package com.example.gymsaround

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class GymsDetailsViewModel(private val stateHandle: SavedStateHandle) : ViewModel() {
    val state = mutableStateOf<Gym?>(null)

    private var apiService: GymsApiService

    init {
        val retrofit: Retrofit = retrofit
        apiService = retrofit.create(GymsApiService::class.java)
        getGyms(6)
    }

    private fun getGyms(id: Int) {
        viewModelScope.launch {
            val gym = getGymsFromDB(id)
            state.value = gym
        }
    }

    private suspend fun getGymsFromDB(id: Int) =
        withContext(Dispatchers.IO) {
            apiService.getGym(id).values.first()
        }
}