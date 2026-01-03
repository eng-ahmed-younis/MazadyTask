package com.example.mazadytask.data.mappers

import com.example.mazadytask.domain.model.LaunchDetails
import com.example.mazadytask.domain.model.LaunchListItem
import com.example.mazadytask.domain.model.Page
import com.example.mazadytask.domain.model.RocketDetails
import com.example.mazadytask.graphql.LaunchDetailsQuery
import com.example.mazadytask.graphql.LaunchListQuery

fun LaunchListQuery.Launches.toDomainLaunchListPage(): Page<LaunchListItem> {
   val launches = launches.mapNotNull { it?.toDomainLaunchListItem() }

    return Page(
        items = launches,
        nextCursor = cursor,
        hasMore = hasMore
    )
}

fun LaunchListQuery.Launch.toDomainLaunchListItem() : LaunchListItem {
    return LaunchListItem(
        id = id,
        missionName =mission?.name ?: "Unknown mission",
        site = site,
        missionPatchUrl = mission?.missionPatch,
        isBooked = isBooked
    )
}

fun LaunchDetailsQuery.Launch.toDomainLaunchDetails(): LaunchDetails =
    LaunchDetails(
        id = id,
        missionName = mission?.name ?: "Unknown mission",
        site = site,
        missionPatchLarge = mission?.missionPatchLarge,
        missionPatchSmall = mission?.missionPatchSmall,
        rocketDetails = RocketDetails(
            id = rocket?.id ?: "",
            name = rocket?.name ?: "",
            type = rocket?.type ?: ""
        ),
        isBooked = isBooked
    )