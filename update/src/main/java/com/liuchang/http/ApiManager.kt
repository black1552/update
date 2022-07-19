package com.liuchang.http

import android.os.Build
import androidx.annotation.RequiresApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration
import java.util.concurrent.TimeUnit

object ApiManager {

    private var base: String = ""
    private var baseUrl: String = ""
    var info: String? = null


    @Volatile
    private var mOkHttpClient: OkHttpClient? = null
    private val okHttpClient: OkHttpClient?
        @RequiresApi(Build.VERSION_CODES.O) private get() {
            if (mOkHttpClient == null) {
                synchronized(ApiManager::class.java) {
                    if (mOkHttpClient == null) {
                        mOkHttpClient = OkHttpClient.Builder()
                            .addInterceptor(OkHttpInterceptor())
                            .retryOnConnectionFailure(true)
                            .readTimeout(8, TimeUnit.SECONDS)
                            .writeTimeout(8, TimeUnit.SECONDS)
                            .connectTimeout(5, TimeUnit.SECONDS)
                            .build()
                    }
                }
            }
            return mOkHttpClient
        }

    fun <T> create(clazz: Class<T>?): T {
        val retrofit: Retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(clazz)
    }

    fun setInstance(baseUrl: String, base: String, info: String?): ApiManager {
        this.baseUrl = baseUrl
        this.base = base
        if (info != null) {
            this.info = info
        }
        return this
    }
}
