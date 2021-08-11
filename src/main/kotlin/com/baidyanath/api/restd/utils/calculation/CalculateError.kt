package com.baidyanath.api.restd.utils.calculation

import com.baidyanath.api.restd.domain.ErrorResponse

object CalculateError : Calculate<Map<String, Map<String, List<ErrorResponse>>>, Int> {

    override fun run(request: Map<String, Map<String, List<ErrorResponse>>>): Int {
        var count = 0
        request.forEach {(_, errorsWithType) ->
            errorsWithType.forEach { (_, errors) ->
                count += errors.size
            }
        }
        return count
    }
}