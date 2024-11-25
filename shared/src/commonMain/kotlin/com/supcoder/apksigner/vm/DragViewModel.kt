package com.supcoder.apksigner.vm

import com.supcoder.apksigner.manager.NurseManager
import com.supcoder.apksigner.model.action.DragAction
import com.supcoder.apksigner.util.ioScope
import com.supcoder.apksigner.usecase.ApkUseCase
import com.supcoder.apksigner.usecase.DexUseCase
import com.supcoder.apksigner.usecase.HackUseCase
import kotlinx.coroutines.launch
import java.io.File

/**
 * 拖拽文件处理的相关ViewModel
 */
class DragViewModel {

    val dragAction = DragAction(
        onFileDrop = {
            onDragFile(it)
        }
    )

    /**
     * 拖拽的文件
     */
    private fun onDragFile(filePath: String) {
        val apk = File(filePath)

        ioScope.launch {
            try {
                val apkFilePath = apk.absolutePath

                val apkUseCase = ApkUseCase()
                val apkInfo = apkUseCase.getApkInfo(apkFilePath)
                NurseManager.updateApkNurseInfo(apkInfo)

                // 解压APK
                apkUseCase.decompressApk(
                    apkFilePath = apkFilePath,
                    outputDirPath = NurseManager.getApkNurseInfo().getDecompressDirPath()
                )

                // 反编译dex文件为java源文件
                val dexUseCase = DexUseCase()
                dexUseCase.dex2java(
                    dexPath = NurseManager.getApkNurseInfo().getDecompressDirPath(),
                    outDirPath = NurseManager.getApkNurseInfo().getDecompiledJavaDirPath()
                )

                // 解码APK
                val hackUseCase = HackUseCase()
                hackUseCase.decodeApk(
                    apkPath = apkFilePath,
                    decodeDirPath = NurseManager.getApkNurseInfo().getDecodeDirPath()
                )

                // 生成项目目录
                NurseManager.createProject(
                    File(NurseManager.getApkNurseInfo().getCurrentProjectDirPath())
                )
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }
}