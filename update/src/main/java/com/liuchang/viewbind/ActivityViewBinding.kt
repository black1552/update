package com.liuchang.viewbind

import android.app.Activity
import androidx.viewbinding.ViewBinding
import com.liuchang.base.ActivityDelegate
import com.liuchang.ext.inflateMethod
import kotlin.reflect.KProperty

/**
 * <pre>
 *     author: dhl
 *     date  : 2020/12/10
 *     desc  :
 * </pre>
 */

class ActivityViewBinding<T : ViewBinding>(
    classes: Class<T>,
    val activity: Activity
) : ActivityDelegate<T>(activity) {

    private var layoutInflater = classes.inflateMethod()

    override fun getValue(thisRef: Activity, property: KProperty<*>): T {
        return viewBinding?.run {
            this

        } ?: let {
            // 获取 ViewBinding 实例
            val bind = layoutInflater.invoke(null, thisRef.layoutInflater) as T
            thisRef.setContentView(bind.root)
            return bind.apply { viewBinding = this }
        }
    }

}