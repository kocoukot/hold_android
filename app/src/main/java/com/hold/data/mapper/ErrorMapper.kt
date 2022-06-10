package com.hold.data.mapper

import com.hold.data.network.model.response.ErrorResponse
import com.hold.data.network.support.ServiceException


class ErrorMapper : Mapper<ErrorResponse, ServiceException>(
    fromData = {
        ServiceException(
            it.errorCode,
            it.message
        )
    }
)
