package com.liuchang.update

import android.app.Application
import com.blankj.utilcode.util.LogUtils
import com.xuexiang.xaop.XAOP
import com.xuexiang.xupdate.XUpdate
import com.xuexiang.xutil.app.AppUtils
import com.xuexiang.xutil.common.StringUtils
import com.xuexiang.xutil.tip.ToastUtils
import com.zhy.http.okhttp.OkHttpUtils
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object Updateinit {
    lateinit var context: Application
    lateinit var url: String

    fun setInstance(context: Application, url: String): Updateinit {
        Updateinit.context = context
        Updateinit.url = url
        return this
    }

    fun initXAOP() {
        XAOP.init(context) //初始化插件
        //设置动态申请权限切片 申请权限被拒绝的事件响应监听
        //设置动态申请权限切片 申请权限被拒绝的事件响应监听
        XAOP.setOnPermissionDeniedListener { permissionsDenied ->
            ToastUtils.toast(
                "权限申请被拒绝:" + StringUtils.listToString(
                    permissionsDenied,
                    ","
                )
            )
        }
    }

    fun initOKHttpUtils() {
        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(20000L, TimeUnit.MILLISECONDS)
            .readTimeout(20000L, TimeUnit.MILLISECONDS)
            .build()
        OkHttpUtils.initClient(okHttpClient)
    }

    fun initUpdate() {
        XUpdate.get()
            .debug(true)
            .isWifiOnly(false)
            .isGet(true).isAutoMode(false)
            .param("versionCode", AppUtils.getAppVersionCode())
            .param("appKey", AppUtils.getAppPackageName())
            .setOnUpdateFailureListener { LogUtils.a(it.detailMsg) }
            .supportSilentInstall(false)
            .setIUpdateHttpService(OKHttpUpdateHttpService())
            .init(context)
    }
}