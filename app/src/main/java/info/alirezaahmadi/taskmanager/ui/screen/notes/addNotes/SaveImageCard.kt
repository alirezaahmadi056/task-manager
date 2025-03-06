package info.alirezaahmadi.taskmanager.ui.screen.notes.addNotes

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.util.TaskHelper

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SaveImageCard(
    uri: Uri,
    context: Context,
    addIconDelete: @Composable (() -> Unit)? = null,
) {

    Card(
        onClick = {
            openUri(context,uri)
        },
        border = BorderStroke(1.dp, Color.LightGray),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 3.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            addIconDelete?.invoke() // فراخوانی تابع اختیاری

            Column(
                modifier = Modifier
                    .padding(end = 8.dp, top = 3.dp)
                    .weight(0.6f),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = getFileMetaData(context, uri)?.name ?:"پیوست پیش فرض",
                    color = MaterialTheme.colorScheme.scrim,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = TaskHelper.taskByLocate(formatFileSize(getFileMetaData(context, uri)?.size ?: 0)),
                    color = MaterialTheme.colorScheme.scrim.copy(0.6f),
                    style = MaterialTheme.typography.bodyMedium,

                    )
            }

            GlideImage(
                model = uri,
                contentDescription = "Image",
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
            ) {
                it.placeholder(R.drawable.document_ic)
            }


        }
    }
}

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