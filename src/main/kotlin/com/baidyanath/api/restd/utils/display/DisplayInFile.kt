package com.baidyanath.api.restd.utils.display

import com.beust.klaxon.JsonObject
import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.ObjectCodec
import shadow.org.apache.logging.log4j.core.jackson.Log4jJsonObjectMapper
import java.io.File
import java.io.InputStream

object DisplayInFile: Display<Map<String, Map<String, List<String>>>> {

    override fun run(result: Map<String, Map<String, List<String>>>) {
        val file = File("result.json")
        val json = JsonObject(result).toJsonString(prettyPrint = true)

        file.writeBytes(json.toByteArray())
    }
}