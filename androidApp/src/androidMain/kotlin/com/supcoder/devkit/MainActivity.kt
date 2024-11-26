package com.supcoder.devkit

import android.app.Activity
import android.content.ContextWrapper
import android.content.res.AssetManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import com.supcoder.devkit.platform._HomeFolder
import java.io.File
import java.io.FileOutputStream
import kotlin.io.copyTo
import kotlin.io.use

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        FragmentActivity.onCreate(savedInstanceState)
//        copyAssets()
//        _HomeFolder = ContextWrapper.getFilesDir
//
//        WindowCompat.setDecorFitsSystemWindows(Activity.getWindow, false)
//
//        setContent {
//            MainView()
//        }
    }

//    private fun copyAssets() {
//        for (filename in AssetManager.list("data")!!) {
//            AssetManager.open("data/$filename").use { assetStream ->
//                val file = File(ContextWrapper.getFilesDir, filename)
//                FileOutputStream(file).use { fileStream ->
//                    assetStream.copyTo(fileStream)
//                }
//            }
//        }
//    }
}