package com.barco.pokedex.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.barco.pokedex.api.PokeService
import com.barco.pokedex.model.PokemonOverview
import retrofit2.HttpException
import java.io.IOException

class PokemonListDataSource(
    private val service: PokeService,
) : PagingSource<Int, PokemonOverview>() { //TODO: Encapsulate and test the paging index logic

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonOverview> {
        return try {
            val currentPage = params.key ?: 1
            val offset = (currentPage - 1) * NETWORK_PAGE_SIZE + 1
            val movies = service.getList(
                offset = offset,
                limit = params.loadSize
            ).results
            val nextKey = if (movies.isEmpty()) {
                null
            } else {
                currentPage + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = movies,
                prevKey = null, //TODO: Support paging backward
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonOverview>): Int? {
        return state.anchorPosition
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}