package com.baidyanath.api.restd.domain

data class ErrorResponse (
    val description: String,
    val type: ErrorType
)