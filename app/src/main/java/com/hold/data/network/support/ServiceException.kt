package com.hold.data.network.support

class ServiceException(val code: Int, message: String?) : Throwable(message)
