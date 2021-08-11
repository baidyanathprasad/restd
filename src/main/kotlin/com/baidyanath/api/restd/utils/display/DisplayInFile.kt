package com.baidyanath.api.restd.utils.display

import com.baidyanath.api.restd.domain.ErrorResponse
import com.beust.klaxon.JsonObject
import java.io.File

object DisplayInFile: Display<Map<String, Map<String, List<ErrorResponse>>>> {

    override fun run(result: Map<String, Map<String, List<ErrorResponse>>>) {
        val file = File("result.json")
        val json = JsonObject(result).toJsonString(prettyPrint = true)

        file.writeBytes(json.toByteArray())
    }
}