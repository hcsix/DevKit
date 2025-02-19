package com.supcoder.devkit.util

import com.supcoder.devkit.model.FileItemInfo
import java.io.File

/**
 * 所用lib文件的名称
 */
// libs文件夹下的直接文件
const val LIB_NAME_APK_TOOL = "apktool_2.7.0.jar"
const val LIB_NAME_JD_GUI = "jd-gui-1.6.6-min.jar"
const val LIB_NAME_PROCYON = "procyon-decompiler-0.6.0.jar"
const val LIB_NAME_KEY_STORE = "vsloong.jks"    // debug签名文件
const val LIB_NAME_APK_SIGNER = "apksigner.jar" // 签名工具的jar包

// 注意：以下内容均为未适配Linux
private val DIR_WIN = "win" + File.separator
private val DIR_MAC = "mac" + File.separator
private val DIR_MAC_ARM = "mac_arm" + File.separator

val LIB_NAME_AAPT2 =
    if (isWindows()) {
        DIR_WIN + "aapt2.exe"
    } else if (isMac()) {
        if (isArm()) {
            DIR_MAC_ARM + "aapt2"
        } else {
            DIR_MAC + "aapt2"
        }
    } else {
        throw Throwable("未支持的系统类型")
    }

val LIB_NAME_ZIP_ALIGN =
    if (isWindows()) {
        DIR_WIN + "zipalign.exe"
    } else if (isMac()) {
        if (isArm()) {
            DIR_MAC_ARM + "zipalign"
        } else {
            DIR_MAC + "zipalign"
        }
    } else {
        throw Throwable("未支持的系统类型")
    }


/**
 * 如果文件不存在则创建
 */
fun createFileIfNoExists(filePath: String) {
    val file = File(filePath)
    val parentDir = File(file.parent)
    if (!parentDir.exists()) {
        parentDir.mkdirs()
    }
    if (!file.exists()) {
        file.createNewFile()
    }
}

/**
 * 如果文件目录不存在则创建
 */
fun createDirIfNoExists(dirPath: String) {
    val dir = File(dirPath)
    if (!dir.exists()) {
        dir.mkdirs()
    }
}

/**
 * 递归删除文件
 */
fun deleteFileRecursively(file: File) {
    if (!file.exists()) {
        return
    }
    if (file.isFile) {
        val deleteResult = file.delete()
        if (!deleteResult) {
            logger("递归删除文件失败${file.absolutePath}")
        }
    } else if (file.isDirectory) {
        val files = file.listFiles() ?: emptyArray()
        for (file1 in files) {
            deleteFileRecursively(file1)
        }
        val deleteResult = file.delete()
        if (!deleteResult) {
            logger("递归删除文件失败${file.absolutePath}")
        }
    }
}

/**
 * 根据文件后缀显示不同的图片资源文件
 */
fun getResByFileItem(item: FileItemInfo): String {
    return if (item.isDir) {
        "icons/file_type_folder.svg"
    } else {
        if (item.name.endsWith(".java")) {
            "icons/file_type_java.svg"
        } else if (item.name.endsWith(".png")
            || item.name.endsWith(".webp")
            || item.name.endsWith(".jpg")
        ) {
            "icons/file_type_image.svg"
        } else if (item.name.endsWith(".xml")) {
            "icons/file_type_xml.svg"
        } else if (item.name.endsWith(".smali")) {
            "icons/file_type_smali.svg"
        } else {
            "icons/file_type_unknown.svg"
        }
    }
}