package ir.hoseinahmadi.taskmanager.ui.screen.about

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.TaskAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ir.hoseinahmadi.taskmanager.data.model.AboutResponse
import ir.hoseinahmadi.taskmanager.util.TaskHelper
import kotlin.math.roundToInt

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalLayoutApi::class)
@Composable
fun AboutHeaderSection(
    item: AboutResponse,
) {

        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start
        ) {
            GlideImage(
                modifier = Modifier.weight(0.4f),
                model = item.data.avatar,
                contentDescription = "",
            )
            Column(
                modifier = Modifier.weight(0.6f),
                horizontalAlignment = Alignment.Start,
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "${item.data.first_name} ${item.data.last_name}",
                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 30.sp),
                    color = MaterialTheme.colorScheme.scrim,
                    fontWeight = FontWeight.Black
                )
                Spacer(modifier = Modifier.height(7.dp))
                Detail("مدیر دانشجویار")
                Detail("مدرس برتر دانشجویار")
            }

        }

        FlowRow(
            modifier = Modifier.padding(horizontal = 3.dp),
            maxItemsInEachRow = 2,
            horizontalArrangement = Arrangement.Center
        ) {
            InfoTeacherItem("${TaskHelper.taskByLocateAndSeparator(item.data.student_count.toString())} نفر",
                "تعداد دانشجو")
            InfoTeacherItem("${TaskHelper.taskByLocate(item.data.rate.toString())} از ۵",
                "امتیاز دانشجویان")
            InfoTeacherItem("${TaskHelper.taskByLocateAndSeparator(item.data.course_count.toString())} عدد",
                "تعداد دوره ها")
            InfoTeacherItem("${TaskHelper.taskByLocateAndSeparator("714")} ساعت",
                "ساعت آموزش")

        }
        CoursesSection(item.data.courses)
    }



@Composable
private fun Detail(
    title: String,
) {
    Row(
        modifier = Modifier.padding(vertical = 5.dp, horizontal = 2.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Rounded.CheckCircle,
            contentDescription = "",
            tint = Color.Green
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.scrim,
        )
    }
}