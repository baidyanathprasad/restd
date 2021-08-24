package com.baidyanath.api.restd

import com.baidyanath.api.restd.data.Service
import com.baidyanath.api.restd.domain.BaseEntityRequest
import com.baidyanath.api.restd.domain.BasePathConventionRequest
import com.baidyanath.api.restd.rules.BaseEntity
import com.baidyanath.api.restd.rules.BasePathConvention
import com.baidyanath.api.restd.rules.LowerCase
import com.baidyanath.api.restd.utils.calculation.CalculateError
import com.baidyanath.api.restd.utils.display.DisplayInFile
import com.baidyanath.api.restd.utils.display.DisplayResult

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        throw RuntimeException("Path must be present to run the project")
    }

    val result = mutableMapOf<String, MutableMap<String, MutableList<Any>>>()
    // val path = "src/main/resources/swagger-sample.json"
    // val url = http://localhost:8000/swagger-sample.json

    val endPoints = Service.parseJson(args[0])
    val version = 1

    if(endPoints.isEmpty()) {
        println("No End Points Found")
        return
    } else {
        // Check first rule in naming convention
        val basePathConventionRequest = BasePathConventionRequest(result, endPoints, version)
        BasePathConvention.check(basePathConventionRequest)

        val baseEntityRequest = BaseEntityRequest(result, endPoints, version)
        BaseEntity.check(baseEntityRequest)

        LowerCase.check(baseEntityRequest)
    }

    // Display Result
    DisplayResult.run(result)

    val totalErrors = CalculateError.run(result)
    result["errors"] = mutableMapOf("count" to mutableListOf<Any>(totalErrors))
    DisplayInFile.run(result)
}


