package com.supcoder.devkit.platform

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun VerticalScrollbar(
    modifier: Modifier,
    scrollState: ScrollState
) = VerticalScrollbar(
    rememberScrollbarAdapter(scrollState),
    modifier
)

@Composable
actual fun VerticalScrollbar(
    modifier: Modifier,
    scrollState: LazyListState
) = VerticalScrollbar(
    rememberScrollbarAdapter(scrollState),
    modifier
)