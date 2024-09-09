package com.evg.database.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.evg.database.domain.models.ProductDBO
import com.evg.database.domain.models.ProductFilterDB
import com.evg.database.domain.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductsPageSourceLocal @Inject constructor(
    private val databaseRepository: DatabaseRepository,
): PagingSource<Int, ProductDBO>() {
    var filter = ProductFilterDB()

    override fun getRefreshKey(state: PagingState<Int, ProductDBO>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductDBO> {
        val page = params.key ?: 0

        try {
            val products = withContext(Dispatchers.IO) {
                databaseRepository.getAllProductsByPage(
                    offset = page * filter.pageSize,
                    filter = filter,
                )
            }

            val prevKey = if (page == 0) null else page - 1
            val nextKey = if (products.isEmpty()) null else page + 1

            return LoadResult.Page(
                data = products,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}