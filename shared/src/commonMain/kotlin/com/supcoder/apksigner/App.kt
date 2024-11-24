package com.supcoder.apksigner


import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.DrawerValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupPositionProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.russhwolf.settings.ExperimentalSettingsApi
import com.supcoder.apksigner.model.ThemeConfig
import com.supcoder.apksigner.platform.createFlowSettings
import com.supcoder.apksigner.router.Page
import com.supcoder.apksigner.theme.AppTheme
import com.supcoder.apksigner.ui.ApkInformation
import com.supcoder.apksigner.ui.ApkSignature
import com.supcoder.apksigner.ui.DecompileScreen
import com.supcoder.apksigner.ui.ModalNavigationDrawerWithLeftLayout
import com.supcoder.apksigner.ui.SettingsScreen
import com.supcoder.apksigner.ui.SignatureGeneration
import com.supcoder.apksigner.ui.SignatureInformation
import com.supcoder.apksigner.ui.component.DarkModeToggleButton
import com.supcoder.apksigner.ui.component.navigation.NavigationConstants
import com.supcoder.apksigner.ui.component.navigation.SimpleNavigationItem
import com.supcoder.apksigner.util.logger
import com.supcoder.apksigner.vm.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.math.abs


@OptIn(ExperimentalSettingsApi::class)
@Composable
@Preview
fun App() {
    val viewModel = viewModel { MainViewModel(settings = createFlowSettings()) }
    val themeConfig by viewModel.themeConfig.collectAsState()
    val useDarkTheme = when (themeConfig) {
        ThemeConfig.LIGHT -> false
        ThemeConfig.DARK -> true
        ThemeConfig.FOLLOW_SYSTEM -> isSystemInDarkTheme()
        else -> false
    }
    AppTheme(useDarkTheme) {
        Surface {
            MainContentScreen(viewModel)
        }

    }
}

suspend fun collectOutputPath(viewModel: MainViewModel) {
    val userData = viewModel.userData.drop(0).first()
    val outputPath = userData.defaultOutputPath
    viewModel.apply {
        updateApkSignature(viewModel.apkSignatureState.copy(outputPath = outputPath))
        updateSignatureGenerate(viewModel.keyStoreInfoState.copy(keyStorePath = outputPath))
        updateJunkCodeInfo(viewModel.junkCodeInfoState.copy(outputPath = outputPath))
        updateIconFactoryInfo(viewModel.iconFactoryInfoState.copy(outputPath = outputPath))
    }
}


/**
 * 主要模块
 */
