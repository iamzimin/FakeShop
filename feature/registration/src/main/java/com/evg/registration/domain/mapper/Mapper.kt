package com.evg.registration.domain.mapper

import com.evg.fakeshop_api.domain.models.RegistrationBody
import com.evg.fakeshop_api.domain.models.RegistrationResponse
import com.evg.registration.domain.model.RegistrationStatus
import com.evg.registration.domain.model.User

fun User.toRegistrationBody(): RegistrationBody {
    return RegistrationBody(
        name = this.name,
        email = this.email,
        password = this.password,
        cpassword = this.cpassword
    )
}

fun RegistrationResponse?.toRegistrationStatus(): RegistrationStatus {
    return RegistrationStatus(
        status = this?.status?: "fail",
        message = this?.message,
    )
}