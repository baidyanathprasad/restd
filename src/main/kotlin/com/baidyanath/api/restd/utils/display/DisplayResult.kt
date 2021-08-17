package com.baidyanath.api.restd.utils.display

import com.baidyanath.api.restd.configs.ANSI_RED
import com.baidyanath.api.restd.configs.ANSI_WHITE
import com.baidyanath.api.restd.utils.calculation.CalculateError

object DisplayResult : Display<Map<String, Map<String, List<Any>>>> {

    override fun run(result: Map<String, Map<String, List<Any>>>) {
        val totalErrors = CalculateError.run(result)

        println("\n\t\t--------------------------------------------------")
        println("\t\tTotal Error(s): $totalErrors")
        println("\t\t--------------------------------------------------\n")
        result.forEach {(checkType: String, errorsWithType) ->
            println("\t\t$checkType:- ")
            errorsWithType.forEach { (path, errors) ->
                print("\t\t\t$ANSI_WHITE- $path: ")
                println(ANSI_RED + errors)
            }
        }
    }
}