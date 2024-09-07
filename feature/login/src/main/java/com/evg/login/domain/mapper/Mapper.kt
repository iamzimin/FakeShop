package com.evg.login.domain.mapper

import com.evg.fakeshop_api.domain.models.LoginBody
import com.evg.fakeshop_api.domain.models.LoginResponse
import com.evg.login.domain.model.LoginStatus
import com.evg.login.domain.model.User

fun User.toLoginBody(): LoginBody {
    return LoginBody(
        email = this.email,
        password = this.password,
    )
}

fun LoginResponse?.toLoginStatus(): LoginStatus {
    return LoginStatus(
        status = this?.status?: "fail",
        message = this?.message,
        token = this?.token,
    )
}