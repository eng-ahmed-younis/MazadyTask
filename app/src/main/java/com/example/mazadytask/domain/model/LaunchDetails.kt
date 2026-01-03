package com.example.mazadytask.domain.model

data class LaunchDetails(
    val id: String,
    val missionName: String,
    val site: String?,
    val missionPatchSmall: String?,
    val missionPatchLarge: String?,
    val rocketName: String?,
    val rocketType: String?,
    val isBooked: Boolean
)
