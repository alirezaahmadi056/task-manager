package ir.hoseinahmadi.taskmanager.ui.screen.task

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.hoseinahmadi.taskmanager.data.db.task.TaskItem

@Composable
fun TaskItemCard(item:TaskItem){

    Card(
        modifier = Modifier.fillMaxWidth()
            .height(100.dp),
        onClick = { /*TODO*/ }) {

    }
}