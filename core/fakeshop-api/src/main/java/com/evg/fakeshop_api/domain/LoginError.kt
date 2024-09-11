package com.evg.fakeshop_api.domain

enum class LoginError: Error {
    REQUEST_TIMEOUT,
    TOO_MANY_REQUESTS,
    SERVER_ERROR,
    SERIALIZATION,
    USER_NOT_FOUND,
    UNKNOWN,
}