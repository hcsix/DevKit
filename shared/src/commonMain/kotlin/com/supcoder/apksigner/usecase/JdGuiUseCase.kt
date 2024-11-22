package com.supcoder.apksigner.usecase

import com.supcoder.apksigner.util.localJdGuiJarPath
import com.supcoder.apksigner.util.logger
import com.supcoder.apksigner.util.runCMD

/**
 * 使用JD-GUI的用例
 */
class JdGuiUseCase {

    /**
     * 会直接启动jd-gui的UI工具
     */
    fun viewJar(jarFilePath: String) {
        runCMD(
            "java",
            "-jar",
            localJdGuiJarPath(),
            jarFilePath,
            onLine = {
                logger("jd-gui : $it")
            }
        )
    }
}