package com.baidyanath.api.restd

import com.baidyanath.api.restd.data.Service
import com.baidyanath.api.restd.domain.BaseEntityRequest
import com.baidyanath.api.restd.domain.BasePathConventionRequest
import com.baidyanath.api.restd.rules.BaseEntity
import com.baidyanath.api.restd.rules.BasePathConvention
import com.baidyanath.api.restd.utils.display.DisplayResult

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        throw RuntimeException("Path must be present to run the project")
    }

    val result = mutableMapOf<String, MutableMap<String, MutableList<String>>>()
    // val path = "src/main/resources/swagger-sample.json"
    println(args[0])

    val endPoints = Service.parseJson(args[0])
    val version = 1

    // Check first rule in naming convention
    val basePathConventionRequest = BasePathConventionRequest(result, endPoints, version)
    BasePathConvention.check(basePathConventionRequest)

    val baseEntityRequest = BaseEntityRequest(result, endPoints, version)
    BaseEntity.check(baseEntityRequest)

    // Display Result
    DisplayResult.run(result)
}

