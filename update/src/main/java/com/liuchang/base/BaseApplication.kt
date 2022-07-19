package com.liuchang.base

import android.app.Application
import com.liuchang.http.ApiManager
import com.liuchang.update.Updateinit
import com.liys.doubleclicklibrary.helper.ViewDoubleHelper

abstract class BaseApplication : Application() {
    /**
     * 域名
     * @var base string
     */
    abstract var base: String

    /**
     * 基础请求地址 传入信息
     * @var baseUrl string
     */
    abstract var baseUrl: String

    /**
     * Agent 传入信息
     * @var info string
     *
     * "${DeviceUtils.getMacAddress()}_${SPStaticUtils.getInt(STAFF_ID,0)}_${DeviceUtils.getManufacturer()}_${DeviceUtils.getModel()}"
     */
    abstract var info: String
    override fun onCreate() {
        super.onCreate()
        Updateinit.url = "http://autoupdate.ali01.zrwsd.com/upload/checkVersion"
        Updateinit.context = this
        Updateinit.initXAOP()
        Updateinit.initUpdate()
        Updateinit.initOKHttpUtils()
        ViewDoubleHelper.init(this)
        ApiManager.setInstance(baseUrl,base,info)
        iniDate()
    }

    abstract fun iniDate()
}