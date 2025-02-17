package ir.lrn.kara.ui.graph.curriculum.main

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import kotlinx.coroutines.launch

@Composable
fun CurriculumTopBar(
    pagerState: PagerState,
    allTabs: List<String>,
    onBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "برنامه درسی من",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 19.sp),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.align(Alignment.Center),
                fontWeight = FontWeight.Black
            )
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Rounded.ArrowForward,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 25.dp),
            divider = {},
            containerColor = Color.Transparent,
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

}

@Composable
private fun Tabs(
    tab: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val textColor by animateColorAsState(
        targetValue = if (!selected) MaterialTheme.colorScheme.onBackground
        else MaterialTheme.colorScheme.background, label = ""
    )

    val backColor by animateColorAsState(
        targetValue = if (!selected) MaterialTheme.colorScheme.background else
            MaterialTheme.colorScheme.onBackground, label = ""
    )

    ElevatedButton(
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = backColor,
            contentColor = textColor
        ),
        modifier = Modifier
            .padding(horizontal = 3.dp)
            .height(42.dp),
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