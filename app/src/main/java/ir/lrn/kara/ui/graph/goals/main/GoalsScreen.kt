package ir.lrn.kara.ui.graph.goals.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import androidx.navigation.NavHostController
import ir.lrn.kara.R
import ir.lrn.kara.data.model.Book
import ir.lrn.kara.data.model.BookMovieType
import ir.lrn.kara.data.model.Movie
import ir.lrn.kara.navigation.Screen
import ir.lrn.kara.ui.component.BaseImageLoader
import ir.lrn.kara.ui.component.CenterBackTopBar
import ir.lrn.kara.viewModel.GoalsViewModel

@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun GoalsScreen(
    navHostController: NavHostController,
    goalsViewModel: GoalsViewModel
) {
    val books = remember { Book.books }
    val movies = remember { Movie.movies }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { CenterBackTopBar(text = stringResource(R.string.my_goals)) { navHostController.navigateUp() } }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item { GoalsTopSection(navHostController, goalsViewModel) }
            stickyHeader {
                Text(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxWidth()
                        .padding(top = 12.dp, start = 23.dp, end = 8.dp, bottom = 12.dp),
                    text = stringResource(R.string.introduction_books),
                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 20.sp),
                    fontWeight = FontWeight.Black,
                )
            }
            item {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    maxItemsInEachRow = 2
                ) {
                    books.forEach { book ->
                        BooksItemCar(
                            title = book.title,
                            image = book.image,
                            description = book.author
                        ) {
                            navHostController.navigate(
                                Screen.MovieBookDetailScreen(
                                    id = book.id,
                                    type = BookMovieType.BOOK.name
                                )
                            )
                        }
                    }
                }
            }
            item {
                HorizontalDivider(
                    thickness = 35.dp,
                    color = Color.LightGray.copy(0.2f),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            stickyHeader {
                Text(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxWidth()
                        .padding(top = 12.dp, start = 23.dp, end = 8.dp, bottom = 12.dp),
                    text = stringResource(R.string.introduction_movies),
                    style = MaterialTheme.typography.labelLarge.copy(fontSize = 20.sp),
                    fontWeight = FontWeight.Black,
                )
            }
            item {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    maxItemsInEachRow = 2
                ) {
                    movies.forEach { movie ->
                        BooksItemCar(
                            title = movie.title,
                            image = movie.image,
                            description = movie.director
                        ) {
                            navHostController.navigate(
                                Screen.MovieBookDetailScreen(
                                    id = movie.id,
                                    type = BookMovieType.MOVIE.name
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BooksItemCar(
    title: String,
    description: String,
    image: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.padding(8.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground
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
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Normal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(20.dp))
        }
        BaseImageLoader(
            model = image,
            modifier = Modifier
                .size(110.dp, 160.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
    }
}