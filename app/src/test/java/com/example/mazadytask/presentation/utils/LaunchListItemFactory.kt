package com.example.mazadytask.presentation.utils

import com.example.mazadytask.domain.model.LaunchListItem

object LaunchListItemFactory {


    fun create(
        id: String = "1",
        missionName: String = "FalconSat",
        site: String? = "Cape Canaveral",
        missionPatchUrl: String? =
            "https://st4.depositphotos.com/8345768/23766/i/450/depositphotos_237666660-stock-photo-closeup-head-tiger-black-background.jpg",
        isBooked: Boolean = false
    ): LaunchListItem {
        return LaunchListItem(
            id = id,
            missionName = missionName,
            site = site,
            missionPatchUrl = missionPatchUrl,
            isBooked = isBooked
        )
    }

    fun createList(
        size: Int = 3,
        isBooked: Boolean = false
    ): List<LaunchListItem> {
        return List(size) { index ->
            create(
                id = (index + 1).toString(),
                missionName = "Mission name is ${index + 1}",
                isBooked = isBooked
            )
        }
    }
}
