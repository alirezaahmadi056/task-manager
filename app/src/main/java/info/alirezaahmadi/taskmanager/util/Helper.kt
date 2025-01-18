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
            throw IllegalArgumentException("نوع فایل نامشخص!")
        }
    } catch (e: Exception) {
        Toast.makeText(context, "خطا${e.message}", Toast.LENGTH_SHORT).show()
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


fun Long.toDetailedTimeString(): String {
    val totalSeconds = this
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60

    return when {
        hours > 0 && minutes > 0 && seconds > 0 -> "$hours ساعت و $minutes دقیقه و $seconds ثانیه"
        hours > 0 && minutes > 0 -> "$hours ساعت و $minutes دقیقه"
        hours > 0 && seconds > 0 -> "$hours ساعت و $seconds ثانیه"
        hours > 0 -> "$hours ساعت"
        minutes > 0 && seconds > 0 -> "$minutes دقیقه و $seconds ثانیه"
        minutes > 0 -> "$minutes دقیقه"
        else -> "$seconds ثانیه"
    }
}
fun getOrdinalInPersian(number: Int): String {
    val ones = listOf(
        "", "اول", "دوم", "سوم", "چهارم", "پنجم",
        "ششم", "هفتم", "هشتم", "نهم"
    )
    val tens = listOf(
        "", "دهم", "بیستم", "سی‌ام", "چهلم",
        "پنجاهم", "شصتم", "هفتادم", "هشتادم", "نودم"
    )
    val teens = listOf(
        "یازدهم", "دوازدهم", "سیزدهم", "چهاردهم",
        "پانزدهم", "شانزدهم", "هفدهم", "هجدهم", "نوزدهم"
    )

    if (number < 1) return "عدد نامعتبر"

    val hundreds = number / 100
    val remainder = number % 100

    val hundredPart = if (hundreds > 0) {
        if (hundreds == 1) "صدم" else "${ones[hundreds]} صد"
    } else ""

    val remainderPart = when {
        remainder == 0 -> ""
        remainder <= 9 -> ones[remainder]
        remainder == 10 -> tens[1]
        remainder in 11..19 -> teens[remainder - 11]
        remainder % 10 == 0 -> tens[remainder / 10]
        else -> tens[remainder / 10] + " و " + ones[remainder % 10]
    }

    return if (hundredPart.isNotEmpty() && remainderPart.isNotEmpty()) {
        "$hundredPart و $remainderPart"
    } else {
        hundredPart + remainderPart
    }
}
