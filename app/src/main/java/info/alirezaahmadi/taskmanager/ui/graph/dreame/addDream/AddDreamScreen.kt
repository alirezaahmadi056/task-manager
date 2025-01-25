package info.alirezaahmadi.taskmanager.ui.graph.dreame.addDream

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.data.db.dream.DreamItem
import info.alirezaahmadi.taskmanager.ui.component.CenterBackTopBar
import info.alirezaahmadi.taskmanager.viewModel.DreamViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddDreamScreen(
    navHostController: NavHostController,
    id: Int,
    dreamViewModel: DreamViewModel
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var checkInput by remember { mutableStateOf(false) }
    val imageList = remember { mutableStateListOf<String>() }
    var coveredImage by remember { mutableStateOf("") }
    LaunchedEffect(id) {
        dreamViewModel.getDreamById(id).collectLatest { dream ->
            dream?.let { item ->
                title = item.title
                description = item.description
                imageList.clear()
                imageList.addAll(item.imageUriList)
                coveredImage = item.coverUri
            }
        }
    }
    Scaffold(
        containerColor = Color.White,
        topBar = {
            CenterBackTopBar(text = stringResource(if (id == 0) R.string.add_dream else R.string.update_dream)) {
                navHostController.navigateUp()
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(0.9f),
                    contentPadding = PaddingValues(
                        horizontal = 40.dp,
                        vertical = 8.dp
                    ),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff535CF0),
                        contentColor = Color.White
                    ),
                    onClick = {
                        if (imageList.toList().isNotEmpty() && title.isNotEmpty()) {
                            checkInput = false
                            dreamViewModel.upsertDreamItem(
                                DreamItem(
                                    id = id,
                                    title = title,
                                    coverUri = coveredImage,
                                    description = description,
                                    imageUriList = imageList
                                )
                            )
                            navHostController.navigateUp()
                        } else {
                            checkInput = true
                        }
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(2.dp),
                        text = stringResource(R.string.save),
                        style = MaterialTheme.typography.labelMedium,
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
                .padding(horizontal = 12.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 12.dp, bottom = 4.dp, start = 10.dp),
                text = stringResource(R.string.title_dream),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            OutlinedTextField(
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.LightGray,
                    unfocusedIndicatorColor = Color.White,
                    unfocusedPlaceholderColor = Color.DarkGray,
                    focusedPlaceholderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.DarkGray,
                    focusedContainerColor = Color.White,
                    errorPlaceholderColor = Color.White,
                    unfocusedContainerColor = Color(0xffECECEC),
                    errorContainerColor = Color(0xFFE20000).copy(0.4f),
                    errorSupportingTextColor = Color(0xFFE20000),
                ),
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                isError = checkInput && title.isEmpty(),
                textStyle = MaterialTheme.typography.bodyLarge,
                shape = RoundedCornerShape(9.dp),
                value = title, onValueChange = {
                    if (it.length <= 30) {
                        title = it
                    }
                },
                placeholder = {
                    Text(
                        text = "عنوان رویا را وارد کنید",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                supportingText = {
                    if (checkInput && title.isEmpty()) {
                        Text(
                            text = "عنوان رویا را وارد کنید",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            )

            Text(
                modifier = Modifier.padding(top = 12.dp, bottom = 8.dp, start = 10.dp),
                text = stringResource(R.string.description_dream),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            OutlinedTextField(
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.LightGray,
                    unfocusedIndicatorColor = Color.White,
                    unfocusedPlaceholderColor = Color.DarkGray,
                    focusedPlaceholderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.DarkGray,
                    focusedContainerColor = Color.White,
                    errorPlaceholderColor = Color.White,
                    unfocusedContainerColor = Color(0xffECECEC),
                    errorContainerColor = Color(0xFFE20000).copy(0.4f),
                    errorSupportingTextColor = Color(0xFFE20000),
                ),
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3,
                minLines = 3,
                textStyle = MaterialTheme.typography.bodyLarge,
                shape = RoundedCornerShape(9.dp),
                value = description, onValueChange = { description = it },
                placeholder = {
                    Text(
                        text = "توضیحات رویا را وارد کنید",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
            )

            SectionSelectedImages(
                selectedImages = imageList,
                onAddImage = { images ->
                    imageList.addAll(images)
                },
                coveredImage = coveredImage,
                onSelectedCaverImage = { image ->
                    coveredImage = image
                },
                onRemoveImage = { image ->
                    imageList.remove(image)
                }
            )
            AnimatedVisibility(
                checkInput && imageList.toList().isEmpty()
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp, horizontal = 12.dp),
                    text = "حداقل یک عکس وارد کنید",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFE20000),
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}