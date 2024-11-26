package com.supcoder.devkit.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.supcoder.devkit.i18n.I18n
import com.supcoder.devkit.util.FileUtils.selectFile

   @Composable
   fun HomeScreen(onScreenSelected: (Router) -> Unit) {
       var keystoreFilePath by remember { mutableStateOf("") }
       var apkFilePath by remember { mutableStateOf("") }
       var keystorePassword by remember { mutableStateOf("") }
       var keyAlias by remember { mutableStateOf("") }
       var keyPassword by remember { mutableStateOf("") }

       // 定义 SnackbarHostState 和 showSnackbar 状态变量
       var snackbarHostState by remember { mutableStateOf(SnackbarHostState()) }
       var showSnackbar by remember { mutableStateOf(false) }

       Scaffold(
           topBar = {
               TopAppBar(
                   title = { Text(I18n.getStringRes("app_name")) },
               )
           },
           floatingActionButton = {
               FloatingActionButton(
                   onClick = { onScreenSelected.invoke(Router.LanguageScreen) },
                   backgroundColor = Color.Blue,
                   interactionSource = remember { MutableInteractionSource() },
               ) {
                   Image(
                       painter = painterResource("icon_language.png"),
                       contentDescription = "Loaded Resource Image",
                       modifier = Modifier.width(48.dp).height(48.dp).padding(12.dp)
                   )
               }
           },
           snackbarHost = { snackbarHostState } // 添加 SnackbarHost
       ) { innerPadding ->
           Column(
               modifier = Modifier
                   .padding(innerPadding)
                   .padding(16.dp)
                   .fillMaxWidth(),
               horizontalAlignment = Alignment.CenterHorizontally,
               verticalArrangement = Arrangement.spacedBy(8.dp)
           ) {
               OutlinedTextField(
                   value = apkFilePath,
                   onValueChange = { apkFilePath = it },
                   label = { Text(I18n.getStringRes("apk_file_path")) },
                   leadingIcon = { Icon(Icons.Filled.Place, contentDescription = null) },
                   trailingIcon = {
                       IconButton(onClick = {
                           selectFile(I18n.getStringRes("tip_apk_file_path"),
                               checkFile = { it.endsWith(".apk") },
                               setPath = { apkFilePath = it })
                        }, interactionSource = remember { MutableInteractionSource() }

                       ) {
                           Icon(Icons.Filled.Add, contentDescription = "选择文件")
                       }
                   },
                   interactionSource = remember { MutableInteractionSource() } // 明确指定类型
               )
               OutlinedTextField(
                   value = keystoreFilePath,
                   onValueChange = { keystoreFilePath = it },
                   label = { Text(I18n.getStringRes("key_file_path")) },
                   leadingIcon = { Icon(Icons.Filled.Place, contentDescription = null) },
                   trailingIcon = {
                       IconButton(onClick = {
                           selectFile(I18n.getStringRes("tip_key_file_path"),
                               checkFile = { true },
                               setPath = {
                                   keystoreFilePath = it
                               })
                       }, interactionSource = remember { MutableInteractionSource() }) {
                           Icon(Icons.Filled.Add, contentDescription = "选择文件")
                       }
                   },
                   interactionSource = remember { MutableInteractionSource() } // 明确指定类型
               )
               OutlinedTextField(
                   value = keystorePassword,
                   onValueChange = { keystorePassword = it },
                   label = { Text(I18n.getStringRes("key_store_pass")) },
                   leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                   visualTransformation = PasswordVisualTransformation(),
                   interactionSource = remember { MutableInteractionSource() } // 明确指定类型
               )
               OutlinedTextField(
                   value = keyAlias,
                   onValueChange = { keyAlias = it },
                   label = { Text(I18n.getStringRes("alias")) },
                   leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
                   interactionSource = remember { MutableInteractionSource() } // 明确指定类型
               )
               OutlinedTextField(
                   value = keyPassword,
                   onValueChange = { keyPassword = it },
                   label = { Text(I18n.getStringRes("key_pass")) },
                   leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                   visualTransformation = PasswordVisualTransformation(),
                   interactionSource = remember { MutableInteractionSource() } // 明确指定类型
               )
               Spacer(modifier = Modifier.height(16.dp))
               Button(onClick = { showSnackbar = true }, interactionSource = remember { MutableInteractionSource() }) {
                   Text(I18n.getStringRes("sign"))
               }
           }

           // 使用 LaunchedEffect 显示 Snackbar
           if (showSnackbar) {
               LaunchedEffect(snackbarHostState) {
                   snackbarHostState.showSnackbar("APK 签名成功！")
                   showSnackbar = false
               }
           }
       }
   }
