package com.liuchang.databind

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.liuchang.base.ActivityDelegate
import kotlin.reflect.KProperty

/**
 * <pre>
 *     author: dhl
 *     date  : 2020/12/10
 *     desc  :
 * </pre>
 */

class ActivityDataBinding<T : ViewDataBinding>(
    val activity: Activity,
    @LayoutRes val resId: Int,
    private var block: (T.() -> Unit)? = null
) : ActivityDelegate<T>(activity) {

    override fun getValue(thisRef: Activity, property: KProperty<*>): T {
        return viewBinding?.run {
            this

        } ?: let {

            // 获取 ViewDataBinding
            val bind: T = DataBindingUtil.setContentView(thisRef, resId)
            return bind.apply {
                if (activity is ComponentActivity) {
                    bind.lifecycleOwner = activity
                }
                viewBinding = this
                block?.invoke(this)
                block = null
            }
        }
    }

}