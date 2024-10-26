package info.alirezaahmadi.taskmanager.ui.screen.notes.addNotes

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun SheetSaveDiscard(
    show: Boolean,
    onDismissRequest: () -> Unit,
    exit: () -> Unit
) {
    if (show) {
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest =onDismissRequest,
            title = {
                Text(
                    text = "تغییرات ذخیره نشده!",
                    color = MaterialTheme.colorScheme.scrim,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier,
                    textAlign = TextAlign.Start,
                )
            },
            text = {
                Text(
                    text = "ادامه می دهید یا آن ها را کنار میگذارید؟",
                    color = MaterialTheme.colorScheme.scrim,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier,
                    textAlign = TextAlign.Start,
                )
            },
            confirmButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(
                        text = "ادامه",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.scrim,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = exit) {
                    Text(
                        text = "کنار گذاشتن",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.scrim,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        )

    }
}