package ru.mailru_test.data.helper

import android.os.Build
import android.util.Base64
import ru.mailru_test.data.global.toFile
import java.io.File
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

object EncryptionHelper {

    private const val DEFAULT_ITERATIONS = 10000
    private const val DEFAULT_KEY_LENGTH = 256

    private val secretKeyFactory by lazy {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            SecretKeyFactory.getInstance("PBKDF2withHmacSHA1")
        } else {
            SecretKeyFactory.getInstance("PBKDF2withHmacSHA256")
        }
    }

    fun encrypt(passphraseOrPin: CharArray, salt: ByteArray): SecretKey {
        val keySpec = PBEKeySpec(passphraseOrPin, salt, DEFAULT_ITERATIONS, DEFAULT_KEY_LENGTH)
        return secretKeyFactory.generateSecret(keySpec)
    }

    fun encryptLocalFile(key: String, iv: String, fileIn: File, fileOut: File) {
        val keyBytes = key.toByteArray(Charsets.UTF_8)
        val ivBytes = iv.toByteArray(Charsets.UTF_8)

        val cipherText = Base64.decode(fileIn.readBytes(), Base64.DEFAULT)

        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val keySecret = SecretKeySpec(keyBytes, "AES")
        cipher.init(Cipher.DECRYPT_MODE, keySecret, IvParameterSpec(ivBytes))

        cipher.doFinal(cipherText).inputStream().toFile(fileOut)
    }
}