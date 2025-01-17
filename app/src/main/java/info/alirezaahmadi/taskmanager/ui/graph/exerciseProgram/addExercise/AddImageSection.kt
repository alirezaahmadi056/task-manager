package info.alirezaahmadi.taskmanager.ui.graph.exerciseProgram.addExercise

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DriveFolderUpload
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.util.getFileMetaData
import info.alirezaahmadi.taskmanager.util.openUri

@Composable
fun AddImageSection(
    uri: Uri?,
    onImageSelected: (Uri?) -> Unit
) {

    val context = LocalContext.current
    val fileName: String = remember(key1 = uri) {
        uri?.let { getFileMetaData(context, it) }?.name ?: "عکس انتخاب نشده"
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) {
        onImageSelected(it)
    }

    Text(
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp),
        text = stringResource(R.string.selected_image),
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.SemiBold,
        color = Color.Black
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xffECECEC))
            .clickable(enabled = uri != Uri.EMPTY && uri != null) {
                uri?.let {
                    openUri(context, it)
                }
            }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = fileName,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xff9747FF), RoundedCornerShape(10.dp))
                .clickable { launcher.launch(arrayOf("image/*")) },
        ) {
            Icon(
                imageVector = Icons.Rounded.DriveFolderUpload,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.padding(5.dp)
            )
        }
    }


}