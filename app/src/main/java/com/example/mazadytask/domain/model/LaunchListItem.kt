package com.example.mazadytask.domain.model

data class LaunchListItem(
    val id: String,
    val missionName: String,
    val site: String?,
    val missionPatchUrl: String?,
    val isBooked: Boolean
)
