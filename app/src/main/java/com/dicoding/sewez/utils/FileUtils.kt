package com.dicoding.sewez.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun createCustomTempFile(context: Context): File {
    val tempFile = File.createTempFile("upload_image", ".jpg", context.cacheDir)
    tempFile.deleteOnExit()
    return tempFile
}

fun reduceFileImage(file: File, maxSize: Int = 1000): File {
    val bitmap = BitmapFactory.decodeFile(file.path)
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outputStream)
    val bitmapData = outputStream.toByteArray()

    var quality = 85
    while (bitmapData.size / 1024 > maxSize && quality > 5) {
        outputStream.reset()
        quality -= 5
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
    }

    try {
        file.createNewFile()
        val fos = FileOutputStream(file)
        fos.write(outputStream.toByteArray())
        fos.flush()
        fos.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return file
}