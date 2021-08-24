package com.baidyanath.api.restd.rules

import com.baidyanath.api.restd.configs.ENTITY_NAME_NOT_FOUND
import com.baidyanath.api.restd.configs.ENTITY_NAME_SHOULD_BE_PLURAL
import com.baidyanath.api.restd.domain.Request
import com.baidyanath.api.restd.utils.result.ResultStoreImpl

/**
 * Basic Entity check for in the end points.
 */
object BaseEntity: Rule<Request> {

    override fun check(request: Request) {
        request.endPoints.forEach {
            val (isValid, pathName, currentErrors) = checkEntityNamePresent(it, request.version)

            if (!isValid) {
                ResultStoreImpl.add(
                    request = request,
                    errors = currentErrors.toSet(),
                    type = "path",
                    value = pathName
                )
            }
        }
    }

    /**
     * Rules check that starts with /api/v1/<entities>
      */
    private fun checkEntityNamePresent(path: String, version: Int): Triple<Boolean, String, MutableList<String>> {
        val basePath = "/api/v$version/"
        path.replace(basePath, "")
        val entityName = path.split("/")[1].trim()

        val isPlural = checkPlurality(entityName = entityName)
        val errors = mutableListOf<String>()

        if (!isPlural) {
            errors.add(ENTITY_NAME_SHOULD_BE_PLURAL)
        }

        if (entityName.isNotEmpty()) {
            return Triple(true,  path, errors)
        }
        errors.add(ENTITY_NAME_NOT_FOUND)

        return Triple(false, path, errors)
    }

    private fun checkPlurality(entityName: String): Boolean {
        if (entityName.isEmpty()) {
            return false
        }

        return !(entityName.endsWith("s") || entityName.endsWith("es"))
    }
}