package com.supcoder.apksigner.ui
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.supcoder.apksigner.i18n.I18n
import com.supcoder.apksigner.i18n.LanguageManager
import kotlinx.coroutines.launch

@Composable
@Preview
fun LanguageScreen(onScreenSelected: (Router) -> Unit) {
    val languageManager = remember { LanguageManager() }
    val language by languageManager.language.collectAsState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(I18n.getStringRes("language_setting")) },
                navigationIcon = {
                    IconButton(onClick = { onScreenSelected(Router.HomeScreen) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "${I18n.getStringRes("current_language")} $language")

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                scope.launch {
                    languageManager.changeLanguage("en")
                }
            }) {
                Text(I18n.getStringRes("set_en"))
            }

            Button(onClick = {
                scope.launch {
                    languageManager.changeLanguage("zh")
                }
            }) {
                Text(I18n.getStringRes("set_zh"))
            }
        }
    }
}
