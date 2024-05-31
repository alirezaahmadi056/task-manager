package ir.hoseinahmadi.taskmanager.ui.screen.notes.addNotes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import ir.hoseinahmadi.taskmanager.ui.theme.DarkOrange

var showBottomSheetSelectedColor = mutableStateOf(false)

data class SelectedColor(
    val color: Color,
    val name: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetSelectedColor(
    onClick: (colorIndex: Int,) -> Unit
) {
    val show by remember {
        showBottomSheetSelectedColor
    }
    if (show) {
        val item = listOf<SelectedColor>(
            SelectedColor(
                color = MaterialTheme.colorScheme.onPrimary,
                name = "عادی"
            ),
            SelectedColor(
                color = MaterialTheme.colorScheme.onSecondary,
                name = "متوسط"
            ),
            SelectedColor(
                color = MaterialTheme.colorScheme.error,
                name = "مهم"
            ),

            )

        ModalBottomSheet(
            modifier = Modifier.fillMaxWidth(),
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest =
            { showBottomSheetSelectedColor.value = false })
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 3.dp)
                    .padding(bottom = 50.dp)
            ) {
                item.fastForEachIndexed { i, selectedColor ->
                    ItemSelectedColor(
                        text = selectedColor.name,
                        color = selectedColor.color,
                        onClick = {
                            onClick(i + 1)
                            showBottomSheetSelectedColor.value = false }
                    )
                    HorizontalDivider(
                        thickness = .5.dp,
                        color = Color.LightGray.copy(0.5f),
                        modifier = Modifier.padding(4.dp)
                    )
                }

            }
        }
    }

}

@Composable
private fun ItemSelectedColor(
    text: String,
    color: Color,
    onClick: () -> Unit
) {

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        onClick = { onClick() }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp, 28.dp)
                    .clip(RoundedCornerShape(7.dp))
                    .background(color)
                    .padding(4.dp)
            )
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = text,
                style = MaterialTheme.typography.labelSmall,
                color = color
            )
        }

    }

}