package com.baidyanath.api.restd.domain

data class BaseEntityRequest(
    val result: MutableMap<String, MutableMap<String, MutableList<String>>>,
    val endPoints: Set<String>,
    val version: Int
)