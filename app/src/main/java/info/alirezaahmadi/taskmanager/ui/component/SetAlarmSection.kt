package info.alirezaahmadi.taskmanager.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AlarmOff
import androidx.compose.material.icons.rounded.AlarmOn
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SetAlarmSection(
    onSelectedDate: () -> Unit,
    onSelectedTime: () -> Unit,
    times: List<String> = emptyList()
) {
    var showSection by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        HorizontalDivider(
            thickness = 6.dp,
            color = Color.LightGray.copy(0.1f)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Switch(
                checked = showSection,
                onCheckedChange = {
                    showSection = it
                },
                colors = SwitchDefaults.colors(
                    checkedIconColor = MaterialTheme.colorScheme.primary.copy(0.8f),
                    checkedThumbColor = MaterialTheme.colorScheme.background,
                    uncheckedIconColor = Color.DarkGray,
                    uncheckedBorderColor = Color.DarkGray.copy(0.8f),
                    uncheckedThumbColor = Color.DarkGray,
                )
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (showSection) Icons.Rounded.AlarmOn else Icons.Rounded.AlarmOff,
                    contentDescription = "",
                    tint = if (!showSection) MaterialTheme.colorScheme.scrim.copy(0.8f) else MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "تنظیم یادآور",
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (!showSection) MaterialTheme.colorScheme.scrim.copy(0.8f) else MaterialTheme.colorScheme.primary
                )
            }

        }
        AnimatedVisibility(
            visible = showSection,
            enter = fadeIn() + expandVertically(animationSpec = tween(800)),
            exit = fadeOut() + shrinkVertically(animationSpec = tween(500))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${times[0]}:${times[1]}"
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.DateRange,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.scrim.copy(0.8f)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "تنظیم تاریخ",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.scrim.copy(0.8f)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) { }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (showSection) Icons.Rounded.AlarmOn else Icons.Rounded.AlarmOff,
                        contentDescription = "",
                        tint = if (!showSection) MaterialTheme.colorScheme.scrim.copy(0.8f) else MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "تنظیم ساعت",
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (!showSection) MaterialTheme.colorScheme.scrim.copy(0.8f) else MaterialTheme.colorScheme.primary
                    )
                }

            }
            HorizontalDivider(
                thickness = 6.dp,
                color = Color.LightGray.copy(0.1f)
            )
        }
    }
}