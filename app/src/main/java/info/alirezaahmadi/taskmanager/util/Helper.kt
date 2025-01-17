package info.alirezaahmadi.taskmanager.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import android.widget.Toast


fun openUri(context: Context, uri: Uri) {
    try {
        val mimeType: String? = context.contentResolver.getType(uri)
            ?: MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(uri.toString()))

        if (mimeType != null) {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, mimeType)
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            }
            context.startActivity(intent)
        } else {
            throw IllegalArgumentException("Unknown MIME type")
        }
    } catch (e: Exception) {
        e.printStackTrace()
        // Handling the error, e.g., showing a Toast message to the user
        Toast.makeText(context, "Unable to open the file: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}

data class FileMetaData(val name: String, val size: Long)

fun getFileMetaData(context: Context, uri: Uri): FileMetaData? {
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    return cursor?.use {
        if (it.moveToFirst()) {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            val sizeIndex = it.getColumnIndex(OpenableColumns.SIZE)
            val name = if (nameIndex != -1) it.getString(nameIndex) else ""
            val size = if (sizeIndex != -1) it.getLong(sizeIndex) else -1L
            FileMetaData(name, size)
        } else null
    }
}

fun formatFileSize(size: Long): String {
    return if (size < 1024) {
        "$size bytes"
    } else if (size < 1024 * 1024) {
        "%.2f KB".format(size / 1024.0)
    } else {
        "%.2f MB".format(size / (1024.0 * 1024.0))
    }
}