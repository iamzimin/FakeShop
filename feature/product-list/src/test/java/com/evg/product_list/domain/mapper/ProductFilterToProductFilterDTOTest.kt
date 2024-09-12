package com.evg.product_list.domain.mapper

import com.evg.fakeshop_api.domain.models.ProductFilterDTO
import com.evg.fakeshop_api.domain.models.SortTypeDTO
import com.evg.product_list.domain.model.ProductFilter
import com.evg.product_list.domain.model.SortType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class ProductFilterToProductFilterDTOTest {

    companion object {
        @JvmStatic
        fun sortTypeProvider(): List<Arguments> {
            return listOf(
                Arguments.of(SortType.DEFAULT, SortTypeDTO.DEFAULT),
                Arguments.of(SortType.ASCENDING, SortTypeDTO.ASCENDING),
                Arguments.of(SortType.DESCENDING, SortTypeDTO.DESCENDING)
            )
        }
    }

    @ParameterizedTest
    @MethodSource("sortTypeProvider")
    fun `test SortType to SortTypeDTO conversion`(sortType: SortType, expectedSortTypeDTO: SortTypeDTO) {
        // When
        val actualSortTypeDTO = sortType.toSortType()

        // Then
        Assertions.assertEquals(expectedSortTypeDTO, actualSortTypeDTO)
    }

    @Test
    fun `test ProductFilter to ProductFilterDTO conversion`() {
        // Given
        val productFilter = ProductFilter(
            pageSize = 20,
            category = "Auto",
            sort = SortType.ASCENDING
        )

        val expected = ProductFilterDTO(
            pageSize = 20,
            category = "Auto",
            sort = SortTypeDTO.ASCENDING
        )

        // When
        val actual = productFilter.toProductFilterDTO()

        // Then
        Assertions.assertEquals(expected, actual)
    }
}
