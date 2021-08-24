package com.baidyanath.api.restd.rules

import com.baidyanath.api.restd.configs.Configs
import com.baidyanath.api.restd.configs.PATH_NAME_SHOULD_BE_IN_LOWER_CASE
import com.baidyanath.api.restd.domain.BaseEntityRequest
import com.baidyanath.api.restd.utils.result.ResultStoreImpl

object LowerCase : Rule<BaseEntityRequest> {

    override fun check(request: BaseEntityRequest) {
        request.endPoints.forEach { endPoint ->
            val (isValid, _, error )= pathIsLowerCase(endPoint)

            if(!isValid) {
                ResultStoreImpl.add(request = request, error = error, type = endPoint)
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