package info.alirezaahmadi.taskmanager.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import info.alirezaahmadi.taskmanager.R

@Composable
fun EmptyList() {
val configuration =LocalConfiguration.current
    Column(
        modifier = Modifier.fillMaxWidth()
            .height(configuration.screenHeightDp.dp -250.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.emptylist),
            contentDescription = ""
        )
    }
}