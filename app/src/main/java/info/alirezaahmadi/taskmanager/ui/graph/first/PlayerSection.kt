package info.alirezaahmadi.taskmanager.ui.graph.first

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.SecureFlagPolicy
import io.sanghun.compose.video.RepeatMode
import io.sanghun.compose.video.VideoPlayer
import io.sanghun.compose.video.controller.VideoPlayerControllerConfig
import io.sanghun.compose.video.uri.VideoPlayerMediaItem

@Composable
fun PlayerSection(
    uri: String
) {
    VideoPlayer(
        mediaItems = listOf<VideoPlayerMediaItem>(VideoPlayerMediaItem.NetworkMediaItem(uri)),
        handleLifecycle = true,
        autoPlay = true,
        usePlayerController = true,
        enablePip = true,
        handleAudioFocus = true,
        enablePipWhenBackPressed = false,
        controllerConfig = VideoPlayerControllerConfig(
            showSpeedAndPitchOverlay = true,
            showSubtitleButton = false,
            showCurrentTimeAndTotalTime = true,
            showBufferingProgress = true,
            showForwardIncrementButton = true,
            showBackwardIncrementButton = true,
            showBackTrackButton = false,
            showNextTrackButton = false,
            showRepeatModeButton = false,
            controllerShowTimeMilliSeconds = 5_000,
            showFullScreenButton = false,
            controllerAutoShow = true,
        ),
        repeatMode = RepeatMode.NONE,
        onCurrentTimeChanged = {},
        fullScreenSecurePolicy = SecureFlagPolicy.SecureOn,
        playerInstance = {},
        modifier = Modifier
            .fillMaxSize()
    )
}