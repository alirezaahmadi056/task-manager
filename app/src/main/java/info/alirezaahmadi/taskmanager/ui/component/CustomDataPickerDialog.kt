package info.alirezaahmadi.taskmanager.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDataPickerDialog(
    initialHour:Int,
    initialMinute:Int,
    isShow: Boolean,
    onDismissRequest: () -> Unit,
    onSelected: (TimePickerState) -> Unit
) {
    if (!isShow) return
    val timePickerState = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute,
        is24Hour = true,
    )
    CompositionLocalProvider(value = LocalLayoutDirection provides LayoutDirection.Ltr) {
        AlertDialog(
            textContentColor = MaterialTheme.colorScheme.scrim,
            titleContentColor = MaterialTheme.colorScheme.scrim,
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(onClick = {
                    onSelected(timePickerState)
                    onDismissRequest()
                }) {
                    Text(
                        text = "ذخیره",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.scrim
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(
                        text = "لغو",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.scrim
                    )
                }
            },
            text = {
                TimePicker(
                    colors = TimePickerDefaults.colors(
                        clockDialSelectedContentColor = MaterialTheme.colorScheme.scrim
                    ),
                    state = timePickerState,
                    layoutType = TimePickerLayoutType.Vertical
                )
            }
        )
    }


}