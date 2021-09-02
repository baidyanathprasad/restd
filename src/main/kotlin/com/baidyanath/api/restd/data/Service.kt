package com.baidyanath.api.restd.data

import com.fasterxml.jackson.databind.ObjectMapper
import shadow.org.apache.commons.io.FileUtils
import java.io.File
import java.net.URL

object Service {

    private val paths = linkedMapOf<String, Any>()
    private var treeMap: Map<String, Any>? = null
    private val keys = mutableListOf<String>()
    private val requestBody = linkedMapOf<String, Any>()

    fun parseJson(path: String): Set<String> {
        return try {
            val url = URL(path)
            val file = File("swagger-sample.json")

            FileUtils.copyURLToFile(url, file)

            if (treeMap == null) {
                treeMap = ObjectMapper().readValue(file.readText(), Map::class.java) as MutableMap<String, Any>
            }

            // Call this method to get all the defaults
            getAllKeys(treeMap!!, keys)

            // Delete if the file still exists
            file.deleteOnExit()

            return paths.keys
        } catch (e: Exception) {
            println("Error: There is some issue in JSON input: ${e.localizedMessage}")
            emptySet()
        }
    }

    fun getPaths(): Set<String> {
        return paths.keys
    }

    fun getRequestBodiesKeys(): List<String> {
        val keys = mutableListOf<String>()

        return getKeyObject(requestBody, keys).toSet()
            .filter {
                // Remove extra numbers which was added at the time of parsing.
                try {
                    it.toInt()
                    false
                } catch (ex: NumberFormatException) {
                    true
                }
            }
    }

    private fun getAllKeys(treeMap: Map<String, Any>, keys: MutableList<String>): MutableList<String> {

        treeMap.forEach { (key, value) ->
            if (value is LinkedHashMap<*, *>) {
                val map: LinkedHashMap<String, Any> = value as LinkedHashMap<String, Any>

                // key all the keys for the request body
                if (key.equals("schema", true) || key.equals("schemas", true)) {
                    val reqBodySize = requestBody.size
                    requestBody.putAll(mutableMapOf("${reqBodySize + 1}" to map))
                }

                if (key == "paths") {
                    paths.putAll(value)
                }
                getAllKeys(map, keys)
            }
            keys.add(key)
        }
        return keys
    }

    private fun getKeyObject(json: Map<String, Any>, keys: MutableList<String>) : List<String> {
        json.forEach { (key, value) ->
            if (value is LinkedHashMap<*, *>) {
                val map: LinkedHashMap<String, Any> = value as LinkedHashMap<String, Any>
                getKeyObject(map, keys)
            }
            keys.add(key)
        }
        return keys
    }

}