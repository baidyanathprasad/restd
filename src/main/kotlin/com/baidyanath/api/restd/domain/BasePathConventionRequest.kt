package com.baidyanath.api.restd.domain

data class BasePathConventionRequest(
    val result: MutableMap<String, MutableMap<String, MutableList<ErrorResponse>>>,
    val endPoints: Set<String>,
    val version: Int
) {
    fun asResult(): Map<String, Map<String, List<ErrorResponse>>> {
        return result
    }
}