package info.alirezaahmadi.taskmanager.ui.component

import android.Manifest
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import info.alirezaahmadi.taskmanager.util.requestPermissionNotification

@SuppressLint("InlinedApi")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SetAlarmSection(
    enableAlarm: Boolean,
    onEnable: (Boolean) -> Unit,
    onSelectedDate: () -> Unit,
    onSelectedTime: () -> Unit,
    times: String,
    dates: String,
) {
    val context = LocalContext.current
    var showSection by remember(key1 = enableAlarm) { mutableStateOf(enableAlarm) }

    val notificationPermissionState =
        rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
    ) {
        HorizontalDivider(
            thickness = 6.dp,
            color = Color.LightGray.copy(0.1f)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Switch(
                checked = showSection,
                onCheckedChange = { enable ->
                    requestPermissionNotification(
                        notificationPermission = notificationPermissionState,
                        isGranted = { granted ->
                            if (granted) {
                                showSection = enable
                                onEnable(enable)
                            } else {
                                Toast.makeText(
                                    context,
                                    "دسترسی نوتیفیکیشن اعطا نشده!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        permissionState = { per ->
                            per.launchPermissionRequest()
                        }
                    )

                },
                colors = SwitchDefaults.colors(
                    checkedIconColor = MaterialTheme.colorScheme.primary.copy(),
                    checkedThumbColor = Color.White,
                    uncheckedBorderColor = Color.DarkGray,
                    uncheckedThumbColor = Color.DarkGray,
                    uncheckedTrackColor = Color.White,
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
            exit = fadeOut() + shrinkVertically(animationSpec = tween(800))
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier
                        .clickable(onClick = onSelectedDate)
                        .fillMaxWidth()
                        .padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.DateRange,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.scrim.copy(0.8f)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = "تاریخ",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.scrim.copy(0.8f)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .border(
                                .5.dp,
                                color = MaterialTheme.colorScheme.scrim,
                                shape = RoundedCornerShape(6.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = dates,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.scrim,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .clickable(onClick = onSelectedTime)
                        .fillMaxWidth()
                        .padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (showSection) Icons.Rounded.AlarmOn else Icons.Rounded.AlarmOff,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.scrim.copy(0.8f)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = "ساعت",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.scrim.copy(0.8f)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .border(
                                .5.dp,
                                color = MaterialTheme.colorScheme.scrim,
                                shape = RoundedCornerShape(6.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = times,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.scrim,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
        HorizontalDivider(
            thickness = 6.dp,
            color = Color.LightGray.copy(0.1f)
        )
    }
}