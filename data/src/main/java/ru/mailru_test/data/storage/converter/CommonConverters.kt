package ru.mailru_test.data.storage.converter

import android.net.Uri
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import ru.mailru_test.domain.global.fromJsonList
import ru.mailru_test.domain.global.toJson
import java.util.*

class CommonConverters {
    private val moshi = Moshi.Builder()
//        .add(KotlinJsonAdapterFactory())
        .build()

    @TypeConverter
    fun dateFromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun stringListFromJson(content: String?): List<String>? {
        return content?.let { moshi.fromJsonList(it) }
    }

    @TypeConverter
    fun stringListToJson(content: List<String>?): String? {
        return moshi.toJson(content)
    }

    @TypeConverter
    fun uriFromJson(content: String?): Uri? {
        return content?.let { Uri.parse(it) }
    }

    @TypeConverter
    fun uriToJson(content: Uri?): String? {
        return content?.toString()
    }

    @TypeConverter
    fun uriListToJson(content: List<Uri>?): String? {
        return moshi.toJson(content?.map { it.toString() })
    }

    @TypeConverter
    fun uriListFromJson(content: String?): List<Uri>? {
        return content?.let { moshi.fromJsonList<String>(it) }?.map { Uri.parse(it) }
    }
}