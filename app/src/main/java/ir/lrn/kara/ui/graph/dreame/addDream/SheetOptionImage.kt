package ir.lrn.kara.ui.graph.dreame.addDream

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.ImageSearch
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ir.lrn.kara.util.openUri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SheetOptionImage(
    uri: Uri?,
    context: Context,
    onDeleted: () -> Unit,
    onCovered: () -> Unit,
    onDismiss: () -> Unit,
) {

    if (uri == null) return
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.background,
        shape = RoundedCornerShape(12.dp)
    ) {
        Spacer(Modifier.height(8.dp))
        SheetItem(
            text = "مشاهده عکس",
            icon = {
                Icon(
                    imageVector = Icons.Rounded.ImageSearch,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            },
            onClick = {
                openUri(context = context, uri = uri)
                onDismiss()
            }
        )
        SheetItem(
            text = "انتخاب به عنوان عکس اصلی",
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Image,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            },
            onClick = {
                onCovered()
                onDismiss()
            }
        )
        SheetItem(
            text = "حذف عکس از لیست",
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            },
            onClick = {
                onDeleted()
                onDismiss()
            }
        )

        Spacer(Modifier.height(8.dp))
        Spacer(Modifier.navigationBarsPadding())
    }
}

@Composable
private fun SheetItem(
    text: String,
    icon: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon.invoke()
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = text,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold
        )
    }
}