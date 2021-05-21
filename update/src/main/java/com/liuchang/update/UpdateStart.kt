package com.liuchang.update

import android.content.Context
import com.xuexiang.xupdate.XUpdate

object UpdateStart {
    fun start(context: Context) {
        XUpdate.newBuild(context).updateUrl(Updateinit.url).update()
    }
}