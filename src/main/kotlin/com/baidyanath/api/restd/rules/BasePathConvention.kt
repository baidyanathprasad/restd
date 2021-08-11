package com.baidyanath.api.restd.rules

import com.baidyanath.api.restd.configs.PATH_SHOULD_START_WITH_API_AND_VERSION
import com.baidyanath.api.restd.domain.BasePathConventionRequest
import com.baidyanath.api.restd.domain.ErrorResponse
import com.baidyanath.api.restd.domain.ErrorType

object BasePathConvention: Rule<BasePathConventionRequest> {

    override fun check(request: BasePathConventionRequest) {
        request.endPoints.forEach {
            val (isValid, pathName, error) = checkBasePathConvention(it, request.version)

            if (!isValid) {
                var ruleTypesMap = request.result["path"]
                if (ruleTypesMap == null) {
                    ruleTypesMap = mutableMapOf()
                    request.result["path"] = ruleTypesMap
                }

                var errors = ruleTypesMap[pathName]
                val errorResponse = ErrorResponse(
                    description = error,
                    type = ErrorType.HIGH
                )

                if (errors == null) {
                    errors = mutableListOf(errorResponse)
                } else {
                   errors.add(errorResponse)
                }

                ruleTypesMap[pathName] = errors
                request.result["path"] = ruleTypesMap
            }
        }
    }

    // Rules check that starts with /api/v1/..
    private fun checkBasePathConvention(path: String, version: Int): Triple<Boolean, String, String> {
        val isValid = path.startsWith("/api/v$version/")

        if (isValid) {
            return Triple(true,  path, "")
        }
        return Triple(false, path, "$PATH_SHOULD_START_WITH_API_AND_VERSION$version/")
    }
}