package info.alirezaahmadi.taskmanager.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import info.alirezaahmadi.taskmanager.R

@Composable
fun EmptyList(text:String?=null) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(110.dp))
        Image(
            painter = painterResource(id = R.drawable.emptylist),
            contentDescription = ""
        )
        text?.let {
            Text(
                text= it,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.scrim
            )
        }
    }
}