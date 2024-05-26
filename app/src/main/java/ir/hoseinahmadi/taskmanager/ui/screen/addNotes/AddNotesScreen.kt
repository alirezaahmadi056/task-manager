package ir.hoseinahmadi.taskmanager.ui.screen.addNotes

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Notes
import androidx.compose.material.icons.rounded.PhoneIphone
import androidx.compose.material.icons.rounded.RunningWithErrors
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastMapIndexed
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import ir.hoseinahmadi.taskmanager.data.db.notes.NotesItem
import ir.hoseinahmadi.taskmanager.util.TaskHelper
import ir.hoseinahmadi.taskmanager.viewModel.NotesViewModel
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun AddNotesScreen(
    navHostController: NavHostController,
    id: Int,
    notesViewModel: NotesViewModel = hiltViewModel()
) {

    var title by remember {
        mutableStateOf("")
    }

    var body by remember {
        mutableStateOf("")
    }

    var selectedColor by remember {
        mutableIntStateOf(1)
    }
    var contactPhone by remember {
        mutableStateOf("")
    }
    var addres by remember {
        mutableStateOf("")
    }

    var selectedImageUriList by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }

    val multipleImagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uriList ->
            selectedImageUriList = uriList
        }
    )

    val taskColor = when (selectedColor) {
        2 -> {
            MaterialTheme.colorScheme.onSecondary
        }

        3 -> {
            MaterialTheme.colorScheme.error
        }

        else -> {
            MaterialTheme.colorScheme.onPrimary
        }
    }
    var nameColor by remember {
        mutableStateOf("عادی")
    }
    var header = " یادداشت جدید"
    var bottom = "افزودن یادداشت"

    var item by remember {
        mutableStateOf<NotesItem>(NotesItem())
    }

    if (id != 999) {
        LaunchedEffect(key1 = true) {
            notesViewModel.getNotesItem(id).collectLatest {
                item = it
            }
        }
        title = item.title
        body = item.body
        selectedColor = item.taskColor
        contactPhone = item.phone
        addres = item.address
        header = "ویرایش یادداشت"
        bottom = "ذخیره یادداشت"
        selectedImageUriList = item.uri
    }

    Log.e("pasi", selectedImageUriList.toString())
    Log.e("pasi", id.toString())
    Scaffold(
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                onClick = {
                    notesViewModel.upsertNotesItem(
                        if (id != 999) {
                            NotesItem(
                                id = item.id,
                                title = title,
                                body = body,
                                taskColor = selectedColor,
                                phone = contactPhone,
                                address = addres
                            )
                        } else {
                            NotesItem(
                                title = title,
                                body = body,
                                taskColor = selectedColor,
                                phone = contactPhone,
                                address = addres,
                                uri = selectedImageUriList
                            )
                        }
                    )
                    navHostController.popBackStack()
                }) {
                Text(text = bottom)

            }
        },
        topBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                IconButton(onClick = { navHostController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                        contentDescription = "",
                        Modifier.size(30.dp),
                        tint = MaterialTheme.colorScheme.scrim
                    )
                }
                Text(text = header)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {


            TextField(
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.scrim,
                    cursorColor = Color(0xFF2196F3)
                ),
                maxLines = 2,
                placeholder = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp),
                        text = "عنوان یادداشت ...",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.scrim.copy(0.7f),
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                value = title,
                onValueChange = { title = it },
                textStyle = MaterialTheme.typography.labelMedium.copy(
                    textAlign = TextAlign.Center
                )
            )

            HorizontalDivider(
                modifier = Modifier.padding(top = 10.dp, bottom = 3.dp),
                thickness = 1.dp,
                color = Color.LightGray.copy(0.5f)
            )

            TextField(
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Notes,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.scrim.copy(0.8f)
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.scrim,
                    cursorColor = Color(0xFF2196F3)
                ),
                placeholder = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp),
                        text = "توضیحات ...",
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.scrim.copy(0.7f),
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(),
                value = body,
                onValueChange = { body = it },
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    textAlign = TextAlign.Start
                )
            )
            HorizontalDivider(
                thickness = 1.dp,
                color = Color.LightGray.copy(0.5f)
            )

            //selected contact
            TextField(
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.PhoneIphone,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.scrim.copy(0.8f)
                    )
                },
                placeholder = {
                    Text(
                        text = "شماره تلفن",
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.scrim.copy(0.7f),
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.scrim,
                    cursorColor = Color(0xFF2196F3),
                    unfocusedTextColor = MaterialTheme.colorScheme.scrim.copy(0.8f)
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    textAlign = TextAlign.End
                ),
                singleLine = true,
                value = contactPhone,
                onValueChange = { phone ->
                    contactPhone = TaskHelper.taskByLocate(phone)
                })
