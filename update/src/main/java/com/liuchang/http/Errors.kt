package com.liuchang.http

import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import retrofit2.HttpException

class Errors {
    fun Create(throwable: Throwable) {
        if (throwable is HttpException) {
            val en = throwable.response().errorBody()?.string()
            if (!en.equals("Not Found")) {
                val entity = GsonUtils.fromJson(en, BaseEntity::class.java)
                LogUtils.a(entity.msg)
                ToastUtils.showShort(entity.msg)
            }else {
                ToastUtils.showShort(throwable.message)
                LogUtils.e(throwable.message)
            }
        } else {
            ToastUtils.showShort(throwable.message)
            LogUtils.e(throwable.message)
        }
    }
}
