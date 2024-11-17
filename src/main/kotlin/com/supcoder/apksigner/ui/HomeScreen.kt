package com.supcoder.apksigner.ui

import androidx.compose.foundation.Image
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
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.supcoder.apksigner.i18n.I18n
import com.supcoder.apksigner.util.FileUtils.selectFile
import java.nio.file.Paths

//import coil.compose.rememberImagePainter

@Composable
fun HomeScreen(onScreenSelected: (Router) -> Unit) {
    var keystoreFilePath by remember { mutableStateOf("") }
    var apkFilePath by remember { mutableStateOf("") }
    var keystorePassword by remember { mutableStateOf("") }
    var keyAlias by remember { mutableStateOf("") }
    var keyPassword by remember { mutableStateOf("") }

    val resourcePath = "src/main/resources/icon_lanuage.png"
    val imagePath = Paths.get(resourcePath).toUri().toString()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(I18n.getStringRes("app_name")) },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onScreenSelected.invoke(Router.LanguageScreen) }) {
                Image(
                    painter = rememberAsyncImagePainter(imagePath),
                    contentDescription = "Loaded Resource Image",
                    modifier = Modifier.width(48.dp).height(48.dp)
                )
//                Icon(Icons.Filled.Settings, contentDescription = "Add")
            }
        }
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
                leadingIcon = { Icon(Icons.Filled.Info, contentDescription = null) },
                trailingIcon = {
                    IconButton(onClick = {
                        selectFile(I18n.getStringRes("tip_apk_file_path"), setPath = {
                            apkFilePath = it
                        })
                    }) {
                        Icon(Icons.Filled.AddCircle, contentDescription = "选择文件")
                    }
                }
            )
            OutlinedTextField(
                value = keystoreFilePath,
                onValueChange = { keystoreFilePath = it },
                label = { Text(I18n.getStringRes("key_file_path")) },
                leadingIcon = { Icon(Icons.Filled.Info, contentDescription = null) },
                trailingIcon = {
                    IconButton(onClick = {
                        selectFile(I18n.getStringRes("tip_key_file_path"), setPath = {
                            keystoreFilePath = it
                        })
                    }) {
                        Icon(Icons.Filled.AddCircle, contentDescription = "选择文件")
                    }
                }
            )
            OutlinedTextField(
                value = keystorePassword,
                onValueChange = { keystorePassword = it },
                label = { Text(I18n.getStringRes("key_store_pass")) },
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                visualTransformation = PasswordVisualTransformation()
            )
            OutlinedTextField(
                value = keyAlias,
                onValueChange = { keyAlias = it },
                label = { Text(I18n.getStringRes("alias")) },
                leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) }
            )
            OutlinedTextField(
                value = keyPassword,
                onValueChange = { keyPassword = it },
                label = { Text(I18n.getStringRes("key_pass")) },
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { /* TODO: 签名 APK */ }) {
                Text(I18n.getStringRes("sign"))
            }
        }
    }


}





