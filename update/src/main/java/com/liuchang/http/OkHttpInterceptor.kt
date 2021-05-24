package com.liuchang.http

import android.annotation.SuppressLint
import android.util.Log
import com.xuexiang.xutil.system.DeviceUtils
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.*

class OkHttpInterceptor : Interceptor {
    private val TAG = "okHttp"
    private var cookie: String? = null

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = AddHeader(request)
        // 打印request日志
        logForRequest(request)
        val response = chain.proceed(request)
        // 打印response日志
        logForResponse(response)
        cookie = response.header("Set-Cookie")
        val cacheControl = request.cacheControl().toString()
        return response.newBuilder()
            .header("Cache-Control", cacheControl)
            .removeHeader("Pragma")
            .build()
    }

    @SuppressLint("MissingPermission")
    private fun AddHeader(request: Request): Request {
        val params = HashMap<String, String>()
        val info =
            "${DeviceUtils.getMacAddress()}_${DeviceUtils.getManufacturer()}_${DeviceUtils.getDeviceBrand()}"
        params["User-Agent"] = "Android $info"
        params["Host"] = ApiManager.base
        params["Connection"] = "keep-alive"
        params["Content-Type"] = "application/json;charset=UTF-8"
        params["X-Requested-With"] = "XMLHttpRequest"
        params["Accept-Language"] = "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6"
        if (cookie != "" && cookie != null) {
            params["CooKie"] = cookie!!
        }
        val header = Headers.of(params)
        return request.newBuilder().headers(header).build()
    }

    private fun logForResponse(response: Response) {
        Log.e(TAG, "response's log---------------------------------start")
        Log.e(TAG, "code: " + response.code())
        Log.e(TAG, "protocol: " + response.protocol())
        val headers = response.headers()
        if (headers != null && headers.size() != 0) {
            Log.e(TAG, headers.toString())
        }
        try {
            // 这里不能直接用response.body().string(),因为调用改方法后流就关闭，程序就可能会发生异常
            // 我们需要创建出一个新的ResponseBody给应用层调用
            val body = response.peekBody((1024 * 1024).toLong())
            if (body != null) {
                Log.e(TAG, "protocol: " + body.string())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        Log.e(TAG, "response's log---------------------------------end")
    }

    private fun logForRequest(request: Request) {
        Log.e(TAG, "request's log----------------------------------start")
        Log.e(TAG, "url: " + request.url())
        Log.e(TAG, "method: " + request.method())
        val headers = request.headers()
        if (headers != null && headers.size() != 0) {
            Log.e(TAG, "headers: $headers")
        }
        val body = request.body()
        if (body != null) {
            Log.e(TAG, body.toString())
        }
        Log.e(TAG, "request's log----------------------------------end")
    }
}
