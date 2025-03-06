package info.alirezaahmadi.taskmanager.ui.screen.notes.addNotes

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.rounded.AttachFile
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Notes
import androidx.compose.material.icons.rounded.PhoneIphone
import androidx.compose.material.icons.rounded.PriorityHigh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import info.alirezaahmadi.taskmanager.R
import info.alirezaahmadi.taskmanager.data.db.notes.NotesItem
import info.alirezaahmadi.taskmanager.ui.component.MySnackbarHost
import info.alirezaahmadi.taskmanager.ui.component.TopBar
import info.alirezaahmadi.taskmanager.util.PersianDate
import info.alirezaahmadi.taskmanager.util.TaskHelper
import info.alirezaahmadi.taskmanager.viewModel.NotesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddNotesScreen(
    navHostController: NavHostController,
    id: Int,
    notesViewModel: NotesViewModel = hiltViewModel()
) {
    var showSheetDiscard by remember {
        mutableStateOf(false)
    }
    val focusManager = LocalFocusManager.current

    var title by remember {
        mutableStateOf("")
    }

    var body by remember {
        mutableStateOf("")
    }

    var oldTitle by remember {
        mutableStateOf("")
    }

    var oldBody by remember {
        mutableStateOf("")
    }


    var contactPhone by remember {
        mutableStateOf("")
    }

    var address by remember {
        mutableStateOf("")
    }


    val selectedImageUriList = remember { mutableStateListOf<Uri>() }


    val context = LocalContext.current


    val multipleImagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments(),
        onResult = { uriList: List<Uri> ->
            if (uriList.isNotEmpty()) {
                selectedImageUriList.addAll(uriList)
                uriList.forEach { uri ->
                    context.contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                }
            }
        }
    )
    var header = " یادداشت جدید"
    var bottom = "افزودن یادداشت"

    var selectedColor by remember {
        mutableIntStateOf(1)
    }

    var createTime by remember {
        mutableStateOf("")
    }

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


    val nameColor = when (selectedColor) {
        2 -> {
            "متوسط"
        }

        3 -> {
            "مهم"
        }

        else -> {
            "عادی"
        }
    }
    if (id != 0) {
        header = "ویرایش یادداشت"
        bottom = "ویرایش یادداشت"


        LaunchedEffect(true) {
            notesViewModel.getNotesItem(id).collectLatest { item ->
                title = item.title
                body = item.body
                contactPhone = item.phone
                address = item.address
                selectedColor = item.taskColor
                item.uri?.let { uris ->
                    val validUris = uris.filterNot { it == Uri.EMPTY }
                    selectedImageUriList.addAll(validUris)
                }
                createTime = item.createTime
                oldTitle = item.title
                oldBody = item.body
            }
        }
    }
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    BackHandler(enabled = title != oldTitle || body != oldBody) {
        showSheetDiscard = true
    }
    SheetSaveDiscard(
        show = showSheetDiscard,
        onDismissRequest = { showSheetDiscard = false },
        exit = {
            showSheetDiscard = false
            focusManager.clearFocus()
            navHostController.navigateUp()
        }
    )

    BottomSheetSelectedColor(
        title = "لطفا اولویت یادداشت رو انتخاب کنید",
        onClick = { colorIndex -> selectedColor = colorIndex })

    Scaffold(
        snackbarHost = {
            MySnackbarHost(
                snackBarHostState,
                action = {data->
                    IconButton(onClick = { data.dismiss() }) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.background,
                        )
                    }
                }
            )
        },
        bottomBar = {
            Bottom(
                title = bottom,
                onUpsertItem = {
                    focusManager.clearFocus()
                    if (title.isEmpty()) {
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                "عنوان یادداشت را مشخص کنید",
                                duration = SnackbarDuration.Short,
                                withDismissAction = true
                            )
                        }
                    } else if (body.isEmpty()) {
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                "توضیحات یادداشت را مشخص کنید",
                                duration = SnackbarDuration.Short,
                                withDismissAction = true
                            )
                        }
                    } else {
                        val dates = PersianDate()
                        notesViewModel.upsertNotesItem(
                            NotesItem(
                                id = id,
                                title = title,
                                body = body,
                                taskColor = selectedColor,
                                phone = contactPhone,
                                address = address,
                                uri = selectedImageUriList,
                                createTime = "${dates.hour}:${dates.min}",
                                createDate = "${dates.year}/${dates.month}/${dates.day}"
                            )
                        )
                        navHostController.popBackStack()

                    }
                },
                onBack = {
                    if (title != oldTitle || body != oldBody) {
                        showSheetDiscard = true
                    } else {
                        focusManager.clearFocus()
                        navHostController.navigateUp()
                    }
                })
        },
        topBar = {
            TopBar(title = header) {
                if (title != oldTitle || body != oldBody) {
                    showSheetDiscard = true
                } else {
                    focusManager.clearFocus()
                    navHostController.navigateUp()
                }
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
            val textFieldColor = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.scrim,
                cursorColor = Color(0xFF2196F3)
            )


            TextField(
                colors = textFieldColor,
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
                onValueChange = { isTitle ->
                    title = isTitle
                },
                textStyle = MaterialTheme.typography.labelMedium.copy(
                    textAlign = TextAlign.Center
                )
            )

            HorizontalDivider(
                modifier = Modifier.padding(top = 10.dp, bottom = 3.dp),
                thickness = 0.6.dp,
                color = Color.LightGray.copy(0.5f)
            )

            TextField(
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Notes,
                        contentDescription = "",
                        tint = if (body.isEmpty()) MaterialTheme.colorScheme.scrim.copy(0.8f) else MaterialTheme.colorScheme.primary
                    )
                },
                colors = textFieldColor,
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
                onValueChange = { isBody ->
                    body = isBody
                },
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    textAlign = TextAlign.Start
                )
            )
            HorizontalDivider(
                thickness = 0.6.dp,
                color = Color.LightGray.copy(0.5f)
            )

            //selected contact
            TextField(
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.PhoneIphone,
                        contentDescription = "",
                        tint = if (contactPhone.isEmpty()) MaterialTheme.colorScheme.scrim.copy(0.8f)
                        else MaterialTheme.colorScheme.primary,
                    )
                },
                trailingIcon = {
                    if (contactPhone.isNotEmpty()) {
                        IconButton(onClick = {
                            val intent = Intent(Intent.ACTION_DIAL)
                            intent.data = Uri.parse("tel:$contactPhone")
                            ContextCompat.startActivity(context, intent, null)

                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Call,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                },
                placeholder = {
                    Text(
                        text = "شماره تلفن",
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.scrim.copy(0.7f),
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                colors = textFieldColor,
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
                thickness = 0.6.dp,
                color = Color.LightGray.copy(0.5f)
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = "",
                        tint = if (address.isEmpty()) MaterialTheme.colorScheme.scrim.copy(0.8f) else MaterialTheme.colorScheme.primary
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
                colors = textFieldColor,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    textAlign = TextAlign.Start
                ),
                maxLines = 4,
                value = address,
                onValueChange = { itt ->
                    address = TaskHelper.taskByLocate(itt)
                })

            HorizontalDivider(
                thickness = 0.6.dp,
                color = Color.LightGray.copy(0.5f)
            )
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                onClick = { showBottomSheetSelectedColor.value = true })
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.PriorityHigh,
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


            }

            HorizontalDivider(
                thickness = 6.dp,
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
                shape = RoundedCornerShape(0.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { expanded = !expanded })
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp, horizontal = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.AttachFile,
                            modifier = Modifier.padding(start = 2.dp, end = 4.dp),
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.scrim.copy()
                        )
                        Text(
                            text = "پیوست",
                            color = MaterialTheme.colorScheme.scrim,
                            style = MaterialTheme.typography.bodyLarge

                        )

                    }

                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowDown, contentDescription = "",
                        modifier = Modifier
                            .padding(start = 2.dp, end = 4.dp)
                            .rotate(rotateState),
                        tint = MaterialTheme.colorScheme.scrim.copy()
                    )

                }
            }

            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(animationSpec = tween(1000)),
                exit = fadeOut() + shrinkVertically(animationSpec = tween(1000))
            ) {
                Column {
                    if (selectedImageUriList.isNotEmpty()) {
                        selectedImageUriList.forEach { uri ->
                            SaveImageCard(uri, context) {
                                IconButton(
                                    modifier = Modifier.padding(bottom = 8.dp),
                                    onClick = {
                                        selectedImageUriList.remove(uri)
                                    }) {
                                    Icon(
                                        imageVector = Icons.Rounded.DeleteForever,
                                        contentDescription = "",
                                        Modifier.size(30.dp),
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    } else {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            text = "هیچ پیوستی اضافه نکرده اید",
                            color = MaterialTheme.colorScheme.scrim,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                    Surface(
                        shape = RoundedCornerShape(9.dp),
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(),
                        border = BorderStroke(1.dp, color = Color.DarkGray),
                        onClick = {
                            multipleImagePickerLauncher.launch(arrayOf("*/*"))
                        })
                    {
                        Row(
                            modifier = Modifier
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.attachment_ic),
                                contentDescription = "",
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .size(30.dp)
                            )
                            Text(
                                text = "افزودن عکس , فایل",
                                color = MaterialTheme.colorScheme.scrim,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                    }

                }
            }
            HorizontalDivider(
                thickness = 6.dp,
                color = Color.LightGray.copy(0.1f)
            )
        }

    }
}


@Composable
private fun Bottom(
    title: String,
    onUpsertItem: () -> Unit,
    onBack: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Button(
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ),
            modifier = Modifier
                .weight(0.6f)
                .padding(horizontal = 4.dp),
            onClick = { onUpsertItem() }) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        OutlinedButton(
            modifier = Modifier
                .weight(0.4f)
                .padding(horizontal = 4.dp),
            shape = RoundedCornerShape(12.dp),

            border = BorderStroke(1.dp, MaterialTheme.colorScheme.scrim),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.scrim
            ),
            onClick = { onBack() }) {
            Text(
                text = "لفو",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }

}


