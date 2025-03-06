package info.alirezaahmadi.taskmanager.util

import android.os.Build
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
fun requestPermissionNotification(
    notificationPermission: PermissionState,
    isGranted: (Boolean) -> Unit,
    permissionState: (PermissionState) -> Unit,
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (notificationPermission.status.isGranted) {
            isGranted(true)
        } else {
            if (notificationPermission.status.shouldShowRationale) {
                isGranted(false)
            } else {
                permissionState(notificationPermission)
            }
        }
    } else {
        isGranted(true)
    }
}
