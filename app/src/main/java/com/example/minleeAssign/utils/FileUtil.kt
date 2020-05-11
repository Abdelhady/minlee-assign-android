package com.example.minleeAssign.utils

import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.TextUtils
import com.example.minleeAssign.App
import timber.log.Timber
import java.io.*


object FileUtil {

    fun getRealPathFromURI(contentUri: Uri?): String? {
        val publicPath = getPublicPathIfAvailable(contentUri!!)
        return publicPath ?: createPrivateCopyFromStreamedUri(contentUri)
    }

    /**
     * Creates local copy of the shared file (contentUri),
     * in our cache folder, so we can handle it easily.
     *
     * @param contentUri received uri from share-via functionality
     * @return filePath of the locally created copy
     */
    fun createPrivateCopyFromStreamedUri(contentUri: Uri): String? {
        Timber.d("creating a local copy of the received file")
        var inputStream: InputStream? = null
        var filePath: String? = null
        if (contentUri.authority != null) {
            try {
                inputStream = App.applicationContext().getContentResolver()
                    .openInputStream(contentUri) // context needed
                val photoFile: File = createTemporalFileFrom(inputStream, contentUri)!!
                filePath = photoFile.getPath()
            } catch (e: FileNotFoundException) {
                Timber.e(e)
            } catch (e: IOException) {
                Timber.e(e)
            } finally {
                try {
                    inputStream?.close()
                } catch (e: Exception) {
                    // `inputStream` could be null because stream couldn't be opened
                    Timber.e(e)
                }
            }
        }
        return filePath
    }

    /**
     * Return the original file path if it is a public path,
     * like a file from Downloads or Camera folders,
     *
     *
     * unlike other files, they may be private, like an image from WhatsApp,
     * where it exists in WhatsApp's internal private cache folder
     *
     * @param uri
     * @return
     */
    private fun getPublicPathIfAvailable(uri: Uri): String? {
        val returnCursor: Cursor =
            App.applicationContext().getContentResolver().query(uri, null, null, null, null)!!
        returnCursor.moveToFirst()
        var publicPath: String? = null
        // Reference: file:///media/e/ide/android/sdk/docs/reference/android/provider/MediaStore.MediaColumns.html
        val pathIndex: Int =
            returnCursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA) // will return -1 if doesn't exist
        if (pathIndex > -1) {
            publicPath = returnCursor.getString(pathIndex)
            Timber.d("found Original Path: %s", publicPath)
        }
        returnCursor.close()
        return publicPath
    }

    @Throws(IOException::class)
    private fun createTemporalFileFrom(
        inputStream: InputStream?,
        fileUri: Uri
    ): File? {
        var targetFile: File? = null
        if (inputStream != null) {
            var read: Int
            val buffer = ByteArray(8 * 1024)
            targetFile = createTemporalFile(fileUri)
            val outputStream: OutputStream = FileOutputStream(targetFile)
            while (inputStream.read(buffer).also { read = it } != -1) {
                outputStream.write(buffer, 0, read)
            }
            outputStream.flush()
            try {
                outputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return targetFile
    }

    fun createTemporalFile(fileUri: Uri): File? {
        return createTemporalFile(fileUri, "")
    }

    fun createTemporalFile(fileUri: Uri, suffix: String): File? {
        var fileName: String = getFileNameByUri(fileUri)
        val lastDot = fileName.lastIndexOf('.')
        if (!TextUtils.isEmpty(suffix) && lastDot > 0) {
            fileName = fileName.substring(0, lastDot) + suffix + fileName.substring(lastDot)
        }
        return File(App.applicationContext().getExternalCacheDir(), fileName)
    }

    fun getFileNameByUri(uri: Uri): String {
        val returnCursor: Cursor =
            App.applicationContext().getContentResolver().query(uri, null, null, null, null)!!
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        val name = returnCursor.getString(nameIndex)
        Timber.d("Original File Name: %s", name)
        returnCursor.close()
        return name
    }

}