package info.alirezaahmadi.taskmanager.topBarMain

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.QuestionAnswer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import info.alirezaahmadi.taskmanager.viewModel.NotifeViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AlertDialogSendMessage(
    show: Boolean,
    onDismissRequest: () -> Unit,
    notifeViewModel: NotifeViewModel = hiltViewModel()
) {
    if (show) {
        var title by remember {
            mutableStateOf("")
        }

        var body by remember {
            mutableStateOf("")
        }

        var checkedInput by remember {
            mutableStateOf(false)
        }
        var loading by remember {
            mutableStateOf(false)
        }
        val context = LocalContext.current
        LaunchedEffect(true) {
            notifeViewModel.notifeResponse.collectLatest {
                if (it.success == 1) {
                    Toast.makeText(context, "پیام با موفقیت ارسال شد", Toast.LENGTH_SHORT).show()
                    loading = false
                    onDismissRequest()
                    notifeViewModel.resetResponse()
                }
            }


        }
        AlertDialog(
            onDismissRequest = { onDismissRequest() },
            confirmButton = { /*TODO*/ },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.QuestionAnswer,
                    contentDescription = ""
                )
            },
            title = {
                Text(
                    text = "ارسال پیام به توسعه دهنده",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.scrim
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    OutlinedTextField(
                        isError = (checkedInput && title.isEmpty()),
                        maxLines = 1,
                        textStyle = MaterialTheme.typography.bodyLarge,
                        shape = RoundedCornerShape(9.dp),
                        value = title, onValueChange = { title = it },
                        label = {
                            Text(
                                text = "موضوع پیام",
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        })
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        isError = (checkedInput && body.isEmpty()),
                        textStyle = MaterialTheme.typography.bodyMedium,
                        shape = RoundedCornerShape(9.dp),
                        value = body, onValueChange = {
                            body = it
                        },
                        label = {
                            Text(
                                text = " پیام",
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        })


                    Button(
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp)
                            .padding(top = 12.dp),
                        onClick = {
                            if (title.isNotEmpty() && body.isNotEmpty()) {
                                loading = true
                                checkedInput = false
                                val message =
                                    "پیام جدید از تسک منیجر :\n\nموضوع : $title\n متن پیام : \n $body "
                                notifeViewModel.senMessage(message)
                            } else {
                                checkedInput = true
                            }
                        }) {
                        if (loading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(30.dp)
                            )
                        } else {
                            Text(
                                text = "ارسال پیام",
                                color = Color.White,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                    }


                }

            }
        )
    }


}