package info.alirezaahmadi.taskmanager.ui.graph.dreame.dreamDetail

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import info.alirezaahmadi.taskmanager.R

@Composable
fun DreamDetailTopBar(
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onDeleted: () -> Unit
) {
    Row(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBack
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowForward,
                contentDescription = "",
                tint = Color.Black
            )
        }
        Spacer(Modifier.width(4.dp))
        IconButton(
            onClick = onEdit
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_edit),
                contentDescription = "",
                tint = Color.Black
            )
        }
        Spacer(Modifier.width(4.dp))
        IconButton(
            onClick = onDeleted
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_bin),
                contentDescription = "",
                tint = Color(0xffC40606)
            )
        }
    }
}