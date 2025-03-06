package info.alirezaahmadi.taskmanager.ui.component

import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun MySnackbarHost(
    snackbarHostState: SnackbarHostState,
    action: @Composable ((SnackbarData) -> Unit)? = null,
    ) {
    SnackbarHost(snackbarHostState) {data->
        Snackbar(
            action = {
                if (data.visuals.withDismissAction) {
                    action?.invoke(data)
                }
            },
            containerColor = MaterialTheme.colorScheme.scrim,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .imePadding()
                .padding(vertical = 8.dp, horizontal = 4.dp)
        ) {
            Text(
                text = data.visuals.message,
                color = MaterialTheme.colorScheme.background,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }

}