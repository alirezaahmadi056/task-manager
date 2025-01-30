package info.alirezaahmadi.taskmanager.ui.graph.first

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.util.applyQuizGraphics
import kotlinx.coroutines.delay

private data class PagerData(
    val title: String,
    val message: String,
    @DrawableRes
    val image: Int,
    val buttonText: String,
    val route: Screen
)

@Composable
fun FirstTopPager(
    navHostController: NavHostController
) {
    val pagerItems = listOf(
        PagerData(
            title = "برنامه‌هات رو مدیریت کن!",
            message = "وظایف روزانه‌ات رو ساده و سریع مدیریت کن.",
            image = R.drawable.duties_image,
            buttonText = "مدیریت وظایف",
            route = Screen.DutiesGraph
        ),
        PagerData(
            title = "روتین هفتگی منظم داشته باش",
            message = "یک برنامه هفتگی بساز و بهش پایبند بمون.",
            image = R.drawable.weekly_routine_image,
            buttonText = "ثبت روتین",
            route = Screen.WeeklyRoutineGraph
        ),
        PagerData(
            title = "ورزش‌هات رو منظم کن!",
            message = "برنامه باشگاه و تمریناتت رو دقیق تنظیم کن.",
            image = R.drawable.ecericies_image,
            buttonText = "برنامه ورزشی",
            route = Screen.ExerciseProgramGraph
        ),
        PagerData(
            title = "مصرف داروهات رو فراموش نکن",
            message = "داروهات رو سر وقت و بدون تاخیر مصرف کن.",
            image = R.drawable.medicine_image,
            buttonText = "برنامه دارویی",
            route = Screen.MedicineGraph
        ),
        PagerData(
            title = "پوستت رو سالم نگه دار!",
            message = "روتین پوستی بساز و از پوستت مراقبت کن.",
            image = R.drawable.skin_routine_image,
            buttonText = "روتین پوستی",
            route = Screen.SkinRoutineGraph
        ),
        PagerData(
            title = "به اهداف خودت نزدیک شو",
            message = "اهدافت رو مشخص کن و براشون برنامه‌ریزی کن.",
            image = R.drawable.goals_image,
            buttonText = "ثبت هدف",
            route = Screen.GoalsGraph
        ),
        PagerData(
            title = "رویاهای خودت رو ثبت کن!",
            message = "رویاهای بزرگت رو بنویس و به یاد داشته باش.",
            image = R.drawable.dream_image,
            buttonText = "ثبت رویا",
            route = Screen.DreamGraph
        )
    )
    val pagerState = rememberPagerState { pagerItems.size }
    LaunchedEffect(pagerState.currentPage) {
        delay(3000)
        val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
        pagerState.scrollToPage(
            page = nextPage,
            pageOffsetFraction = 0f,
        )

    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 8.dp),
            key = { "page:$it" }
        ) { page ->
            SinglePagerItem(
                modifier = Modifier.applyQuizGraphics(pagerState, page),
                item = pagerItems[page],
                navHostController = navHostController
            )
        }
        Row(
            modifier = Modifier
                .padding(6.dp)
                .clip(RoundedCornerShape(12.dp))
                .align(Alignment.BottomCenter)
                .background(Color.White.copy(0.15f))
                .padding(vertical = 2.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PagerIndicator(
                pagerItems.size,
                pagerState.currentPage
            )
        }
    }


}

@Composable
private fun SinglePagerItem(
    modifier: Modifier,
    navHostController: NavHostController,
    item: PagerData
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xff007AFF), RoundedCornerShape(12.dp))
            .padding(horizontal = 10.dp, vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(0.8f)
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            Spacer(Modifier.height(5.dp))
            Text(
                text = item.message,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White.copy(0.7f)
            )
            Spacer(Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .clickable { navHostController.navigate(item.route) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.buttonText,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    modifier = Modifier.padding(
                        horizontal = 8.dp,
                        vertical = 4.dp
                    )
                )
            }
        }
        Image(
            painter = painterResource(item.image),
            contentDescription = "",
            modifier = Modifier
                .weight(.2f)
                .aspectRatio(1f),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun PagerIndicator(
    listSize: Int,
    page: Int
) {
    repeat(listSize) {
        val size by animateDpAsState(targetValue = if (page == it) 7.dp else 5.dp, label = "")
        val color by animateColorAsState(
            targetValue = if (page == it) Color.White else Color.White.copy(0.2f),
            label = ""
        )
        Box(
            modifier = Modifier
                .padding(2.dp)
                .clip(CircleShape)
                .background(color = color, shape = CircleShape)
                .size(size)
        )
    }

}