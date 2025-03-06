package info.alirezaahmadi.taskmanager.ui.screen.routine

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import kotlinx.coroutines.launch

@Composable
fun TabSection(pagerState: PagerState, allTabs: List<String>) {
    val scope = rememberCoroutineScope()
    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 12.dp),
        divider = {},
        containerColor = MaterialTheme.colorScheme.background,
        indicator = {},
        edgePadding = 19.dp
    ) {
        allTabs.fastForEachIndexed { i, s ->
            Tabs(
                tab = s,
                selected = i == pagerState.currentPage
            ) {
                scope.launch { pagerState.animateScrollToPage(i) }
            }
        }
    }
}

@Composable
private fun Tabs(
    tab: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val textColor by animateColorAsState(
        targetValue = if (!selected) MaterialTheme.colorScheme.scrim
        else MaterialTheme.colorScheme.background, label = ""
    )
    val backColor by animateColorAsState(
        targetValue = if (!selected) MaterialTheme.colorScheme.background else
            MaterialTheme.colorScheme.scrim, label = ""
    )

    ElevatedButton(
        border = BorderStroke(0.8.dp,MaterialTheme.colorScheme.scrim),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = backColor,
            contentColor = textColor
        ),
        modifier = Modifier
            .padding(horizontal = 2.5.dp)
            .height(40.dp),
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
    ) {
        Text(
            text = tab,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
    }


}