package info.alirezaahmadi.taskmanager.ui.graph.skinRoutine

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import info.alirezaahmadi.taskmanager.data.db.skinRoutine.SkinRoutineItem
import info.alirezaahmadi.taskmanager.data.db.skinRoutine.SkinStatus
import info.alirezaahmadi.taskmanager.viewModel.SkinRoutineViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkinRoutineDialog(
    show: Boolean,
    skinRoutineItem: SkinRoutineItem? = null,
    onDismissRequest: () -> Unit,
    skinRoutineViewModel: SkinRoutineViewModel,
) {

    if (!show) return

    var title by remember { mutableStateOf(skinRoutineItem?.title ?: "") }
    var body by remember { mutableStateOf(skinRoutineItem?.description ?: "") }
    var dayStatus by remember { mutableStateOf(skinRoutineItem?.status ?: SkinStatus.DAY.name) }
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(14.dp))
                .background(MaterialTheme.colorScheme.background)
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "روتین پوستی",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                color = MaterialTheme.colorScheme.scrim,
                fontWeight = FontWeight.SemiBold
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 7.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "روز",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.scrim
                )
                Checkbox(
                    checked = dayStatus == SkinStatus.DAY.name,
                    onCheckedChange = { dayStatus = SkinStatus.DAY.name },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary,
                        checkmarkColor = Color.White,
                        uncheckedColor = MaterialTheme.colorScheme.scrim
                    )
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    text = "شب",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.scrim
                )
                Checkbox(
                    checked = dayStatus == SkinStatus.NIGHT.name,
                    onCheckedChange = { dayStatus = SkinStatus.NIGHT.name },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary,
                        checkmarkColor = Color.White,
                        uncheckedColor = MaterialTheme.colorScheme.scrim
                    )
                )
            }
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                textStyle = MaterialTheme.typography.bodyLarge,
                shape = RoundedCornerShape(9.dp),
                value = title, onValueChange = { title = it },
                label = {
                    Text(
                        text = "عنوان روتین",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                })

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 3,
                textStyle = MaterialTheme.typography.bodyLarge,
                shape = RoundedCornerShape(9.dp),
                value = body, onValueChange = { body = it },
                label = {
                    Text(
                        text = "عنوان روتین",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                })

            Button(
                enabled = title.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.primary.copy(0.5f),
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .padding(top = 12.dp),
                onClick = {
                    skinRoutineViewModel.upsertSkinRoutine(
                        SkinRoutineItem(
                            id = skinRoutineItem?.id ?: 0,
                            title = title,
                            status = dayStatus,
                            description = body,
                        )
                    )
                    onDismissRequest()
                }) {
                Text(
                    text = "ذخیره",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}