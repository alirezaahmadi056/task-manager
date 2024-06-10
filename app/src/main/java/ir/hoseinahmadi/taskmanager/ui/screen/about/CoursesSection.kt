package ir.hoseinahmadi.taskmanager.ui.screen.about

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.hoseinahmadi.taskmanager.data.model.Course

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun CoursesSection(items: List<Course>) {
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 5.dp),
        textAlign = TextAlign.Start,
        text = "دوره های آموزشی",
        style = MaterialTheme.typography.labelLarge.copy(fontSize = 24.sp),
        color = MaterialTheme.colorScheme.scrim,
        fontWeight = FontWeight.Bold
    )
    FlowRow(
        maxItemsInEachRow = 2,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        for (it in items) {
            CourseItemCard(it)

        }
    }

}