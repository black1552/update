package com.liuchang.http

data class BaseEntity<T>(
    val code: Int,
    val data: T,
    val msg: String
)