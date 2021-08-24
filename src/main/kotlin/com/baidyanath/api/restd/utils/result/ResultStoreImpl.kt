package com.baidyanath.api.restd.utils.result

import com.baidyanath.api.restd.domain.BaseEntityRequest
import com.baidyanath.api.restd.domain.ErrorResponse
import com.baidyanath.api.restd.domain.ErrorType

object ResultStoreImpl : ResultStore<BaseEntityRequest> {

    override fun add(request: BaseEntityRequest, error: String, type: String) {
        var ruleTypesMap = request.result["path"]
        if (ruleTypesMap == null) {
            ruleTypesMap = mutableMapOf()
            request.result["path"] = ruleTypesMap
        }

        var errors = ruleTypesMap[type]
        val errorResponse = ErrorResponse(
            description = error,
            type = ErrorType.HIGH
        )

        if (errors == null) {
            errors = mutableListOf(errorResponse)
        } else {
            errors.add(errorResponse)
        }

        ruleTypesMap[type] = errors
        request.result["path"] = ruleTypesMap
    }
}