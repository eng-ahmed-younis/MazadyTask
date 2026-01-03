package com.example.mazadytask.domain.model

data class LaunchDetails(
    val id: String ,
    val missionName: String,
    val site: String? = null,
    val missionPatchSmall: String? = null,
    val missionPatchLarge: String? = null,
    val rocketDetails: RocketDetails? = null,
    val isBooked: Boolean = false
)


data class RocketDetails(
    val id: String,
    val name: String,
    val type: String
)