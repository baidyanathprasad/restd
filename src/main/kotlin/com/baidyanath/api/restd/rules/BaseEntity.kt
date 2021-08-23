package com.baidyanath.api.restd.rules

import com.baidyanath.api.restd.configs.ENTITY_NAME_NOT_FOUND
import com.baidyanath.api.restd.configs.ENTITY_NAME_SHOULD_BE_PLURAL
import com.baidyanath.api.restd.domain.BaseEntityRequest
import com.baidyanath.api.restd.domain.ErrorResponse
import com.baidyanath.api.restd.domain.ErrorType

/**
 * Basic Entity check for in the end points.
 */
object BaseEntity: Rule<BaseEntityRequest> {

    override fun check(request: BaseEntityRequest) {
        request.endPoints.forEach {
            val (isValid, pathName, currentErrors) = checkEntityNamePresent(it, request.version)

            if (!isValid) {
                var ruleTypesMap = request.result["path"]
                if (ruleTypesMap == null) {
                    ruleTypesMap = mutableMapOf()
                    request.result["path"] = ruleTypesMap
                }
                var errors = ruleTypesMap[pathName]

                val errorsResponse = currentErrors.map { error ->
                    ErrorResponse(
                        description = error,
                        type = ErrorType.HIGH
                    )
                }

                if (errors == null) {
                    errors = mutableListOf(errorsResponse)
                } else {
                   errors.addAll(errorsResponse)
                }
                ruleTypesMap[pathName] = errors

                request.result["path"] = ruleTypesMap
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