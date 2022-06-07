package com.liuchang.viewbind

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.viewbinding.ViewBinding
import com.liuchang.ext.inflateMethod
import com.liuchang.ext.inflateMethodWithViewGroup
import com.liuchang.ext.observerWhenDestroyed
import java.lang.reflect.Method
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * <pre>
 *     author: dhl
 *     date  : 2020/12/17
 *     desc  :
 * </pre>
 */

class ViewGroupViewBinding<T : ViewBinding>(
    classes: Class<T>,
    val inflater: LayoutInflater,
    val viewGroup: ViewGroup? = null
) : ReadOnlyProperty<ViewGroup, T> {

    private var viewBinding: T? = null
    private var layoutInflater: Method

    init {
        if (viewGroup != null) {
            layoutInflater = classes.inflateMethodWithViewGroup()
        } else {
            layoutInflater = classes.inflateMethod()
        }

        viewGroup?.apply {
            when (context) {
                is ComponentActivity -> {
                    (context as ComponentActivity).lifecycle.observerWhenDestroyed { destroyed() }
                }
                is Activity -> {
                    val activity = context as Activity
                    activity.observerWhenDestroyed { destroyed() }
                }
            }
        }
    }

    override fun getValue(thisRef: ViewGroup, property: KProperty<*>): T {
        return viewBinding?.run {
            this

        } ?: let {

            val bind: T
            if (viewGroup != null) {
                bind = layoutInflater.invoke(null, inflater, viewGroup) as T
            } else {
                bind = layoutInflater.invoke(null, inflater) as T
            }

            bind.apply {
                if (viewGroup == null) {
                    thisRef.addView(bind.root)
                }
                viewBinding = this
            }
        }
    }

    private fun destroyed() {
        viewBinding = null
    }
}