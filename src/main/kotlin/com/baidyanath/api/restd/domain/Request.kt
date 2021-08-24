package com.baidyanath.api.restd.domain

data class Request(
    val result: MutableMap<String, MutableMap<String, MutableList<Any>>>,
    val endPoints: Set<String>,
    val version: Int
)