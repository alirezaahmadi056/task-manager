package info.alirezaahmadi.taskmanager.ui.graph.first

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import info.alirezaahmadi.taskmanager.R

@Composable
fun IntroductionSection() {

    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Brush.linearGradient(listOf(Color(0xffFF3EA7), Color(0xffFFBF01))),RoundedCornerShape(12.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.inestagram_logo),
            contentDescription = "",
            modifier = Modifier
                .weight(0.1f)
                .aspectRatio(1f)
        )
        Text(
            text = "از ما در اینستاگرام حمایت کنید!",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            fontWeight = FontWeight.Black,
            modifier = Modifier.weight(0.7f)
                .padding(horizontal = 8.dp),
            textAlign = TextAlign.Center
        )
        Box(
            modifier = Modifier.weight(0.2f)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White,RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "@Lrn.ir",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}