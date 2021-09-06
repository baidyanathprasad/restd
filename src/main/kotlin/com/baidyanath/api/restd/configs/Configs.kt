package com.baidyanath.api.restd.configs

object Configs {

    // Characters allowed in the path
    val allowedCharsInPath = setOf('-', '?', '/', '&', '=', '_', '{', '}')

    // Characters allowed in the snakeCase
    val allowedCharsInSnakeCase = setOf('_', '$')
}