package com.supcoder.apksigner.manager

import com.supcoder.apksigner.model.ApkBasicInfo
import com.supcoder.apksigner.model.ApkNurseInfo
import com.supcoder.apksigner.model.FileItemInfo
import com.supcoder.apksigner.vm.DragViewModel
import com.supcoder.apksigner.vm.EditorViewModel
import com.supcoder.apksigner.vm.LeftBarViewModel
import com.supcoder.apksigner.vm.ProjectPanelViewModel
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