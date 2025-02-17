package info.alirezaahmadi.taskmanager.ui.graph.first

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SheetVideo(
    show: Boolean,
    onDismiss: () -> Unit
) {
    if (!show) return
    val sheetState = rememberModalBottomSheetState(true)
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize(),
        sheetState = sheetState,
        shape = RoundedCornerShape(12.dp),
        containerColor = Color.Black
    ) { PlayerSection("https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/Files/kara/video.mp4") }
}