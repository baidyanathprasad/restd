package com.baidyanath.api.restd.rules

interface Rule<T> {
    fun check(request: T)
}