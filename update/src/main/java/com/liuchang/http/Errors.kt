package com.liuchang.http

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import retrofit2.HttpException

object Errors {
    fun Create(throwable: Throwable) {
        if (throwable is HttpException) {
            val en = throwable.response().errorBody()?.string()
            if (!en.equals("Not Found")) {
                val entity = GsonUtils.fromJson(en, BaseEntity::class.java)
                LogUtils.a(entity.msg)
                ToastUtils.showShort(entity.msg)
            } else {
                ToastUtils.showShort(throwable.message)
                LogUtils.e(throwable.message)
            }
        } else {
            ToastUtils.showShort(throwable.message)
            LogUtils.e(throwable.message)
        }
    }

    fun CreateDialog(throwable: Throwable, context: Context, clz: Class<out Activity>) {
        if (throwable is HttpException) {
            val en = throwable.response().errorBody()?.string()
            if (!en.equals("Not Found")) {
                val entity = GsonUtils.fromJson(en, BaseEntity::class.java)
                AlertDialog.Builder(context).setTitle("提示")
                    .setMessage(entity.msg).setNegativeButton("确定") { _, _ ->
                        ActivityUtils.getTopActivity()
                        ActivityUtils.finishActivity(clz)
                    }.show()
                LogUtils.a(entity.msg)
                ToastUtils.showShort(entity.msg)
            } else {
                ToastUtils.showShort(throwable.message)
                LogUtils.e(throwable.message)
            }
        } else {
            ToastUtils.showShort(throwable.message)
            LogUtils.e(throwable.message)
        }
    }
}
