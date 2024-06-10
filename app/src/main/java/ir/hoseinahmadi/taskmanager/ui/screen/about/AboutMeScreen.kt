package ir.hoseinahmadi.taskmanager.ui.screen.about

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ir.hoseinahmadi.taskmanager.data.model.AboutResponse
import ir.hoseinahmadi.taskmanager.viewModel.AboutViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AboutMeScreen(
    navHostController: NavHostController,
    aboutViewModel: AboutViewModel = hiltViewModel()
) {

    var item by remember {
        mutableStateOf(AboutResponse())
    }

    LaunchedEffect(key1 = true) {
        aboutViewModel.getAllData()
        aboutViewModel.allData.collectLatest {response->
                item =response
        }
    }
    Scaffold(
        topBar = {
            TopBar { navHostController.popBackStack() }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(it)
                .background(MaterialTheme.colorScheme.background),
        ) {
            AboutHeaderSection(item)

        }
    }

}

@Composable
private fun TopBar(onBack:()->Unit){
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = {onBack() }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowForward,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.scrim
                )
            }
            Text(
                text = "درباره من",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                color = MaterialTheme.colorScheme.scrim,
                modifier = Modifier.padding(horizontal = 3.dp)
            )
        }
        HorizontalDivider(
            modifier = Modifier.padding(bottom = 15.dp),
            thickness = 2.dp,
            color = Color.LightGray.copy(0.5f)
        )

    }
}