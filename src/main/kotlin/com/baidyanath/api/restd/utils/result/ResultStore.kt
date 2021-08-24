package com.baidyanath.api.restd.utils.result

interface ResultStore<T> {

    fun add(request: T, error: String, type: String)
}