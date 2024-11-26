package com.supcoder.devkit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.MenuScope
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.supcoder.devkit.usecase.ResourceUseCase
import com.supcoder.devkit.util.isMac
import com.supcoder.devkit.util.isWindows


//fun main() = singleWindowApplication(
//    title = "Code Viewer",
//    state = WindowState(width = 1280.dp, height = 768.dp),
//    icon = BitmapPainter(useResource("logo.png", ::loadImageBitmap)),
//) {
//    App()
//}

val resourceUserCase = ResourceUseCase()

fun main() = application {

    // 先将所需的资源拷贝到本机
    resourceUserCase.copyLibsToLocal()

    val applicationState = remember { ApplicationState() }

    if (isWindows && applicationState.windows.isNotEmpty()) {
        ApplicationTray(applicationState)
    }
    for (window in applicationState.windows) {
        key(window) {
            Window(window)
        }
    }

}


private class WindowState(
    val title: String = "DevKit",
    val openNewWindow: () -> Unit,
    val exit: () -> Unit,
    private val close: (WindowState) -> Unit
) {
    fun close() = close(this)
}


@Composable
private fun ApplicationScope.ApplicationTray(state: ApplicationState) {
    Tray(icon = painterResource("logo.png"), tooltip = "AndroidToolKit", menu = { ApplicationMenu(state) })
}

@Composable
private fun Window(
    state: WindowState
) = Window(
    onCloseRequest = state::close, title = state.title, icon = painterResource("logo.png")
//    onCloseRequest = state::close, title = state.title, icon = painterResource("logo.png")
) {
    if (isMac) {
        MenuBar {
            Menu("文件") {
                Item("打开新的窗口", onClick = state.openNewWindow)
                Item("关闭窗口", onClick = { state.close() })
                Separator()
                Item("全部关闭", onClick = state.exit)
            }
        }
    }
    App()
}

@Composable
private fun MenuScope.ApplicationMenu(state: ApplicationState) {
    Item(text = "打开新的窗口", onClick = state::openNewWindow)
}

private class ApplicationState {

    private val _windows = mutableStateListOf<WindowState>()
    val windows: List<WindowState> get() = _windows

    init {
        _windows.add(WindowState())
    }

    fun openNewWindow() {
        _windows.add(WindowState())
    }

    fun exit() {
        _windows.clear()
    }

    private fun WindowState() = WindowState(
        title = "DevKit", openNewWindow = ::openNewWindow, exit = ::exit, _windows::remove
    )
}






