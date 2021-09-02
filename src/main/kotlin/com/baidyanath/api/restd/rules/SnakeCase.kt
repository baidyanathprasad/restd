package com.baidyanath.api.restd.rules

import com.baidyanath.api.restd.configs.Configs
import com.baidyanath.api.restd.configs.KEY_SHOULD_BE_IN_SNAKE_CASE
import com.baidyanath.api.restd.domain.Request
import com.baidyanath.api.restd.utils.result.ResultStoreImpl

object SnakeCase : Rule<Request>{

    override fun check(request: Request) {
        request.keys.forEach { key ->
            val (isValid, _, error) = isValidSnakeCase(key)

            if (!isValid) {
                ResultStoreImpl.add(
                    request = request,
                    errors = setOf(error),
                    type = "identifierNaming",
                    value = key
                )
            }
        }
    }

    private fun isValidSnakeCase(input: String) : Triple<Boolean, String, String> {
        var isValid = true

        if (input.isNotEmpty()) input.forEach {  char ->
            isValid = isValid && char in 'a'..'z' || char in Configs.allowedCharsInSnakeCase
        } else isValid = true

        if (isValid) {
            return Triple(first = true, second = input, third = "")
        }
        return Triple(first = false, second = input, third = KEY_SHOULD_BE_IN_SNAKE_CASE)
    }
}