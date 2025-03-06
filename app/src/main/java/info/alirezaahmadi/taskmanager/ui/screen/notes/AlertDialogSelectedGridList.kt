package info.alirezaahmadi.taskmanager.ui.screen.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import info.alirezaahmadi.taskmanager.util.Constants
import info.alirezaahmadi.taskmanager.util.Constants.GRIDLIST
import info.alirezaahmadi.taskmanager.viewModel.DatStoreViewModel

var showDialogSelectedGridList = mutableStateOf(false)

@Composable
fun AlertDialogSelectedGridList(
    gridList :(Boolean) ->Unit,
    datStoreViewModel: DatStoreViewModel = hiltViewModel()
) {
    val show by remember {
        showDialogSelectedGridList
    }
    if (show) {
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.background,
            textContentColor = MaterialTheme.colorScheme.scrim,
            titleContentColor = MaterialTheme.colorScheme.scrim,
            iconContentColor = MaterialTheme.colorScheme.scrim,
            onDismissRequest = {
                showDialogSelectedGridList.value = false
            },
            title = {
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = " نوع نمایش لیست را انتخاب کنید",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.scrim
                )
            },
            text = {
                Column(
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = GRIDLIST,
                            onClick = {
                                gridList(true)
                                Constants.GRIDLIST = true
                                datStoreViewModel.saveGridList(true)
                                showDialogSelectedGridList.value = false
                            })
                        Text(
                            text = "لیست شبکه ای",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.scrim
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = !GRIDLIST,
                            onClick = {
                                gridList(false)

                                GRIDLIST =false
                                datStoreViewModel.saveGridList(false)
                                showDialogSelectedGridList.value = false
                            })
                        Text(
                            text = "لیست عادی",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.scrim
                        )

                    }
                }


            },
            confirmButton = { /*TODO*/ }
        )
    }

}