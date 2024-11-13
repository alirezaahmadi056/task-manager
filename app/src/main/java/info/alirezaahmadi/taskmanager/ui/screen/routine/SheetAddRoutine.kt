package info.alirezaahmadi.taskmanager.ui.screen.routine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import info.alirezaahmadi.taskmanager.data.db.routine.RoutineItem
import info.alirezaahmadi.taskmanager.viewModel.RoutineViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SheetAddRoutine(
    show: Boolean,
    routineItem: RoutineItem,
    routineViewModel: RoutineViewModel,
    onDismissRequest: () -> Unit,
) {
    if (!show) return
    var title by remember { mutableStateOf("") }

    ModalBottomSheet(
        shape = RoundedCornerShape(14.dp),
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = onDismissRequest,
        dragHandle = {
            DragHandle(
                isUpdate = routineItem.id != 0,
                onDismissRequest = onDismissRequest,
                onSaved = {}
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                value = title,
                onValueChange = { title = it },
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = MaterialTheme.colorScheme.scrim,
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.scrim.copy(0.8f),
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedPlaceholderColor =  MaterialTheme.colorScheme.primary,
                ),
                label = {
                    Text(
                        "روتین",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                placeholder = {
                    Text(
                        "عنوان روتین .......",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                textStyle = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                maxLines = 2,
            )
            Spacer(Modifier.navigationBarsPadding())
        }
    }
}


@Composable
private fun DragHandle(
    isUpdate: Boolean = false,
    onDismissRequest: () -> Unit,
    onSaved: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (isUpdate) "ویرایش روتین" else "افزودن روتین",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.scrim
        )
        IconButton(
            modifier = Modifier.align(Alignment.CenterStart),
            onClick = onDismissRequest
        ) {
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.scrim
            )
        }
        TextButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            onClick = onSaved
        ) {
            Text(
                text = "ذخیره",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}