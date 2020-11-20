package ru.mailru_test.data.helper

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

object UploadHelper {

    fun getImagePart(data:ByteArray, index: Int = 0): MultipartBody.Part {
        val requestBody = data.toRequestBody("image/jpeg".toMediaTypeOrNull())
        val filePartName = if (index == 0) "file" else "file$index"
        return MultipartBody.Part.createFormData(filePartName, "file", requestBody)
    }

    fun getTextPart(type: String, text: String): MultipartBody.Part {
        return MultipartBody.Part.createFormData(type, text)
    }
}