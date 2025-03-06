package info.alirezaahmadi.taskmanager.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DeleteSweep
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DialogDeleteItemTask(
    title:String,
    body :String,
    show: Boolean,
    onDeleteItem: () -> Unit,
    onBack: () -> Unit,
) {

    if (show) {
        AlertDialog(
            textContentColor = MaterialTheme.colorScheme.scrim,
            titleContentColor = MaterialTheme.colorScheme.scrim,
            icon = {
                   Icon(imageVector = Icons.Rounded.DeleteSweep,
                       contentDescription ="",
                       tint = MaterialTheme.colorScheme.error
                       )
            },
            title = {
                Text(
                    text =title,
                    style = MaterialTheme.typography.labelMedium
                )
            },
            text = {
                Text(
                    text = body,
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            onDismissRequest = { onBack() },
            confirmButton = {
                Button(
                    modifier = Modifier.padding(horizontal = 3.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ),
                    onClick = { onDeleteItem() }) {
                    Text(
                        text = "حذف",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            },
            dismissButton = {
                TextButton(

                    onClick = { onBack() }) {
                    Text(
                        text = "لغو",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

            }
        )
    }

}