package com.baidyanath.api.restd.utils.display

import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File

object DisplayInFile: Display<Map<String, Map<String, List<Any>>>> {

    override fun run(result: Map<String, Map<String, List<Any>>>) {
        val file = File("result.json")
        val json = ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(result)

        file.writeBytes(json.toByteArray())
    }
}