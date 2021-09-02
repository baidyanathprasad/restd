package com.baidyanath.api.restd

import com.baidyanath.api.restd.data.Service
import com.baidyanath.api.restd.domain.Request
import com.baidyanath.api.restd.rules.BaseEntity
import com.baidyanath.api.restd.rules.BasePathConvention
import com.baidyanath.api.restd.rules.LowerCase
import com.baidyanath.api.restd.utils.calculation.CalculateError
import com.baidyanath.api.restd.utils.display.DisplayInFile
import com.baidyanath.api.restd.utils.display.DisplayResult

/**
 * Input file can be either of the file or the url in the below format: -
 * val path = "src/main/resources/swagger-sample.json"
 * val url = http://localhost:8000/swagger-sample.json
 */
fun main(args: Array<String>) {
    if (args.isEmpty()) {
        throw RuntimeException("Path must be present to run the project")
    }

    val result = mutableMapOf<String, MutableMap<String, MutableList<Any>>>()

    val (endPoints, version) = Service.parseJson(args[0]) to 1 // version as 1
    if(endPoints.isEmpty()) {
        println("No End Points Found")
        return
    }

    val requestKeys = Service.getRequestBodiesKeys()

    println(requestKeys)

    applyRules(result = result, endPoints = endPoints, version = version)
    displayResult(result = result)

}

private fun applyRules(
    result: MutableMap<String, MutableMap<String, MutableList<Any>>>,
    endPoints: Set<String>,
    version: Int
) {
    Request(result, endPoints, version)
        .apply(BasePathConvention::check)
        .apply(BaseEntity::check)
        .apply(LowerCase::check)
}

private fun displayResult(result: MutableMap<String, MutableMap<String, MutableList<Any>>>) {
    DisplayResult.run(result)

    val totalErrors = CalculateError.run(result)
    result["errors"] = mutableMapOf("count" to mutableListOf<Any>(totalErrors))

    DisplayInFile.run(result)
}


