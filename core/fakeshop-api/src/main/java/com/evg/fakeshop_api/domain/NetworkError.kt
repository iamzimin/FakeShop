package com.evg.fakeshop_api.domain

enum class NetworkError: Error {
    REQUEST_TIMEOUT,
    TOO_MANY_REQUESTS,
    SERVER_ERROR,
    //RESPONSE_FAIL,
    SERIALIZATION,
    UNKNOWN,
}