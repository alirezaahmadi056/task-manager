package info.alirezaahmadi.taskmanager.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
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
import info.alirezaahmadi.taskmanager.util.Constants
import info.alirezaahmadi.taskmanager.util.Constants.SORT_NOTE
import info.alirezaahmadi.taskmanager.util.Constants.SORT_TASK
import info.alirezaahmadi.taskmanager.viewModel.DatStoreViewModel


val showSelectedSortNotList = mutableStateOf(false)

enum class PageType {
    TASK,
    NOTE,
    ROUTINE
}

@Composable
fun SelectedSortNotList(
    pageType: PageType,
    noteSort: (sort: Int) -> Unit,
    taskSort: (sort: Int) -> Unit,
    routineSort:(Int)->Unit,
    datStoreViewModel: DatStoreViewModel = hiltViewModel()
) {


    val show by remember {
        showSelectedSortNotList
    }
    if (show) {
        val item = mutableListOf(
            "جدید ترین",
            "اولویت کم",
            "اولویت معمولی",
            "اولویت بالا",
        )
        if (pageType==PageType.ROUTINE){
            item.add("اولویت زمانی")
        }
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest = { showSelectedSortNotList.value = false },
            confirmButton = { },
            title = {
                Text(
                    text = " ترتیب لیست بر اساس",
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
                            RadioButton(
                                selected =index == when(pageType){
                                     PageType.NOTE -> SORT_NOTE
                                     PageType.TASK ->Constants.SORT_TASK
                                    PageType.ROUTINE ->Constants.ROUTINE_SORT
                                },

                                onClick = {
                                    when(pageType){
                                        PageType.TASK ->  {
                                            taskSort(index)
                                            SORT_TASK = index
                                            datStoreViewModel.saveTaskSort(index)
                                            showSelectedSortNotList.value = false
                                        }
                                        PageType.NOTE -> {
                                            noteSort(index)
                                            SORT_NOTE = index
                                            datStoreViewModel.saveNoteSort(index)
                                            showSelectedSortNotList.value = false
                                        }
                                        PageType.ROUTINE -> {
                                            routineSort(index)
                                            Constants.ROUTINE_SORT = index
                                            datStoreViewModel.saveRoutineSort(index)
                                            showSelectedSortNotList.value = false
                                        }
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
                            color = Color.LightGray.copy(0.4f)
                        )
                    }

                }
            }
        )


    }


}