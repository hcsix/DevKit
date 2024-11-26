package com.supcoder.devkit.manager

import com.supcoder.devkit.model.ApkBasicInfo
import com.supcoder.devkit.model.ApkNurseInfo
import com.supcoder.devkit.model.FileItemInfo
import com.supcoder.devkit.vm.DragViewModel
import com.supcoder.devkit.vm.EditorViewModel
import com.supcoder.devkit.vm.LeftBarViewModel
import com.supcoder.devkit.vm.ProjectPanelViewModel
import java.io.File

/**
 * 工程的单例处理类
 */
object NurseManager {

    val leftBarViewModel = LeftBarViewModel()
    val dragViewModel = DragViewModel()
    val projectPanelViewModel = ProjectPanelViewModel()
    val editorViewModel = EditorViewModel()

    private var apkNurseInfo = ApkNurseInfo()

    fun updateApkNurseInfo(apkBasicInfo: ApkBasicInfo) {
        apkNurseInfo = ApkNurseInfo(apkBasicInfo)
    }

    fun getApkNurseInfo(): ApkNurseInfo {
        return apkNurseInfo
    }

    /**
     * 创建工程
     */
    fun createProject(file: File) {
        projectPanelViewModel.createProject(file)
        leftBarViewModel.openProject()
    }

    /**
     * 查看文件
     */
    fun viewFile(fileItemInfo: FileItemInfo) {
        editorViewModel.viewFile(fileItemInfo)
    }

}