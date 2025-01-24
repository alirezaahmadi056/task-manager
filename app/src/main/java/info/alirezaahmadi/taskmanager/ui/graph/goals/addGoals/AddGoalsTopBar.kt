package info.alirezaahmadi.taskmanager.ui.graph.goals.addGoals

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import info.alirezaahmadi.taskmanager.R

@Composable
fun AddGoalsTopBar(
    onBack: () -> Unit,
) {
    Box(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = stringResource(R.string.add_goals),
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
            color = Color.Black,
            modifier = Modifier.align(Alignment.Center),
            fontWeight = FontWeight.Black
        )
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.Rounded.ArrowForward,
                contentDescription = "",
                tint = Color.Black
            )
        }
    }
}