@OptIn(ExperimentalMaterial3Api::class, FlowPreview::class, ExperimentalComposeUiApi::class)
@Composable
fun MainContentScreen(viewModel: MainViewModel) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var isCollapsed = remember { mutableStateOf(false) }

    val railWidth = NavigationConstants.navigationRailItemWidth(isCollapsed.value)
    val animatedWidth by animateDpAsState(targetValue = railWidth)

    val iconSize = NavigationConstants.iconSize(isCollapsed.value)
    val animatedIconSize by animateDpAsState(targetValue = iconSize)

    var drawerState = rememberDrawerState(DrawerValue.Closed)


    val events = remember { MutableSharedFlow<Int>() }

    // 收集 events 流，并在 150 毫秒内只处理最后一次事件
    LaunchedEffect(events) {
        events.debounce(150).collect {
            if (drawerState.isClosed) {
                drawerState.open()
            }
        }
    }

    scope.launch {
        collectOutputPath(viewModel)
    }

    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
    }) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            Row(
                modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically
            ) {
                val pages = Page.entries.toMutableList()
                // 导航栏
                NavigationRail(Modifier.fillMaxHeight().width(animatedWidth)) {
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        IconButton(onClick = { isCollapsed.value = !isCollapsed.value }) {
                            Icon(
//                                imageVector = if (isCollapsed.value) Icons.Filled.ArrowForwardIos else Icons.Filled.ArrowBackIosNew,
                                imageVector = if (isCollapsed.value) Icons.Filled.Menu
                                else Icons.Filled.MenuOpen,
                                contentDescription = "Collapse/Expand"
                            )
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            pages.forEachIndexed { _, page ->
                                TooltipBox(
                                    positionProvider = rememberRichTooltipPositionProvider(), tooltip = {
                                        PlainTooltip {
                                            Text(page.title, style = MaterialTheme.typography.bodySmall)
                                        }
                                    }, state = rememberTooltipState(), enableUserInput = viewModel.uiPageIndex != page
                                ) {
                                    SimpleNavigationItem(
                                        tag = page.tag,
                                        label = {
                                            if (!isCollapsed.value) Text(
                                                page.title,
                                                style = MaterialTheme.typography.labelMedium,
                                                maxLines = 1
                                            )
                                        },
                                        icon = {
                                            Icon(
                                                page.icon,
                                                contentDescription = page.title,
                                                modifier = Modifier.size(animatedIconSize)
                                            )
                                        },
                                        selected = viewModel.uiPageIndex == page,
                                        onClick = { viewModel.updateUiState(page) },
                                        alwaysShowLabel = false,
                                        isCollapsed = isCollapsed.value,
                                        onHover = { isHovered, tag ->
                                            logger("${page.title} isHovered -> $isHovered")
                                            if (isHovered) {
                                                scope.launch {
                                                    events.emit(value = tag)
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                        }
                        DarkModeToggleButton(viewModel, isCollapsed)
                    }
                }
                // 主界面
                val content: @Composable (Page) -> Unit = { page ->
                    when (page) {
//                        Page.SIGNATURE_INFORMATION -> HomeScreen {  }
                        Page.SIGNATURE_INFORMATION -> SignatureInformation(viewModel)
//                        Page.JSON_FORMAT -> JsonScreen(viewModel)
//                        Page.JSON_FORMAT -> DrawerScreen(drawerState)
                        Page.JSON_FORMAT -> ModalNavigationDrawerWithLeftLayout()
                        Page.APK_DECOMPILE -> DecompileScreen(viewModel)
                        Page.APK_INFORMATION -> ApkInformation(viewModel)
                        Page.APK_SIGNATURE -> ApkSignature(viewModel)
                        Page.SIGNATURE_GENERATION -> SignatureGeneration(viewModel)
                        Page.SETTINGS -> SettingsScreen(viewModel)
                        else -> SettingsScreen(viewModel)
                    }
                }


                Crossfade(
                    targetState = viewModel.uiPageIndex, modifier = Modifier.fillMaxSize(), content = content
                )


            }
        }
    }
    val snackbarVisuals by viewModel.snackbarVisuals.collectAsState()
    snackbarVisuals.apply {
        if (message.isBlank() || abs(timestamp - System.currentTimeMillis()) > 50) return@apply
        scope.launch(Dispatchers.Main) {
            val snackbarResult = snackbarHostState.showSnackbar(this@apply)
            when (snackbarResult) {
                SnackbarResult.ActionPerformed -> action?.invoke()
                SnackbarResult.Dismissed -> Unit
            }
        }
    }
}

@Composable
private fun rememberRichTooltipPositionProvider(): PopupPositionProvider {
    val tooltipAnchorSpacing = with(LocalDensity.current) { 4.dp.roundToPx() }
    return remember(tooltipAnchorSpacing) {
        object : PopupPositionProvider {
            override fun calculatePosition(
                anchorBounds: IntRect, windowSize: IntSize, layoutDirection: LayoutDirection, popupContentSize: IntSize
            ): IntOffset {
                var x = anchorBounds.right
                if (x + popupContentSize.width > windowSize.width) {
                    x = anchorBounds.left - popupContentSize.width
                    if (x < 0) x = anchorBounds.left + (anchorBounds.width - popupContentSize.width) / 2
                }
                x -= tooltipAnchorSpacing
                val y = anchorBounds.top + (anchorBounds.height - popupContentSize.height) / 2
                return IntOffset(x, y)
            }
        }
    }
}


