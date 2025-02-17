package ir.lrn.kara.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.compose.foundation.pager.PagerState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import ir.lrn.kara.data.db.goals.GoalsTimeFrame
import kotlin.math.absoluteValue

fun openUri(context: Context, uri: Uri) {
    try {
        val mimeType: String? = context.contentResolver.getType(uri)
            ?: MimeTypeMap.getSingleton()
                .getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(uri.toString()))

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
        Toast.makeText(context, " خطا${e.message}", Toast.LENGTH_SHORT).show()
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

fun getGoalColor(status: String): Pair<Color, Color> {
    return when (status) {
        GoalsTimeFrame.SHORT.name -> Pair(Color(0xff6C66FF), Color(0xff334FDE))
        GoalsTimeFrame.MEDIUM.name -> Pair(Color(0xff66FFFC), Color(0xff00868D))
        GoalsTimeFrame.LONG.name -> Pair(Color(0xff66FF99), Color(0xff08B539))
        else -> Pair(Color(0xff6C66FF), Color(0xff334FDE))
    }
}
fun Modifier.applyQuizGraphics(pagerState: PagerState, page: Int): Modifier {
    return this.graphicsLayer {
        val pageOffset =
            ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
        alpha = lerp(start = 0.5f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f))
        scaleX = lerp(start = 0.85f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f))
        scaleY = scaleX
    }
}

