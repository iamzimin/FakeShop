package com.evg.fakeshop_api.domain.mapper

import com.evg.fakeshop_api.domain.models.SpecificationResponse
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class ProductSpecificationsResponseDeserializer : JsonDeserializer<SpecificationResponse> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): SpecificationResponse? {
        if (!json.isJsonObject) {
            return null
        }

        val jsonObject = json.asJsonObject

        return try {
            val key = jsonObject.get("key")?.asString
            val value = jsonObject.get("value")?.asString

            if (value != null) {
                SpecificationResponse(key, value)
            } else {
                null
            }
        } catch (e: JsonParseException) {
            null
        }
    }
}