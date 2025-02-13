package info.alirezaahmadi.taskmanager.ui.graph.dreame.dreamDetail

import android.net.Uri
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.data.db.dream.DreamItem
import info.alirezaahmadi.taskmanager.navigation.Screen
import info.alirezaahmadi.taskmanager.ui.component.BaseImageLoader
import info.alirezaahmadi.taskmanager.ui.component.DialogDeleteItemTask
import info.alirezaahmadi.taskmanager.util.applyQuizGraphics
import info.alirezaahmadi.taskmanager.util.openUri
import info.alirezaahmadi.taskmanager.viewModel.DreamViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DreamDetailScreen(
    navHostController: NavHostController,
    id: Int,
    dreamViewModel: DreamViewModel,
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var cover by remember { mutableStateOf("") }
    val imageList = remember { mutableStateListOf<String>() }
    LaunchedEffect(id) {
        dreamViewModel.getDreamById(id).collectLatest { dream ->
            dream?.let { item ->
                title = item.title
                description = item.description
                imageList.clear()
                imageList.addAll(item.imageUriList)
                cover =item.coverUri
            }
        }
    }
    var singleId by remember { mutableStateOf<Int?>(null) }
    val pagerState = rememberPagerState { imageList.size }
    DialogDeleteItemTask(
        title = "حذف رویا",
        body = "از حذف این رویا اطمینان دارید؟",
        onBack = { singleId = null },
        show = singleId != null,
        onDeleteItem = {
            singleId?.let { dreamViewModel.deleteDreamByID(it) }
            navHostController.navigateUp()
            singleId = null
        }
    )
    Scaffold(
        containerColor = Color.White,
        topBar = {
            DreamDetailTopBar(
                onBack = { navHostController.navigateUp() },
                onEdit = { navHostController.navigate(Screen.AddDreamsScreen(id = id)) },
                onDeleted = { singleId = id }
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                TextButton(
                    modifier = Modifier
                        .padding(12.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.textButtonColors(contentColor = Color(0xff535CF0)),
                    onClick = {
                        dreamViewModel.upsertDreamItem(
                           DreamItem(
                               id= id,
                               title =title,
                               description =description,
                               coverUri = cover,
                               imageUriList = imageList,
                               isCompleted = true
                           )
                        )
                        navHostController.navigateUp()
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(2.dp),
                        text = "رویا محقق شد",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            HeaderPager(
                pagerState = pagerState,
                imageList = imageList
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            Spacer(Modifier.height(18.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
    }
}

@Composable
private fun HeaderPager(
    imageList: List<String>,
    pagerState: PagerState
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(330.dp),
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxSize(),
            state = pagerState
        ) { page ->
            val currentImage =
                remember(key1 = imageList, key2 = page) { Uri.parse(imageList[page]) }
            BaseImageLoader(
                model = currentImage,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .applyQuizGraphics(pagerState, page)
                    .fillMaxSize()
                    .clickable { openUri(context, currentImage) }
            )


        }
        if (imageList.size < 10) {
            Row(
                modifier = Modifier
                    .height(25.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .align(Alignment.BottomCenter)
                    .background(Color.Transparent.copy(0.7f))
                    .padding(vertical = 5.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PagerIndicator(imageList.size, pagerState.currentPage)
            }
        }
    }
}

@Composable
private fun PagerIndicator(
    listSize: Int,
    page: Int
) {
    repeat(listSize) {
        val size by animateDpAsState(targetValue = if (page == it) 10.dp else 7.dp, label = "")
        val color by animateColorAsState(
            targetValue = if (page == it) Color.White else Color.LightGray,
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