package ir.hoseinahmadi.taskmanager.ui.screen.notes.addNotes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SheetSaveDiscard(
    show: Boolean,
    text: String,
    onDismissRequest: () -> Unit,
    save: () -> Unit,
    exit: () -> Unit
) {
    if (show) {
        ModalBottomSheet(
            shape = RoundedCornerShape(12.dp),
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest = onDismissRequest
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .navigationBarsPadding()
            ) {
                Text(
                    text = text,
                    color = MaterialTheme.colorScheme.scrim,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {

                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                        ),
                        onClick = {
                            save()
                            exit()
                        }
                    ) {
                        Text(
                            text = "ذخیره",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    TextButton(onClick = exit) {
                        Text(
                            text = "خروج",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.scrim,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

            }
        }
    }
}