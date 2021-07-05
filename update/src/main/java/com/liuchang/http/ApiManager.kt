package com.liuchang.http

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiManager {

    private var base: String = ""
    private var baseUrl: String = ""

    @Volatile
    private var mOkHttpClient: OkHttpClient? = null
    private val okHttpClient: OkHttpClient?
        private get() {
            if (mOkHttpClient == null) {
                synchronized(ApiManager::class.java) {
                    if (mOkHttpClient == null) {
                        mOkHttpClient = OkHttpClient.Builder()
                            .addInterceptor(OkHttpInterceptor())
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

    fun setInstance(baseUrl: String, base: String): ApiManager {
        this.baseUrl = baseUrl
        this.base = base
        return this
    }
}
