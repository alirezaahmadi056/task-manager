package info.alirezaahmadi.taskmanager.ui.graph.goals.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.data.model.ResponseMoviesBook
import info.alirezaahmadi.taskmanager.ui.component.BaseImageLoader
import info.alirezaahmadi.taskmanager.viewModel.GoalsViewModel

@Composable
fun MoviesAndBooksSection(
    goalsViewModel: GoalsViewModel
) {
    val books = ResponseMoviesBook.fakeMoviesBooks
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 23.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.introduction_movies_books),
            style = MaterialTheme.typography.labelLarge.copy(fontSize = 18.sp),
            fontWeight = FontWeight.Bold,
        )
        TextButton(
            onClick = {}
        ) {
            Text(
                text = stringResource(R.string.more),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Spacer(Modifier.width(4.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                contentDescription = "",
                tint = Color.Black
            )
        }
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
    ) {
        item { Spacer(Modifier.width(12.dp)) }
        items(books) { book -> BooksItemCar(item = book) }
    }
}

@Composable
private fun BooksItemCar(
    item: ResponseMoviesBook,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier.padding(8.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            elevation = CardDefaults.cardElevation(2.dp),
            border = BorderStroke(1.dp, Color.LightGray.copy(0.4f)),
            onClick = onClick,
            modifier = Modifier
                .padding(top = 55.dp)
                .width(170.dp)
        ) {
            Spacer(Modifier.height(120.dp))
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = item.author,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Normal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(20.dp))
        }
        BaseImageLoader(
            model = item.image,
            modifier = Modifier
                .size(110.dp, 160.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
    }
}