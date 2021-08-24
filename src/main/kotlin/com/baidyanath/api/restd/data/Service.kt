package com.baidyanath.api.restd.data

import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import shadow.org.apache.commons.io.FileUtils
import java.io.File
import java.net.URL

object Service {

    private var result: API? = null
    fun parseJson(path: String): Set<String> {
        return try {
            val url = URL(path)
            val file = File("swagger-sample.json")

            FileUtils.copyURLToFile(url, file)
            result = Klaxon().parse<API>(file)

            file.deleteOnExit()
            result?.endPoints!!
        } catch (e: Exception) {
            println("Error: There is some issue in JSON input: ${e.localizedMessage}")
            emptySet()
        }
    }
}

class API(val paths: Map<String, Any>) {
    @Json(ignored = true)
    val endPoints: Set<String> get() = paths.keys
}