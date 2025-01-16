package com.example.gymsaround

import com.google.gson.annotations.SerializedName

data class Gym(
    val gymId: Int,
    @SerializedName("gym_name")
    val name: String,
    @SerializedName("gym_location")
    val place: String,
    @SerializedName("is_open")
    val isOpen: String,
    var isFav: Boolean = false
)