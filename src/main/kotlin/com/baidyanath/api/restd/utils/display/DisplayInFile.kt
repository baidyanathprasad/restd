package com.baidyanath.api.restd.utils.display

import com.beust.klaxon.JsonObject
import java.io.File

object DisplayInFile: Display<Map<String, Map<String, List<Any>>>> {

    override fun run(result: Map<String, Map<String, List<Any>>>) {
        val file = File("result.json")
        val json = JsonObject(result).toJsonString(prettyPrint = true)

        file.writeBytes(json.toByteArray())
    }
}