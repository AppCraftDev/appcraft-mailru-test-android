package ru.mailru_test.data.global

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.FileProvider
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.File
import java.io.InputStream

fun String.getMediaType(): MediaType? {
    return if (endsWith(".pdf")) "application/pdf".toMediaTypeOrNull()
    else if (endsWith(".jpeg") || endsWith(".jpg")) "image/jpeg".toMediaTypeOrNull()
    else if (endsWith(".png")) "image/png".toMediaTypeOrNull()
    else if (endsWith(".bmp")) "image/bmp".toMediaTypeOrNull()
    else if (endsWith(".zip")) "application/zip".toMediaTypeOrNull()
    else if (endsWith(".gif")) "image/gif".toMediaTypeOrNull()
    else if (endsWith(".mp3")) "audio/mp3".toMediaTypeOrNull()
    else if (endsWith(".m4a")) "audio/m4a".toMediaTypeOrNull()
    else if (endsWith(".mp4")) "video/mp4".toMediaTypeOrNull()
    else if (endsWith(".mov")) "video/mov".toMediaTypeOrNull()
    else "application/*".toMediaTypeOrNull()
}

fun Context.isAppAvailableApp(appName: String): Boolean {
    val pm = packageManager
    return try {
        pm.getPackageInfo(appName, PackageManager.GET_ACTIVITIES)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}

fun Context.getProvider() = applicationContext.packageName.toString() + ".file_provider"

fun Context.getUriFromFile(file: File) = FileProvider.getUriForFile(this, getProvider(), file)

fun InputStream.toFile(file: File) {
    use { input ->
        file.outputStream().use { input.copyTo(it) }
    }
}