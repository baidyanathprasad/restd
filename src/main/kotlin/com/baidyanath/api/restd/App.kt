package com.baidyanath.api.restd

import com.baidyanath.api.restd.configs.ANSI_RED
import com.baidyanath.api.restd.configs.ANSI_WHITE
import com.baidyanath.api.restd.configs.PATH_SHOULD_START_WITH_API_AND_VERSION
import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import java.io.File

/**
 * 1. To build
 *
 */
fun main(args:Array<String>) {

    val result = mutableMapOf<String, MutableMap<String, MutableList<String>>>()

    val path = "src/main/resources/swagger-sample.json"
    val endPoints = parseJson(path)

    val version = 1
    // Check first rule in naming convention
    endPoints.forEach {
        val (isValid, pathName, error) = checkBasePathConvention(it, version)

        if (!isValid) {
            var ruleTypesMap = result["path"]
            if (ruleTypesMap == null) {
                ruleTypesMap = mutableMapOf()
                result["path"] = ruleTypesMap
            }

            var errors = ruleTypesMap[pathName]
            if (errors == null) {
                errors = mutableListOf(error)
            }
            ruleTypesMap[pathName] = errors

            result["path"] = ruleTypesMap
        }
    }
    displayResult(result)
}

// Rules check that starts with /api/v1/..
fun checkBasePathConvention(path: String, version: Int): Triple<Boolean, String, String> {

    val isValid = path.startsWith("/api/v$version/")

    if (isValid) {
        return Triple(true,  path, "")
    }
    return Triple(false, path, "$PATH_SHOULD_START_WITH_API_AND_VERSION$version/")
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

