package com.example.mazadytask.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.example.mazadytask.data.mappers.toAppError
import com.example.mazadytask.domain.model.AppError
import com.example.mazadytask.domain.model.LaunchListItem
import com.example.mazadytask.graphql.LaunchListQuery

class LaunchesPagingSource(
    private val apollo: ApolloClient,
    private val pageSize: Int
) : PagingSource<String, LaunchListItem>() {

    override fun getRefreshKey(state: PagingState<String, LaunchListItem>): String? = null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, LaunchListItem> {
        return try {
            val cursor = params.key

            val response = apollo.query(
                LaunchListQuery(
                    after = Optional.presentIfNotNull(cursor),
                    pageSize = pageSize
                )
            ).execute()

            if (response.hasErrors()) {
                val msg = response.errors?.firstOrNull()?.message
                return LoadResult.Error(
                    AppErrorThrowable(AppError.Server(code = null, message = msg))
                )
            }

            val data = response.data?.launches
                ?: return LoadResult.Error(
                    AppErrorThrowable(AppError.Server(code = null, message = "No data available"))
                )

            val items = data.launches
                .filterNotNull()
                .map { launch ->
                    LaunchListItem(
                        id = launch.id.orEmpty(),
                        missionName = launch.mission?.name.orEmpty(),
                        site = launch.site,
                        missionPatchUrl = launch.mission?.missionPatch,
                        isBooked = launch.isBooked ?: false
                    )
                }

            val nextKey = if (data.hasMore) data.cursor else null

            LoadResult.Page(
                data = items,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (t: Throwable) {
            LoadResult.Error(AppErrorThrowable(t.toAppError()))
        }
    }
}
