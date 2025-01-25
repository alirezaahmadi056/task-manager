package info.alirezaahmadi.taskmanager.ui.graph.dreame.addDream

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PathEffect.Companion.dashPathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.ui.component.BaseImageLoader

@Composable
fun SectionSelectedImages(
    selectedImages: List<String>,
    coveredImage: String,
    onSelectedCaverImage: (String) -> Unit,
    onAddImage: (List<String>) -> Unit,
    onRemoveImage: (String) -> Unit,
) {
    val context = LocalContext.current
    var showSheetOptionImage by remember { mutableStateOf<Uri?>(null) }

    SheetOptionImage(
        uri = showSheetOptionImage,
        context = context,
        onDismiss = { showSheetOptionImage = null },
        onDeleted = {
            if (coveredImage==showSheetOptionImage.toString()){
                if (selectedImages.isNotEmpty()){
                    onSelectedCaverImage(selectedImages.random())
                }else{onSelectedCaverImage("")}
            }else{
                onRemoveImage(showSheetOptionImage.toString())
            }
            showSheetOptionImage = null
        },
        onCovered = {
            onSelectedCaverImage(showSheetOptionImage.toString())
            showSheetOptionImage = null
        }
    )
    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments()
    ) { uris: List<Uri> ->
        onAddImage(uris.map { it.toString() }.filterNot { it in selectedImages })
        uris.forEach {
            context.contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }
    }
    val currentList = remember(
        key1 = selectedImages.toList(),
        key2 = coveredImage
    ) {
        selectedImages.filterNot { it == coveredImage }
    }
    Text(
        modifier = Modifier.padding(vertical = 14.dp),
        text = stringResource(R.string.selected_image),
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.SemiBold,
        color = Color.Black
    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
    ) {
        item { AddImage(onAdd = { imageLauncher.launch(arrayOf("image/*")) }) }
        if (coveredImage.isNotEmpty()) {
            item {
                SingleImage(
                    image = coveredImage,
                    coveredImage = true,
                    onEdit = { showSheetOptionImage = Uri.parse(coveredImage) }
                )
            }
        }
        items(currentList) {
            SingleImage(
                image = it,
                coveredImage = false,
                onEdit = { showSheetOptionImage = Uri.parse(it) })
        }
    }

}

@Composable
private fun AddImage(onAdd: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(5.dp)
            .size(100.dp)
            .clip(RoundedCornerShape(12.dp))
            .drawBehind {
                val dashWidth = 10.dp.toPx()
                val dashGap = 5.dp.toPx()
                val paint = Paint().apply {
                    color = Color(0xFF535CF0)
                    style = PaintingStyle.Stroke
                    strokeWidth = 4.dp.toPx()
                    pathEffect = dashPathEffect(
                        floatArrayOf(dashWidth, dashGap),
                        0f
                    )
                }
                drawRoundRect(
                    color = Color(0xFF535CF0),
                    style = Stroke(
                        width = 4.dp.toPx(),
                        pathEffect = paint.pathEffect
                    ),
                    cornerRadius = CornerRadius(12.dp.toPx())
                )
            }
            .clickable(onClick = onAdd),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.height(5.dp))
        Icon(
            painter = painterResource(R.drawable.album_image),
            contentDescription = "",
            tint = Color.Black
        )
        Text(
            modifier = Modifier.padding(vertical = 5.dp),
            text = "افزودن",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
    }
}

@Composable
private fun SingleImage(
    image: String,
    coveredImage: Boolean,
    onEdit: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .size(100.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onEdit),
        contentAlignment = Alignment.Center
    ) {
        BaseImageLoader(
            model = Uri.parse(image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
        )
        if (coveredImage) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Color.Transparent.copy(0.7f))
                    .padding(vertical = 4.dp)
                    .fillMaxWidth(),
                text = "عکس اصلی",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }

    }
}