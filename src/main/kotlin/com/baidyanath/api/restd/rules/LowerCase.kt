package com.baidyanath.api.restd.rules

import com.baidyanath.api.restd.configs.Configs
import com.baidyanath.api.restd.configs.PATH_NAME_SHOULD_BE_IN_LOWER_CASE
import com.baidyanath.api.restd.domain.BaseEntityRequest
import com.baidyanath.api.restd.domain.ErrorResponse
import com.baidyanath.api.restd.domain.ErrorType

object LowerCase : Rule<BaseEntityRequest> {

    override fun check(request: BaseEntityRequest) {
        request.endPoints.forEach { endPoint ->
            val (isValid, _, error )= pathIsLowerCase(endPoint)

            if(!isValid) {
                var ruleTypesMap = request.result["path"]
                if (ruleTypesMap == null) {
                    ruleTypesMap = mutableMapOf()
                    request.result["path"] = ruleTypesMap
                }

                var errors = ruleTypesMap[endPoint]
                val errorResponse = ErrorResponse(
                    description = error,
                    type = ErrorType.HIGH
                )

                if (errors == null) {
                    errors = mutableListOf(errorResponse)
                } else {
                    errors.add(errorResponse)
                }

                ruleTypesMap[endPoint] = errors
                request.result["path"] = ruleTypesMap
            }
        }
    }

    private fun pathIsLowerCase(path: String) : Triple<Boolean, String, String> {
        var isValid = true

        if (path.isNotEmpty()) path.forEach { char ->
            isValid = char in 'a'..'z' || char in '0'..'9' || char in Configs.allowedCharsInPath
        } else isValid = false

        if (isValid) {
            return Triple(first = true, second = path, third = "")
        }
        return Triple(first = false, second = path, third = PATH_NAME_SHOULD_BE_IN_LOWER_CASE)
    }
}