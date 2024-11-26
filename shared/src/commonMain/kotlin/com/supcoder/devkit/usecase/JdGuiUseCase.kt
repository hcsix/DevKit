package com.supcoder.devkit.usecase

import com.supcoder.devkit.util.localJdGuiJarPath
import com.supcoder.devkit.util.logger
import com.supcoder.devkit.util.runCMD

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