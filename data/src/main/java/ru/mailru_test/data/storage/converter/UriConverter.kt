package ru.mailru_test.data.storage.converter

import android.net.Uri
import androidx.room.TypeConverter

class UriConverter {

    @TypeConverter
    fun uriFromString(value: String?): Uri? {
        return value?.let { Uri.parse(it) }
    }

    @TypeConverter
    fun uriToString(uri: Uri?): String? {
        return uri?.toString()
    }
}