package com.baidyanath.api.restd.domain

/**
 * Here key could be anything like endpoints, request body, response body, etc.
 */
data class Request(
    val result: MutableMap<String, MutableMap<String, MutableList<Any>>>,
    val keys: Set<String>,
    val version: Int = Int.MIN_VALUE
)