package com.liuchang.update

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blankj.utilcode.util.BarUtils
import com.liuchang.http.ApiManager

/**
 * 添加所有其他大神编写所需要用到的引用库
 * 包含：
 *      通用适配器
 *      基础网络通信组件
 *      图片加载组件
 *      各种工具类
 *      应用更新
 * 使用方式代码中使用
 **/

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BarUtils.setStatusBarLightMode(this,true)
        setContentView(R.layout.activity_main)

        // 更新
        Updateinit.context = this.application
        //  更新效验地址
        Updateinit.url = ""
        Updateinit.initXAOP()
        Updateinit.initOKHttpUtils()
        Updateinit.initUpdate()

        // 网络请求基地址
        ApiManager.base = ""
        // 网络请求基础及地址
        ApiManager.baseUrl = ""
        // 其他如以往添加实体及请求方法
    }
}