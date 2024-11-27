package info.alirezaahmadi.taskmanager.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DeleteSweep
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDismissBoxLayout(
    enableDismissFromEndToStart: Boolean,
    enableDismissFromStartToEnd: Boolean,
    startToEnd: () -> Unit = {},
    endToStart: () -> Unit = {},
    content: @Composable RowScope.() -> Unit
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        val swipeToDismiss = rememberSwipeToDismissBoxState(
            confirmValueChange = { swiped ->
                when (swiped) {
                    SwipeToDismissBoxValue.StartToEnd -> startToEnd()
                    SwipeToDismissBoxValue.EndToStart -> endToStart()
                    SwipeToDismissBoxValue.Settled -> {}
                }
                return@rememberSwipeToDismissBoxState false
            }
        )
        SwipeToDismissBox(
            enableDismissFromEndToStart = enableDismissFromEndToStart,
            enableDismissFromStartToEnd = enableDismissFromStartToEnd,
            state = swipeToDismiss,
            backgroundContent = {
                when (swipeToDismiss.dismissDirection) {
                    SwipeToDismissBoxValue.StartToEnd -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(5.dp)
                                .clip(RoundedCornerShape(11.dp))
                                .background(Color(0xFF4CAF50)),
                            contentAlignment = Alignment.CenterStart
                        )
                        {
                            Icon(
                                Icons.Rounded.EditNote,
                                contentDescription = "",
                                tint = Color.White,
                                modifier = Modifier.size(50.dp)
                            )

                        }
                    }

                    SwipeToDismissBoxValue.EndToStart -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(5.dp)
                                .clip(RoundedCornerShape(11.dp))
                                .background(Color.Red),
                            contentAlignment = Alignment.CenterEnd
                        )
                        {
                            Icon(
                                Icons.Rounded.DeleteSweep,
                                contentDescription = "",
                                tint = Color.White, modifier = Modifier.size(50.dp)
                            )

                        }
                    }

                    SwipeToDismissBoxValue.Settled -> {}
                }

            }
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                content()
            }
        }
    }

}