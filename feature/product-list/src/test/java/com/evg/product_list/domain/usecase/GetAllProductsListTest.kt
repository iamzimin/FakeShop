package com.evg.product_list.domain.usecase

import androidx.paging.PagingData
import com.evg.fakeshop_api.domain.utils.NetworkError
import com.evg.fakeshop_api.domain.utils.Result
import com.evg.product_list.domain.model.Product
import com.evg.product_list.domain.model.ProductFilter
import com.evg.product_list.domain.model.SortType
import com.evg.product_list.domain.repository.ProductListRepository
import com.evg.product_list.utils.MockFactory.createMockProduct
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito.mock
import org.mockito.Mockito.reset
import org.mockito.Mockito.`when`
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class GetAllProductsListTest {
    companion object {
        @JvmStatic
        fun filterProvider(): List<ProductFilter> {
            return listOf(
                ProductFilter(
                    pageSize = 10,
                    category = null,
                    sort = SortType.DEFAULT
                ),
                ProductFilter(
                    pageSize = 30,
                    category = "Auto",
                    sort = SortType.DESCENDING
                ),
                ProductFilter(
                    pageSize = 20,
                    category = "Table",
                    sort = SortType.ASCENDING
                ),
            )
        }
    }

    private val productListRepository = mock<ProductListRepository>()
    private lateinit var getAllProductsList: GetAllProductsList

    @BeforeEach
    fun setUp() {
        getAllProductsList = GetAllProductsList(productListRepository)
    }

    @AfterEach
    fun tearDown() {
        reset(productListRepository)
    }


    @ParameterizedTest
    @MethodSource("filterProvider")
    fun `invoke should return products from repository`(filter: ProductFilter): Unit = runBlocking {
        // Given
        val products = listOf(createMockProduct(1), createMockProduct(2))
        val expected: PagingData<Result<Product, NetworkError>> = PagingData.from(
            products.map { product ->
                Result.Success(product)
            }
        )

        `when`(productListRepository.getAllProductsList(filter = filter)).thenReturn(flowOf(expected))

        // When
        val flowResult = getAllProductsList.invoke(filter = filter)

        // Then
        var actual: PagingData<Result<Product, NetworkError>>? = null
        flowResult.collect { pagingData ->
            actual = pagingData
        }

        Assertions.assertEquals(actual, expected)
        verify(productListRepository, times(1)).getAllProductsList(filter = filter)
    }
}