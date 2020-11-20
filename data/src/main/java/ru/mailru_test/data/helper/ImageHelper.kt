package ru.mailru_test.data.helper

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream

object ImageHelper {

    fun compressBitmap(contentResolver: ContentResolver, uri: Uri): ByteArray {
        val out = ByteArrayOutputStream()
        val inputStream = contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, out)
        return out.toByteArray()
    }
}