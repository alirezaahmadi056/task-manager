package ir.hoseinahmadi.taskmanager.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.OpenInNew
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp


@Composable
fun test(){

    var selectedImageUriList by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }

    val multipleImagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uriList ->
            selectedImageUriList = uriList
        }
    )


    val context = LocalContext.current
    LazyColumn(modifier = Modifier.fillMaxSize()) {

        item {
            Button(onClick = {
                multipleImagePickerLauncher.launch("*/*")
            }) {
                Text(text = "pick single Image")
            }
        }
        items(selectedImageUriList) { uri ->


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = uri
                            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                        }
                        context.startActivity(intent)
                    },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {

                }
                Icon(
                    imageVector = Icons.Rounded.OpenInNew,
                    contentDescription = "Open File"
                )
            }
            Divider()
        }
    }
}