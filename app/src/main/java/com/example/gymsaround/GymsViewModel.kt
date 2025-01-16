package com.example.gymsaround

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class GymsViewModel(private val stateHandle: SavedStateHandle) : ViewModel() {
    var state by mutableStateOf(emptyList<Gym>())
    private var errorHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }
    private var apiService: GymsApiService

    init {
        val retrofit: Retrofit = retrofit
        apiService = retrofit.create(GymsApiService::class.java)
        getGyms()
    }

    private fun getGyms() {
        viewModelScope.launch(errorHandler) {
            val gyms = getGymsFromDB()
            state = gyms.restoreSelectedGym()
        }
    }

    private suspend fun getGymsFromDB() = withContext(Dispatchers.IO) { apiService.getGyms() }


    fun toggleFaveState(gymId: Int) {
        val gyms = state.toMutableList()
        val itemIndex = gyms.indexOfFirst { it.gymId == gymId }
        gyms[itemIndex] = gyms[itemIndex].copy(isFav = !gyms[itemIndex].isFav)
        storeSelectedGym(gyms[itemIndex])
        state = gyms
        Log.i("TAG2", "GymItem: $gymId")
    }

    private fun storeSelectedGym(gym: Gym) {
        val savedHandleList = stateHandle.get<List<Int>?>(FAV_IDS).orEmpty().toMutableList()
        if (gym.isFav) savedHandleList.add(gym.gymId)
        else savedHandleList.remove(gym.gymId)
        stateHandle[FAV_IDS] = savedHandleList
    }

    private fun List<Gym>.restoreSelectedGym(): List<Gym> {
        stateHandle.get<List<Int>?>(FAV_IDS)?.let { savedId ->
            savedId.forEach { gymId ->
                this.find { it.gymId == gymId }?.isFav = true
            }
        }
        return this
    }

    companion object {
        const val FAV_IDS = "favGymIDs"
    }
}