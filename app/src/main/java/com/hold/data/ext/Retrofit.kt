package com.hold.data.ext

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

fun File.toPart(partName: String): MultipartBody.Part {
    return MultipartBody.Part.createFormData(
        partName,
        name,
        asRequestBody(("image/$extension".toMediaType()))
    )
}

