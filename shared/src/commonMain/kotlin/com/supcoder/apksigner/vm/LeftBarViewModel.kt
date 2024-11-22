package com.supcoder.apksigner.vm

import androidx.compose.runtime.mutableStateOf
import com.supcoder.apksigner.model.action.LeftBarAction
import com.supcoder.apksigner.model.state.LeftBarState

/**
 * 左侧工具栏的ViewModel
 */
class LeftBarViewModel {

    val leftBarState = mutableStateOf(LeftBarState())

    val leftBarAction = LeftBarAction(
        clickProject = {
            val projectOn = leftBarState.value.projectOn
            leftBarState.value = leftBarState.value.copy(
                projectOn = !projectOn
            )
        },
    )


    /**
     * 解析完文件后直接打开解析后的文件夹工程
     */
    fun openProject() {
        leftBarState.value = leftBarState.value.copy(
            projectOn = true
        )
    }
}