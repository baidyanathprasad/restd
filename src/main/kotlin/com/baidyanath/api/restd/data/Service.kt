package com.baidyanath.api.restd.data

import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import java.io.File

object Service {

    private var result: API? = null
    fun parseJson(path: String): Set<String> {
        val file = File(path)
        val result = Klaxon().parse<API>(file)

        return result?.endPoints!!
    }
}

class API(val paths: Map<String, Any>) {
    @Json(ignored = true)
    val endPoints: Set<String> get() = paths.keys
}