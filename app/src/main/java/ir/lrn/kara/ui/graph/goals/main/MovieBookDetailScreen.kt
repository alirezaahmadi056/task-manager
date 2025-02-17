package ir.lrn.kara.ui.graph.goals.main

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ir.lrn.kara.data.model.Book
import ir.lrn.kara.data.model.BookMovieType
import ir.lrn.kara.data.model.Movie
import ir.lrn.kara.ui.component.BaseImageLoader
import ir.lrn.kara.ui.component.CenterBackTopBar

@Composable
fun MovieBookDetailScreen(
    navHostController: NavHostController,
    id: Int,
    type: String,
) {
    AnimatedContent(
        targetState = type,
        label = ""
    ) {
        when (it) {
            BookMovieType.MOVIE.name -> {MovieScreen(navHostController,id)}
            BookMovieType.BOOK.name -> {BookScreen(navHostController,id)}
            else -> {BookScreen(navHostController,id)}
        }
    }


}

@Composable
private fun MovieScreen(navHostController: NavHostController, id: Int) {
    val currentMovie = remember(id) { Movie.movies.find { it.id == id } }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { CenterBackTopBar("معرفی فیلم") { navHostController.navigateUp() } }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            currentMovie?.image?.let {
                BaseImageLoader(
                    model = it,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(0.65f)
                        .height(350.dp)
                        .clip(RoundedCornerShape(15.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(Modifier.height(15.dp))
            Text(
                text = currentMovie?.title ?: "",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp),
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = currentMovie?.director ?: "",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "بازیگران",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
            currentMovie?.actors?.let { actors ->
                Text(
                    text = actors.joinToString(", "),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = "خلاصه",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 6.dp)
            )
            Text(
                text = currentMovie?.summary ?: "",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(Modifier.height(15.dp))
        }
    }
}

@Composable
private fun BookScreen(navHostController: NavHostController, id: Int) {
    val currentBook = remember(id) { Book.books.find { it.id == id } }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { CenterBackTopBar("معرفی کتاب") { navHostController.navigateUp() } }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            currentBook?.image?.let {
                BaseImageLoader(
                    model = it,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(0.65f)
                        .height(350.dp)
                        .clip(RoundedCornerShape(15.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(Modifier.height(15.dp))
            Text(
                text = currentBook?.title ?: "",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp),
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = currentBook?.author ?: "",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(.8f),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = "خلاصه",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 19.sp),
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 6.dp)
            )
            Text(
                text = currentBook?.summary ?: "",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(Modifier.height(15.dp))
        }
    }
}

