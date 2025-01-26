package info.alirezaahmadi.taskmanager.ui.component


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.FormatListNumberedRtl
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.ui.graph.duties.notes.showDialogSelectedGridList


@Composable
fun BaseTopBar(
    navHostController: NavHostController,
    text: String,
    searchIcon: (@Composable () -> Unit)? = null,
    isNote: Boolean = false,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .drawBehind {
                drawLine(
                    color = Color.LightGray.copy(alpha = 0.5f),
                    start = Offset(0f, size.height), // شروع خط در پایین کامپوزبل
                    end = Offset(size.width, size.height), // پایان خط در پایین کامپوزبل
                    strokeWidth = 1.5.dp.toPx()
                )
            }
            .padding(vertical = 12.dp),
    ) {
        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedVisibility(
                visible = searchIcon != null,
            ) {
                searchIcon?.invoke()
            }

            var expand by remember {
                mutableStateOf(false)
            }
            Column {
                IconButton(onClick = {
                    expand = true
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.menu_dots),
                        contentDescription = "",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.scrim.copy()
                    )
                }
                DropdownMenu(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background),
                    expanded = expand,
                    onDismissRequest = { expand = false })
                {
                    DropdownMenuItem(
                        modifier = Modifier.background(MaterialTheme.colorScheme.background),
                        text = {
                            Text(
                                text = "ترتیب لیست",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.scrim

                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.FormatListNumberedRtl,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.scrim
                            )
                        },
                        onClick = {
                            showSelectedSortNotList.value = true
                            expand = false
                        })

                    if (isNote) {
                        HorizontalDivider(
                            thickness = 0.5.dp,
                            color = Color.LightGray
                        )
                        DropdownMenuItem(
                            modifier = Modifier.background(MaterialTheme.colorScheme.background),
                            text = {
                                Text(
                                    text = "نوع شاهده لیست",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.scrim
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.FilterList,
                                    contentDescription = "",
                                    tint = MaterialTheme.colorScheme.scrim
                                )
                            },
                            onClick = {
                                showDialogSelectedGridList.value = true
                                expand = false
                            })
                    }


                }
            }
        }

        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.scrim
        )

        IconButton(
            modifier = Modifier.align(Alignment.CenterStart),
            onClick = { navHostController.navigateUp() }
        ) {
            Icon(
                Icons.Rounded.ArrowForward,
                contentDescription = ""
            )
        }


    }


}