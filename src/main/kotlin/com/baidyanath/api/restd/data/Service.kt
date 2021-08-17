package com.baidyanath.api.restd.data

import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import shadow.org.apache.commons.io.FileUtils
import java.io.File
import java.net.URL

object Service {

    private var result: API? = null
    fun parseJson(path: String): Set<String> {
        val url = URL(path)
        val file = File("swagger-sample.json")

        FileUtils.copyURLToFile(url, file)
        val result = Klaxon().parse<API>(file)

        file.deleteOnExit()
        return result?.endPoints!!
    }
}

class API(val paths: Map<String, Any>) {
    @Json(ignored = true)
    val endPoints: Set<String> get() = paths.keys
}