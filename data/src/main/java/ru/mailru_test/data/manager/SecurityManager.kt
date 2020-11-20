package ru.mailru_test.data.manager

import java.io.File

interface SecurityManager {

    fun openLocalFile(inFile: File, outFile: File)
}