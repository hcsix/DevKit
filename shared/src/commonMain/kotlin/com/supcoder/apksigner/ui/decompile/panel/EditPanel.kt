package com.supcoder.apksigner.ui.decompile.panel

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.supcoder.apksigner.theme.codeTextColor
import com.supcoder.apksigner.manager.transfer.JavaKeywordVisualTransformation
import com.supcoder.apksigner.ui.decompile.scroll.ScrollPanel
import com.supcoder.apksigner.vm.MainViewModel


/**
 * 代码编辑的面板
 * 预览java、smali代码，支持java关键字高亮显示
 */
@Composable
fun EditPanel(
    isDark: Boolean,
    modifier: Modifier,
    textContent: String,
    textType: String
) {

    val verticalScrollState = rememberScrollState()
    val horizontalScrollState = rememberScrollState()

    ScrollPanel(
        modifier = modifier,
        verticalScrollStateAdapter = rememberScrollbarAdapter(verticalScrollState),
        horizontalScrollStateAdapter = rememberScrollbarAdapter(horizontalScrollState)
    ) {

        TextField(
            value = textContent,
            colors = TextFieldDefaults.textFieldColors(
                textColor = codeTextColor(isDark = isDark),
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.White,
                errorCursorColor = Color.Red,
                backgroundColor = Color.Transparent,

                ),
            textStyle = TextStyle(
                color = codeTextColor(isDark),
                fontSize = 16.sp,
                lineHeight = 24.sp,
                fontFamily = FontFamily.Monospace
            ),
            modifier = Modifier.fillMaxSize()
                .verticalScroll(verticalScrollState)
                .horizontalScroll(horizontalScrollState),
            onValueChange = {},
            visualTransformation = if (textType == "java") {
                JavaKeywordVisualTransformation
            } else {
                VisualTransformation.None
            }
        )

    }
}