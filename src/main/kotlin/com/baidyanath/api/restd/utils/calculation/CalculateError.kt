package com.baidyanath.api.restd.utils.calculation

object CalculateError : Calculate<Map<String, Map<String, List<String>>>, Int> {

    override fun run(request: Map<String, Map<String, List<String>>>): Int {
        var count = 0
        request.forEach {(_, errorsWithType) ->
            errorsWithType.forEach { (_, errors) ->
                count += errors.size
            }
        }
        return count
    }
}