//address
            HorizontalDivider(
                thickness = 1.dp,
                color = Color.LightGray.copy(0.5f)
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.LocationOn,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.scrim.copy(0.8f)
                    )
                },
                placeholder = {
                    Text(
                        text = "آدرس",
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.scrim.copy(0.7f),
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = MaterialTheme.colorScheme.scrim,
                    cursorColor = Color(0xFF2196F3),
                    unfocusedTextColor = MaterialTheme.colorScheme.scrim.copy(0.8f)
                ),
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    textAlign = TextAlign.Start
                ),
                maxLines = 3,
                value = addres,
                onValueChange = { address ->
                    addres = TaskHelper.taskByLocate(address)
                })

            HorizontalDivider(
                thickness = 1.dp,
                color = Color.LightGray.copy(0.5f)
            )
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                onClick = { showBottomSheetSelectedColor.value = true })
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.RunningWithErrors,
                            contentDescription = "",
                            Modifier.padding(end = 8.dp),
                            tint = MaterialTheme.colorScheme.scrim.copy(0.8f)
                        )
                        Text(
                            text = "اولویت",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.scrim
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            text = nameColor,
                            style = MaterialTheme.typography.bodyLarge,
                            color = taskColor
                        )
                        Box(
                            modifier = Modifier
                                .size(13.dp, 28.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(taskColor)
                                .padding(4.dp)
                        )
                        Icon(
                            modifier = Modifier.padding(start = 8.dp, end = 5.dp),
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                            contentDescription = ""
                        )
                    }


                }

                BottomSheetSelectedColor(onClick = { colorIndex, name ->
                    selectedColor = colorIndex
                    nameColor = name
                })

            }

            HorizontalDivider(
                thickness = 4.dp,
                color = Color.LightGray.copy(0.1f)
            )

            var expanded by remember {
                mutableStateOf(false)
            }
            val rotateState by animateFloatAsState(
                targetValue = if (expanded) 180f else 0f,
                label = ""
            )

            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = 300
                        )
                    ),
                onClick = {
                    expanded = !expanded
                })
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 14.dp, horizontal = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(text = "پیوست")

                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowDown, contentDescription = "",
                        modifier = Modifier
                            .rotate(rotateState)
                            .size(28.dp),
                        tint = MaterialTheme.colorScheme.scrim
                    )
                }
            }

            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(animationSpec = tween(1000)),
                exit = fadeOut() + shrinkVertically(animationSpec = tween(1000))
            ) {
                Column {
                    val context = LocalContext.current
                    for (uri in selectedImageUriList) {
                        val fileInfo = getFileInfo(context.contentResolver, uri)
                        fileInfo?.let { (name, size) ->
                            // اطلاعات فایل را دریافت کرده‌ایم، می‌توانید از آنها استفاده کنید
                            // برای مثال، می‌توانید اطلاعات را به کارت جزئیات فایل اضافه کنید
                            SaveImageCard(uri, context, name, size.toString()) {
                                if (id != 0) {
                                    IconButton(
                                        modifier = Modifier.padding(bottom = 8.dp),
                                        onClick = { }) {
                                        Icon(
                                            imageVector = Icons.Rounded.DeleteForever,
                                            contentDescription = "",
                                            Modifier.size(30.dp),
                                            tint = MaterialTheme.colorScheme.error
                                        )
                                    }
                                }
                            }
                        }

                    }
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .padding(horizontal = 5.dp, vertical = 4.dp),
                        border = BorderStroke(1.dp, color = Color.DarkGray),
                        onClick = {
                            multipleImagePickerLauncher.launch("*/*")
                        })
                    {
                        Row(
                            modifier = Modifier
                                .padding(vertical = 18.dp, horizontal = 30.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "اضافه کردن عکس, فایل"
                            )
                        }

                    }
                }


            }


        }

    }
}

//@RequiresApi(Build.VERSION_CODES.O)
//fun DateTimeButton(
//    data: (date: String, time: String) -> Unit
//) {
//    val currentDateTime = LocalDateTime.now()
//    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
//
//    val currentDate = currentDateTime.format(dateFormatter)
//    val currentTime = currentDateTime.format(timeFormatter)
//
//}

fun getFileInfo(contentResolver: ContentResolver, uri: Uri): Pair<String, Long>? {
    val cursor = contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            val sizeIndex = it.getColumnIndex(OpenableColumns.SIZE)
            val name = it.getString(nameIndex)
            val size = it.getLong(sizeIndex)
            return Pair(name, size)
        }
    }
    return null
}
