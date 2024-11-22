package com.supcoder.apksigner.router

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Android
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Factory
import androidx.compose.material.icons.rounded.FormatSize
import androidx.compose.material.icons.rounded.Key
import androidx.compose.material.icons.rounded.Pin
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector

//enum class Page(val title: StringResource, val icon: ImageVector) {
//    SIGNATURE_INFORMATION(Res.string.sign_info, Icons.Rounded.Description),
//    APK_INFORMATION(Res.string.apk_info, Icons.Rounded.Android),
//    APK_SIGNATURE(Res.string.apk_sign, Icons.Rounded.Pin),
//    SIGNATURE_GENERATION(Res.string.gen_sign, Icons.Rounded.Key),
//    SET_UP(Res.string.settings, Icons.Rounded.Settings)
//}
enum class Page(val title: String, val icon: ImageVector) {
    SIGNATURE_INFORMATION("签名信息", Icons.Rounded.Description),
    APK_DECOMPILE("APK反编译", Icons.Rounded.Factory),
    JSON_FORMAT("Json处理", Icons.Rounded.FormatSize),
    APK_INFORMATION("APK信息", Icons.Rounded.Android),
    APK_SIGNATURE("APK签名", Icons.Rounded.Pin),
    SIGNATURE_GENERATION("生成签名", Icons.Rounded.Key),
    SETTINGS("设置", Icons.Rounded.Settings)
}