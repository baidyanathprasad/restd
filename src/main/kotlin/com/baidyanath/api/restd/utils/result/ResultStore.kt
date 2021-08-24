package com.baidyanath.api.restd.utils.result

interface ResultStore<T> {

    fun add(request: T, errors: Set<String>, type: String, value: String)
}