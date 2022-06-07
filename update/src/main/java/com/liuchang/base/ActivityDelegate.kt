package com.liuchang.base

import android.app.Activity
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.viewbinding.ViewBinding
import com.liuchang.ext.observerWhenDestroyed
import kotlin.properties.ReadOnlyProperty

/**
 * <pre>
 *     author: dhl
 *     date  : 2020/12/15
 *     desc  :
 * </pre>
 */

abstract class ActivityDelegate<T : ViewBinding>(
    activity: Activity
) : ReadOnlyProperty<Activity, T> {

    protected var viewBinding: T? = null

    init {
        when (activity) {
            is ComponentActivity -> activity.lifecycle.observerWhenDestroyed { destroyed() }
            else -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    activity.observerWhenDestroyed { destroyed() }
                }
            }
        }

    }


    private fun destroyed() {
        viewBinding = null
    }
}