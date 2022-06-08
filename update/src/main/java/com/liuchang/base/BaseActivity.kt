package com.liuchang.base

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.AdaptScreenUtils
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.FileUtils

abstract class BaseActivity: FragmentActivity() {

    private val path = "/storage/emulated/0/Download/${AppUtils.getAppPackageName()}"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    abstract fun initView()

    fun saveFile(content: String, pathName: String?) {
        var thisPath = ""
        pathName?.let {
            thisPath = "${path}/${it}"
        }
        FileUtils.createFileByDeleteOldFile(thisPath)
        FileIOUtils.writeFileFromString(thisPath, content)
    }

    fun getFileContext(pathName: String): List<String>? {
        val thisPath = "${path}/${pathName}"
        return if (FileUtils.isFileExists(thisPath)) {
            val context = FileIOUtils.readFile2List(thisPath)
            if (!context.isNullOrEmpty()) {
                context
            } else {
                null
            }
        } else {
            null
        }
    }

    override fun getResources(): Resources {
        return AdaptScreenUtils.adaptWidth(super.getResources(), 1080)
    }
}
