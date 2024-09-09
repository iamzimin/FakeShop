package com.evg.fakeshop_api.data

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.evg.fakeshop_api.domain.models.ProductFilterDTO
import com.evg.fakeshop_api.domain.models.ProductResponse
import com.evg.fakeshop_api.domain.repository.FakeShopApiRepository
import com.evg.fakeshop_api.domain.service.FakeShopApi
import javax.inject.Inject

class ProductsListPageSourceRemote @Inject constructor(
    private val fakeShopApiRepository: FakeShopApiRepository,
): PagingSource<Int, ProductResponse>() {
    var filter = ProductFilterDTO()

    override fun getRefreshKey(state: PagingState<Int, ProductResponse>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductResponse> {
        val page = params.key ?: 1

        return try {
            val response = fakeShopApiRepository.getAllProductsListByPage(
                page = page,
                filter = filter,
            )
            val products = response?.productsList ?: emptyList() //TODO
            val count = response?.count ?: 0

            val nextPage = if (count >= products.size) {
                page + 1
            } else {
                null
            }
            LoadResult.Page(
                data = products,
                prevKey = if (page == 1) null else page - 1,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private fun getPage(page: String?): Int? {
        if (page == null) return null

        val uri = Uri.parse(page)
        val pageQuery = uri.getQueryParameter("page")
        return pageQuery?.toInt()
    }
}