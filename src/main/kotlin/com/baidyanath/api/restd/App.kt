package com.baidyanath.api.restd

import com.baidyanath.api.restd.configs.ANSI_RED
import com.baidyanath.api.restd.configs.ANSI_WHITE
import com.baidyanath.api.restd.configs.PATH_SHOULD_START_WITH_API_AND_VERSION
import com.baidyanath.api.restd.domain.BasePathConventionRequest
import com.baidyanath.api.restd.rules.BasePathConvention
import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import java.io.File

/**
 * 1. To build
 *
 */
fun main(args:Array<String>) {
    if (args.isEmpty()) {
        throw RuntimeException("Path must be present to run the project.")
    }

    val result = mutableMapOf<String, MutableMap<String, MutableList<String>>>()
    // val path = "src/main/resources/swagger-sample.json"
    val endPoints = parseJson(args[0])
    val version = 1

    // Check first rule in naming convention
    val basePathConventionRequest = BasePathConventionRequest(result, endPoints, version)
    BasePathConvention.check(basePathConventionRequest)

    displayResult(result)
}

fun findTotalErrors(result: Map<String, Map<String, List<String>>>): Int {
    var count = 0
    result.forEach {(_, errorsWithType) ->
        errorsWithType.forEach { (_, errors) ->
           count += errors.size
        }
    }
    return count
}

fun displayResult(result: Map<String, Map<String, List<String>>>) {
    println("\n\t\t--------------------------------------------------")
    println("\t\tTotal Error(s): ${findTotalErrors(result)}")
    println("\t\t--------------------------------------------------\n")
    result.forEach {(checkType: String, errorsWithType) ->
        println("\t\t$checkType:- ")
        errorsWithType.forEach { (path, errors) ->
            print("\t\t\t$ANSI_WHITE- $path: ")
            println(ANSI_RED + errors)
        }
    }
}

fun parseJson(path: String): Set<String> {
    val file = File(path)
    val result = Klaxon().parse<API>(file)

    return result?.endPoints!!
}

class API(val paths: Map<String, Any>) {
    @Json(ignored = true)
    val endPoints: Set<String> get() = paths.keys
}

