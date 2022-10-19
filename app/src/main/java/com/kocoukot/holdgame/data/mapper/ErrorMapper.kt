package com.kocoukot.holdgame.data.mapper

import com.kocoukot.holdgame.data.network.model.response.ErrorResponse
import com.kocoukot.holdgame.data.network.support.ServiceException


class ErrorMapper : Mapper<ErrorResponse, ServiceException>(
    fromData = {
        ServiceException(
            it.errorCode,
            it.message
        )
    }
)
