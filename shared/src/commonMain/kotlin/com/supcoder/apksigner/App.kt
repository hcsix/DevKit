package com.supcoder.apksigner


import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.rememberDrawerState
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.russhwolf.settings.ExperimentalSettingsApi
import com.supcoder.apksigner.model.ThemeConfig
import com.supcoder.apksigner.platform.createFlowSettings
import com.supcoder.apksigner.router.Page
import com.supcoder.apksigner.router.fetchNavList
import com.supcoder.apksigner.router.getContent
import com.supcoder.apksigner.theme.AppTheme
import com.supcoder.apksigner.theme.with_drawer
import com.supcoder.apksigner.ui.component.DarkModeToggleButton
import com.supcoder.apksigner.ui.component.navigation.NavigationConstants
import com.supcoder.apksigner.ui.component.navigation.SimpleNavigationItem
import com.supcoder.apksigner.ui.component.rememberRichTooltipPositionProvider
import com.supcoder.apksigner.util.detectMouseMovement
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

    val drawerNavPage = remember { mutableStateOf(Page.JSON_FORMAT.tag) }


    val drawerNavTag = remember { mutableStateOf("") }

    val events = remember { MutableSharedFlow<Int>() }

    // 收集 events 流，并在 150 毫秒内只处理最后一次事件
    LaunchedEffect(events) {
        events.debounce(150).collect { it ->
            logger("events -> $it")
            if (((it == Page.JSON_FORMAT.tag) or (it == Page.APK_SIGNATURE.tag))) {
                drawerNavPage.value = it
                if (drawerState.isClosed){
                    drawerState.open()
                }
                logger("events -> $it  OPEN")
            }
            if (drawerState.isOpen and ((it != Page.JSON_FORMAT.tag) and (it != Page.APK_SIGNATURE.tag))) {
                drawerState.close()
                logger("events -> $it  CLOSE")
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
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            val pages = Page.entries.toMutableList()
            val content: @Composable (Page) -> Unit = { page ->
                getContent(viewModel, page, drawerNavTag.value)
            }
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet(modifier = Modifier.width(with_drawer)) {
                        val itemsList = fetchNavList(drawerNavPage.value)
                        LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                            items(itemsList) { item ->
                                NavigationDrawerItem(
                                    label = { Text(item.title, fontSize = 14.sp) },
                                    selected = (drawerNavTag.value == item.navTag),
                                    onClick = {
                                        scope.launch {
                                            drawerState.close()
                                            drawerNavTag.value = item.navTag
                                            viewModel.updateUiState(item.page)
                                        }
                                    }
                                    , modifier = Modifier.padding(bottom = 8.dp).height(48.dp)
                                )

                            }
                        }
                    }
                },
                content = {
                    Crossfade(
                        targetState = viewModel.uiPageIndex, modifier = Modifier.fillMaxSize(), content = content
                    )
                }, gesturesEnabled = false,
                modifier = Modifier.padding(start = animatedWidth).pointerInput(Unit) {
                    detectMouseMovement { x, y ->
                        if (x > with_drawer.toPx() && drawerState.isOpen) {
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    }
                }
            )
            // 导航栏
            NavigationRail(Modifier.fillMaxHeight().width(animatedWidth).align(Alignment.CenterStart)) {
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




