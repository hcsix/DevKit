package com.supcoder.apksigner.ui

/**
 * PersistentDrawer
 *
 * @author lee
 * @date 2024/11/24
 */
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.ui.Alignment
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalNavigationDrawerWithLeftLayout() {
    var drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        // 左边的布局


        // 主内容区域
        Box(
            modifier = Modifier
                .padding(start = 200.dp)
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
        ) {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet {
                        Spacer(Modifier.height(12.dp))
                        NavigationDrawerItem(
                            icon = { Icon(Icons.Default.Menu, contentDescription = "Menu") },
                            label = { Text("Home") },
                            selected = true,
                            onClick = {
                                scope.launch { drawerState.close() }
                            }
                        )
                        NavigationDrawerItem(
                            icon = { Icon(Icons.Default.Menu, contentDescription = "Menu") },
                            label = { Text("Settings") },
                            selected = false,
                            onClick = {
                                scope.launch { drawerState.close() }
                            }
                        )
                        NavigationDrawerItem(
                            icon = { Icon(Icons.Default.Menu, contentDescription = "Menu") },
                            label = { Text("About") },
                            selected = false,
                            onClick = {
                                scope.launch { drawerState.close() }
                            }
                        )
                    }
                },
                content = {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text("Modal Navigation Drawer") },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        scope.launch { drawerState.open() }
                                    }) {
                                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                                    }
                                }
                            )
                        }
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .background(Color.White)
                                .padding(16.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text("Main Content", fontSize = 24.sp, color = Color.Black)
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(onClick = {
                                    scope.launch { drawerState.open() }
                                }) {
                                    Text("Open Drawer")
                                }
                            }
                        }
                    }
                }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(200.dp)
                .background(Color.LightGray)
                .padding(16.dp)
        ) {
            Column {
                Text("Left Layout", fontSize = 24.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    scope.launch { drawerState.open() }
                }) {
                    Text("Open Drawer")
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewModalNavigationDrawerWithLeftLayout() {
    ModalNavigationDrawerWithLeftLayout()
}

