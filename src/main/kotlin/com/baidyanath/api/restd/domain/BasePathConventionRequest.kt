package com.baidyanath.api.restd.domain

data class BasePathConventionRequest(
    val result: MutableMap<String, MutableMap<String, MutableList<String>>>,
    val endPoints: Set<String>,
    val version: Int
)