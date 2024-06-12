package ir.hoseinahmadi.taskmanager.ui.screen.about

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.hoseinahmadi.taskmanager.data.model.about.Course

@OptIn( ExperimentalLayoutApi::class)
@Composable
fun CoursesSection(goldCourses: List<Course>, nonGoldCourses: List<Course>) {

    val utiHandle = LocalUriHandler.current
    val context = LocalContext.current
    Spacer(modifier = Modifier.height(10.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 9.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "دوره های طلایی",
            style = MaterialTheme.typography.labelLarge.copy(fontSize = 24.sp),
            color = MaterialTheme.colorScheme.scrim,
            fontWeight = FontWeight.Bold
        )

        TextButton(onClick = {
            try {
                utiHandle.openUri("https://www.daneshjooyar.com/teacher/alireza-ahmadi/")
            }catch (e:Exception){
                Toast.makeText(context, "خطا", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(text = "مشاهده همه",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
                )
        }
    }

    FlowRow(
        maxItemsInEachRow = 2,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 3.dp),
        horizontalArrangement = Arrangement.Start,
        verticalArrangement = Arrangement.Center

    ) {
        for (it in goldCourses) {
            CourseItemCard(it)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 9.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "سایر دوره ها",
            style = MaterialTheme.typography.labelLarge.copy(fontSize = 24.sp),
            color = MaterialTheme.colorScheme.scrim,
            fontWeight = FontWeight.Bold
        )

        TextButton(onClick = {
            try {
                utiHandle.openUri("https://www.daneshjooyar.com/teacher/alireza-ahmadi/")
            }catch (e:Exception){
                Toast.makeText(context, "خطا", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(text = "مشاهده همه",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }


    FlowRow(
        maxItemsInEachRow = 2,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 3.dp),
        horizontalArrangement = Arrangement.Start,
        verticalArrangement = Arrangement.Center
    ) {
        for (it in nonGoldCourses) {
            CourseItemCard(it)
        }
    }
}