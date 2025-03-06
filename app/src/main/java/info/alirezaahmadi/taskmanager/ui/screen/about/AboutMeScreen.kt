package info.alirezaahmadi.taskmanager.ui.screen.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.data.model.about.AboutResponse
import info.alirezaahmadi.taskmanager.util.TaskHelper
import info.alirezaahmadi.taskmanager.viewModel.AboutViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AboutMeScreen(
    navHostController: NavHostController,
    aboutViewModel: AboutViewModel = hiltViewModel()
) {
    val loading by aboutViewModel.loading.collectAsState()
    var item by remember {
        mutableStateOf(AboutResponse())
    }

    LaunchedEffect(key1 = true) {
        aboutViewModel.getAllData()
        aboutViewModel.allData.collectLatest { response ->
            item = response
        }
    }



    Scaffold(
        topBar = {
            TopBar { navHostController.navigateUp() }
        }
    ) {
        if (loading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(it)
                    .background(MaterialTheme.colorScheme.background),
            ) {
                AboutHeaderSection(item)
                FlowRow(
                    modifier = Modifier.padding(horizontal = 3.dp),
                    maxItemsInEachRow = 2,
                    horizontalArrangement = Arrangement.Center
                ) {
                    InfoTeacherItem(
                        "${TaskHelper.taskByLocateAndSeparator(item.data.student_count.toString())} نفر",
                        "تعداد دانشجو",
                        icon = painterResource(id = R.drawable.users_class)
                    )
                    InfoTeacherItem(
                        "${TaskHelper.taskByLocate(item.data.rate.toString())} از ۵",
                        "امتیاز دانشجویان",
                        icon = painterResource(id = R.drawable.feedback_review)

                    )
                    InfoTeacherItem(
                        "${TaskHelper.taskByLocateAndSeparator(item.data.course_count.toString())} عدد",
                        "تعداد دوره ها",
                        icon = painterResource(id = R.drawable.lesson)

                    )
                    InfoTeacherItem(
                        "${TaskHelper.taskByLocateAndSeparator((item.data.total_teach_duration / 3600).toString())} ساعت",
                        "ساعت آموزش",
                        icon = painterResource(id = R.drawable.clock_five)
                    )

                }
                Text(
                    modifier = Modifier.padding(12.dp),
                    text = item.data.description,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.scrim
                )
                Text(
                    modifier = Modifier.padding(start = 12.dp, top = 8.dp),
                    text = "شبکه های اجتماعی",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 25.sp
                    ),
                    color = MaterialTheme.colorScheme.scrim
                )

                Row(
                    modifier = Modifier
                        .padding(vertical = 14.dp, horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.telegram),
                        contentDescription = "",
                        Modifier
                            .weight(0.475f),
                        contentScale = ContentScale.FillBounds
                    )
                    Spacer(modifier = Modifier.weight(0.05f))
                    Image(
                        painter = painterResource(id = R.drawable.instagram),
                        contentDescription = "",
                        Modifier
                            .weight(0.475f),
                        contentScale = ContentScale.FillBounds

                    )

                }

            }
        }
    }


}

@Composable
private fun TopBar(onBack: () -> Unit) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = { onBack() }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowForward,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.scrim
                )
            }
            Text(
                text = "درباره من",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                color = MaterialTheme.colorScheme.scrim,
                modifier = Modifier.padding(horizontal = 3.dp)
            )
        }
        HorizontalDivider(
            modifier = Modifier.padding(bottom = 15.dp),
            thickness = 1.dp,
            color = Color.LightGray.copy(0.5f)
        )

    }
}