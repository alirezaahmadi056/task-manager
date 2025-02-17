package ir.lrn.kara.ui.graph.exerciseProgram.startExercise

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ir.lrn.kara.R
import ir.lrn.kara.data.db.exerciseProgram.ExerciseProgramItem
import ir.lrn.kara.ui.graph.exerciseProgram.ExerciseItemCard

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SheetListExercise(
    isShow: Boolean,
    day: String,
    exerciseList: List<ExerciseProgramItem>,
    onClick: (Int) -> Unit,
    onDismiss: () -> Unit
) {

    if (!isShow) return
    ModalBottomSheet(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(12.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            stickyHeader {
                Text(
                    text = "${stringResource(R.string.today_exercise_program)} ($day)",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                )
            }
            items(items = exerciseList, key = { it.id }) { exercise ->
                ExerciseItemCard(
                    item = exercise,
                    onClick = {
                        onDismiss()
                        findClickIndex(exercise, exerciseList)?.let(onClick)
                    },
                    onLongClick = {
                        onDismiss()
                        findClickIndex(exercise, exerciseList)?.let(onClick)
                    }
                )
            }
            item { Spacer(Modifier.height(8.dp)) }
            item { Spacer(Modifier.navigationBarsPadding()) }
        }
    }


}

fun findClickIndex(
    element: ExerciseProgramItem,
    items: List<ExerciseProgramItem>
): Int? {
    val index = items.indexOf(element)
    return if (index == -1) null else index
}