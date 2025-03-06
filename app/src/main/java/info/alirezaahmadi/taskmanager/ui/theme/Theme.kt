package info.alirezaahmadi.taskmanager.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF3F51B5),
    secondary = PurpleGrey80,
    tertiary = Pink80,

    background = Color(0xff121212),

    scrim = Color.White,


    error = DarkRed,
    onSecondary = DarkOrange,
    onPrimary = DarkGreen

)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2196F3),
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = Color.White,

    scrim = Color.Black,

    error = LightRed,
    onSecondary = LightOrange,
    onPrimary = LightGreen


)

@Composable
fun TaskManagerTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor =colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}