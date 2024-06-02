package ir.hoseinahmadi.taskmanager.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.hilt.navigation.compose.hiltViewModel
import ir.hoseinahmadi.taskmanager.util.Constants.SORT_NOTE
import ir.hoseinahmadi.taskmanager.util.Constants.SORT_TASK
import ir.hoseinahmadi.taskmanager.viewModel.DatStoreViewModel


val showSelectedSortNotList = mutableStateOf(false)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectedSortNotList(
    isNoteScreen: Boolean,
    noteSort: (sort: Int) -> Unit,
    taskSort: (sort: Int) -> Unit,
    datStoreViewModel: DatStoreViewModel = hiltViewModel()
) {


    val show by remember {
        showSelectedSortNotList
    }
    if (show) {
        val item = listOf(
            "آخرین نوشته",
            "اولویت کم",
            "اولویت معمولی",
            "اولویت بالا",
        )
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest = { showSelectedSortNotList.value = false },
            confirmButton = { /*TODO*/ },
            title = {
                Text(
                    text = "ترتیب یادداشت ها بر اساس",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Column {
                    item.fastForEachIndexed { index: Int, s: String ->
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = if (isNoteScreen) SORT_NOTE == index else SORT_TASK == index,
                                onClick = {
                                    if (isNoteScreen) {
                                        noteSort(index)
                                        SORT_NOTE = index
                                        datStoreViewModel.saveNoteSort(index)
                                        showSelectedSortNotList.value = false
                                    } else {
                                        taskSort(index)
                                        SORT_TASK = index
                                        datStoreViewModel.saveTaskSort(index)
                                        showSelectedSortNotList.value = false
                                    }

                                }
                            )
                            Text(
                                text = s,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.scrim
                            )
                        }
                        HorizontalDivider(
                            thickness = 0.3.dp,
                            color = Color.LightGray.copy(0.3f)
                        )
                    }

                }
            }
        )


    }


}