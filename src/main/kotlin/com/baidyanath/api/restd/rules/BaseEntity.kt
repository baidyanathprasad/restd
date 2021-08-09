package com.baidyanath.api.restd.rules

import com.baidyanath.api.restd.configs.ENTITY_NAME_NOT_FOUND
import com.baidyanath.api.restd.domain.BaseEntityRequest

object BaseEntity: Rule<BaseEntityRequest> {

    override fun check(request: BaseEntityRequest) {
        request.endPoints.forEach {
            val (isValid, pathName, error) = checkEntityNamePresent(it, request.version)

            if (!isValid) {
                var ruleTypesMap = request.result["path"]
                if (ruleTypesMap == null) {
                    ruleTypesMap = mutableMapOf()
                    request.result["path"] = ruleTypesMap
                }
                var errors = ruleTypesMap[pathName]
                if (errors == null) {
                    errors = mutableListOf(error)
                } else {
                   errors.add(error)
                }
                ruleTypesMap[pathName] = errors

                request.result["path"] = ruleTypesMap
            }
        }
    }

    // Rules check that starts with /api/v1/<entities>
    private fun checkEntityNamePresent(path: String, version: Int): Triple<Boolean, String, String> {
        val basePath = "/api/v$version/"
        path.replace(basePath, "")

        val entityName = path.split("/")[0]
        if (entityName.isNotEmpty()) {
            return Triple(true,  path, "")
        }
        return Triple(false, path, ENTITY_NAME_NOT_FOUND)
    }
}