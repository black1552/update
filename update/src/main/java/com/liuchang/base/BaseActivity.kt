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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    abstract fun initView()

    fun saveFile(content: String, pathName: String) {
        FileUtils.createFileByDeleteOldFile(pathName)
        FileIOUtils.writeFileFromString(pathName, content)
    }

    fun getFileContext(pathName: String): List<String>? {
        val thisPath = "${path}/${pathName}"
        return if (FileUtils.isFileExists(pathName)) {
            val context = FileIOUtils.readFile2List(pathName)
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
