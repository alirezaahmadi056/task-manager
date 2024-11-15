package info.alirezaahmadi.taskmanager.ui.screen.routine

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import info.alirezaahmadi.taskmanager.ui.screen.notes.addNotes.SelectedColor

@Composable
fun SectionSelectedColorRoutine(
    currentColor: Int,
    onColor: (Int) -> Unit
) {
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
    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        itemsIndexed(item){ index, selectedColor ->
            val boarderColor by animateColorAsState(
                targetValue = if (index+1 == currentColor) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
                label = ""
            )
            Surface(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.padding(horizontal = 3.dp),
                onClick = { onColor(index + 1) },
                border = BorderStroke(2.dp, color = boarderColor)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Canvas(modifier = Modifier
                        .size(22.dp)
                        .border(1.dp, Color.LightGray, CircleShape),
                        onDraw = { drawCircle(color = selectedColor.color) }
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = selectedColor.name,
                        color = MaterialTheme.colorScheme.scrim,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }





}