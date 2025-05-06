package ir.lrn.kara.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ir.lrn.kara.R
import ir.lrn.kara.viewModel.DatStoreViewModel

@Composable
fun FirstPagerScreen(
    navHostController: NavHostController,
    datStoreViewModel: DatStoreViewModel
) {

    val imageList = remember {
        listOf<Int>(
            R.drawable.p1,
            R.drawable.p2,
            R.drawable.p3,
            R.drawable.p4,
        )
    }
    val pagerState = rememberPagerState { imageList.size }
    val scope = rememberCoroutineScope()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->

            Image(
                painter = painterResource(imageList[page]),
                contentScale = ContentScale.FillBounds,
                contentDescription = "",
                modifier = Modifier.fillMaxSize()
            )

        }
        Row(
            modifier = Modifier
                .navigationBarsPadding()
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Indicator(
                page = pagerState.currentPage,
                listSize = imageList.size
            )
            Button(
                onClick = {}
            ) { }
        }
    }
}

@Composable
private fun Indicator(
    listSize: Int,
    page: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(listSize) {
            val size by animateDpAsState(targetValue = if (page == it) 7.dp else 5.dp, label = "")
            val color by animateColorAsState(
                targetValue = if (page == it) Color.DarkGray else Color.LightGray,
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

}