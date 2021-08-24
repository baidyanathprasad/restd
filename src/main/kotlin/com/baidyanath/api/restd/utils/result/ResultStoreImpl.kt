package com.baidyanath.api.restd.utils.result

import com.baidyanath.api.restd.domain.Request
import com.baidyanath.api.restd.domain.ErrorResponse
import com.baidyanath.api.restd.domain.ErrorType

object ResultStoreImpl : ResultStore<Request> {

    override fun add(request: Request, errors: Set<String>, type: String, value: String) {
        val ruleTypesMap = request.result[type] ?: mutableMapOf()

        val existingErrors = ruleTypesMap[value] ?: mutableListOf()
        val errorsResponse = errors.map { error ->
            ErrorResponse(
                description = error,
                type = ErrorType.HIGH
            )
        }
        existingErrors.addAll(errorsResponse)

        ruleTypesMap[value] = existingErrors
        request.result[type] = ruleTypesMap
    }
}