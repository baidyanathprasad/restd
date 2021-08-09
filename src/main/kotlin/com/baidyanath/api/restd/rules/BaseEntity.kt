package com.baidyanath.api.restd.rules

import com.baidyanath.api.restd.configs.PATH_SHOULD_START_WITH_API_AND_VERSION
import com.baidyanath.api.restd.domain.BaseEntityRequest

object BaseEntity: Rule<BaseEntityRequest> {

    override fun check(request: BaseEntityRequest) {
        TODO("Not yet implemented")
    }

    // Rules check that starts with /api/v1/<entities>
    private fun checkBasePathConvention(path: String, version: Int): Triple<Boolean, String, String> {
        val isValid = path.startsWith("/api/v$version/")

        if (isValid) {
            return Triple(true,  path, "")
        }
        return Triple(false, path, "$PATH_SHOULD_START_WITH_API_AND_VERSION$version/")
    }
}