package ir.lrn.kara.ui.graph.first

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastMapIndexed
import ir.lrn.kara.data.model.FirstRouteData

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SheetCustomList(
    isShow: Boolean,
    allList: List<FirstRouteData>,
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
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "مدیریت آیتم‌های فعال",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(vertical = 12.dp, horizontal = 5.dp),
                fontWeight = FontWeight.Bold,
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth()
            ){
                allList.fastMapIndexed { index, firstRouteData ->
                        RouteItem(
                            routeData = firstRouteData,
                            selected = firstRouteData.id in selectedIds,
                            onSelected = { isChecked ->
                                if (isChecked) selectedIds.add(firstRouteData.id)
                                else selectedIds.remove(firstRouteData.id)
                            },
                            lastItem =allList.lastIndex ==index
                        )
                    }

            }


            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    updateId(selectedIds.toSet())
                    onDismiss()
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = MaterialTheme.colorScheme.primary
                ),
            ) {
                Text(
                    text = "ذخیره تغییرات",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}


@Composable
private fun RouteItem(
    routeData: FirstRouteData,
    selected: Boolean,
    onSelected: (Boolean) -> Unit,
    lastItem: Boolean
) {
    Row(
        modifier = Modifier
            .clickable { onSelected(!selected) }
            .fillMaxWidth()
            .padding(8.dp)
            .drawBehind {
                if(!lastItem){
                    drawLine(
                        color = Color.LightGray.copy(alpha = 0.4f),
                        start = Offset(0f, size.height), // شروع خط در پایین کامپوزبل
                        end = Offset(size.width, size.height), // پایان خط در پایین کامپوزبل
                        strokeWidth = 1.5.dp.toPx()
                    )
                }

            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(routeData.image),
                contentDescription = "",
                modifier = Modifier.size(35.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(5.dp))
            Text(
                text = stringResource(routeData.nameRes) ,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Checkbox(
            checked = selected,
            onCheckedChange = onSelected,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                checkmarkColor = Color.White
            )
        )
    }
}