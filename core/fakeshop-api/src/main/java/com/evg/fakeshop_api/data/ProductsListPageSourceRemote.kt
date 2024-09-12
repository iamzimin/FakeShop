package com.evg.fakeshop_api.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.evg.fakeshop_api.domain.NetworkError
import com.evg.fakeshop_api.domain.Result
import com.evg.fakeshop_api.domain.models.ProductFilterDTO
import com.evg.fakeshop_api.domain.models.ProductResponse
import com.evg.fakeshop_api.domain.repository.FakeShopApiRepository
import javax.inject.Inject

class ProductsListPageSourceRemote @Inject constructor(
    private val fakeShopApiRepository: FakeShopApiRepository,
): PagingSource<Int, Result<ProductResponse, NetworkError>>() {
    var filter = ProductFilterDTO()

    override fun getRefreshKey(state: PagingState<Int, Result<ProductResponse, NetworkError>>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result<ProductResponse, NetworkError>> {
        val page = params.key ?: 1

        val response = fakeShopApiRepository.getAllProductsListByPage(
            page = page,
            filter = filter,
        )

        return when (response) {
            is Result.Success -> {
                val products = response.data.productsList ?: return LoadResult.Page(
                    data = listOf(Result.Error(NetworkError.UNKNOWN)),
                    prevKey = null,
                    nextKey = null
                )
                val count = response.data.count ?: return LoadResult.Page(
                    data = listOf(Result.Error(NetworkError.UNKNOWN)),
                    prevKey = null,
                    nextKey = null
                )

                val nextPage = if (count >= products.size) {
                    page + 1
                } else {
                    null
                }

                LoadResult.Page(
                    data = products.map { Result.Success(it) },
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = nextPage
                )
            }
            is Result.Error -> {
                LoadResult.Page(
                    data = listOf(Result.Error(response.error)),
                    prevKey = null,
                    nextKey = null
                )
            }
        }
    }
}