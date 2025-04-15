package ir.lrn.kara.ui.graph.first

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ir.lrn.kara.data.model.FirstRouteData

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SheetCustomList(
    isShow: Boolean,
    allList: List<FirstRouteData>,
    activeList: List<FirstRouteData>,
    activeId: List<FirstRouteData>,
    updateId: (Set<Int>) -> Unit,
    onDismiss: () -> Unit
) {
    if (!isShow) return

    val selectedIds = remember { mutableStateListOf<Int>() }

    // مقدار اولیه
    LaunchedEffect(Unit) {
        selectedIds.clear()
        selectedIds.addAll(activeId.map { it.id })
    }
    val sheetState = rememberModalBottomSheetState(true)
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.background,
        shape = RoundedCornerShape(12.dp),
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = "فعال‌ها",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            FlowRow {
                activeList.forEach { item ->
                    RouteItem(
                        name = stringResource(id = item.nameRes),
                        selected = item.id in selectedIds,
                        onSelected = { isChecked ->
                            if (isChecked) selectedIds.add(item.id)
                            else selectedIds.remove(item.id)
                        }
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Text(
                text = "غیرفعال‌ها",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            FlowRow {
                val inactiveList = allList.filter { it.id !in activeList.map { a -> a.id } }
                inactiveList.forEach { item ->
                    RouteItem(
                        name = stringResource(id = item.nameRes),
                        selected = item.id in selectedIds,
                        onSelected = { isChecked ->
                            if (isChecked) selectedIds.add(item.id)
                            else selectedIds.remove(item.id)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    updateId(selectedIds.toSet())
                    onDismiss()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.background,
                    containerColor = MaterialTheme.colorScheme.onBackground
                )
            ) {
                Text("ویرایش", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}


@Composable
private fun RouteItem(
    name: String,
    selected: Boolean,
    onSelected: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Checkbox(
            checked = selected,
            onCheckedChange = onSelected,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.onBackground,
                checkmarkColor = MaterialTheme.colorScheme.background
            )
        )
    }
}