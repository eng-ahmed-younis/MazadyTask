package com.example.mazadytask.presentation.utils

import com.example.mazadytask.domain.model.LaunchDetails
import com.example.mazadytask.domain.model.RocketDetails

object LaunchDetailsFactory {

    fun create(
        id: String = "1",
        missionName: String = "FalconSat",
        site: String? = "Cape Canaveral",
        missionPatchSmall: String? =
            "https://example.com/mission_patch_small.png",
        missionPatchLarge: String? =
            "https://example.com/mission_patch_large.png",
        rocketDetails: RocketDetails? = createRocketDetails(),
        isBooked: Boolean = false
    ): LaunchDetails {
        return LaunchDetails(
            id = id,
            missionName = missionName,
            site = site,
            missionPatchSmall = missionPatchSmall,
            missionPatchLarge = missionPatchLarge,
            rocketDetails = rocketDetails,
            isBooked = isBooked
        )
    }

    fun createRocketDetails(
        id: String = "rocket_1",
        name: String = "Falcon 9",
        type: String = "Reusable"
    ): RocketDetails {
        return RocketDetails(
            id = id,
            name = name,
            type = type
        )
    }
}
