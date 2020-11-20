package ru.mailru_test.data.manager

import android.util.Base64
import ru.mailru_test.data.helper.EncryptionHelper
import ru.mailru_test.resources.Constants.IV_LOCAL_FILES
import ru.mailru_test.resources.Constants.KEY_LOCAL_FILES
import java.io.File

class SecurityManagerImpl(
    private val preferences: AppPreferencesManager
) : SecurityManager {

    @ExperimentalStdlibApi
    override fun openLocalFile(inFile: File, outFile: File) {
        val keyDecode = Base64.decode(KEY_LOCAL_FILES, Base64.DEFAULT).decodeToString().padEnd(16)
        val ivDecode = Base64.decode(IV_LOCAL_FILES, Base64.DEFAULT).decodeToString().padEnd(16)

        EncryptionHelper.encryptLocalFile(keyDecode, ivDecode, inFile, outFile)
    }
}