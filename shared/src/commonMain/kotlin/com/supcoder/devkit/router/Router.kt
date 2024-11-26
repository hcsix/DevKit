package com.supcoder.devkit.router

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Android
import androidx.compose.material.icons.rounded.Factory
import androidx.compose.material.icons.rounded.FormatSize
import androidx.compose.material.icons.rounded.Pin
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.supcoder.devkit.ui.ApkInformation
import com.supcoder.devkit.ui.ApkSignature
import com.supcoder.devkit.ui.DecompileScreen
import com.supcoder.devkit.ui.JsonScreen
import com.supcoder.devkit.ui.SettingsScreen
import com.supcoder.devkit.ui.SignatureGeneration
import com.supcoder.devkit.ui.SignatureInformation
import com.supcoder.devkit.ui.component.codeviewer.MainView
import com.supcoder.devkit.vm.MainViewModel

//enum class Page(val title: StringResource, val icon: ImageVector) {
//    SIGNATURE_INFORMATION(Res.string.sign_info, Icons.Rounded.Description),
//    APK_INFORMATION(Res.string.apk_info, Icons.Rounded.Android),
//    APK_SIGNATURE(Res.string.apk_sign, Icons.Rounded.Pin),
//    SIGNATURE_GENERATION(Res.string.gen_sign, Icons.Rounded.Key),
//    SET_UP(Res.string.settings, Icons.Rounded.Settings)
//}
enum class Page(val title: String, val icon: ImageVector, val tag: Int) {
    APK_DECOMPILE("反编译", Icons.Rounded.Factory, 0),
    APK_INFORMATION("APK信息", Icons.Rounded.Android, 1),
    JSON_FORMAT("Json处理", Icons.Rounded.FormatSize, 2),
    APK_SIGNATURE("APK签名", Icons.Rounded.Pin, 3),
    SETTINGS("设置", Icons.Rounded.Settings, 4)
}

data class NavItem(val title: String, val page: Page, val navTag: String)


@Composable
fun getContent(viewModel: MainViewModel, page: Page, navTag: String?) {
    return when (page) {
        Page.JSON_FORMAT -> when (navTag) {
            "json_format" -> JsonScreen(viewModel,0)
            "json_to_model" -> JsonScreen(viewModel,1)
            else -> JsonScreen(viewModel,0)
        }

//        Page.APK_DECOMPILE -> DecompileScreen(viewModel)
        Page.APK_DECOMPILE -> MainView()
        Page.APK_INFORMATION -> ApkInformation(viewModel)
        Page.APK_SIGNATURE -> when (navTag) {
            "sign_apk" -> ApkSignature(viewModel)
            "gen_signature" -> SignatureGeneration(viewModel)
            "sign_info" -> SignatureInformation(viewModel)
            else -> ApkSignature(viewModel)
        }

        Page.SETTINGS -> SettingsScreen(viewModel)
        else -> SettingsScreen(viewModel)
    }
}

fun fetchNavList(tag: Int): List<NavItem> {
    return when (tag) {
        Page.JSON_FORMAT.tag -> return listOf(
            NavItem("Json格式化", Page.JSON_FORMAT, "json_format"),
            NavItem("模型生成", Page.JSON_FORMAT, "json_to_model")
        )

        Page.APK_SIGNATURE.tag -> return listOf(
            NavItem("签名APK", Page.APK_SIGNATURE, "sign_apk"),
            NavItem("生成签名", Page.APK_SIGNATURE, "gen_signature"),
            NavItem("签名信息", Page.APK_SIGNATURE, "sign_info")
        )
        else -> emptyList()
    }
}
