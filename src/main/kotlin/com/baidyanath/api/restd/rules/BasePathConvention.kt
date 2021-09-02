package com.baidyanath.api.restd.rules

import com.baidyanath.api.restd.configs.PATH_SHOULD_START_WITH_API_AND_VERSION
import com.baidyanath.api.restd.domain.Request
import com.baidyanath.api.restd.utils.result.ResultStoreImpl

/**
 * All the path should start with the /api/v<version>
 */
object BasePathConvention: Rule<Request> {

    override fun check(request: Request) {
        request.keys.forEach {
            val (isValid, pathName, error) = checkBasePathConvention(it, request.version)

            if (!isValid) {
                ResultStoreImpl.add(
                    request = request,
                    errors = setOf(error),
                    type = "path",
                    value = pathName
                )
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