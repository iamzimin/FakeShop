package com.evg.database.domain.conventers

import androidx.room.TypeConverter
import com.evg.database.domain.models.SpecificationDBO
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object ProductsConverter {
    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun toStringList(data: String): List<String> {
        return Json.decodeFromString(data)
    }

    @TypeConverter
    fun fromSpecificationList(specifications: List<SpecificationDBO>): String {
        return Json.encodeToString(specifications)
    }

    @TypeConverter
    fun toSpecificationList(specificationsString: String): List<SpecificationDBO> {
        return Json.decodeFromString(specificationsString)
    }
}