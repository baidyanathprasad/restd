package com.baidyanath.api.restd.utils.calculation

interface Calculate<T, R> {
    fun run(request: T): R
}