package com.jagrosh.jagtag.libraries

import com.jagrosh.jagtag.Environment
import com.jagrosh.jagtag.Method

class Arguments {
    companion object {
        fun getMethods(): Collection<Method> {

            // gets the full argument input
            val argsMethod = Method("args", { env: Environment ->
                env.getOrDefault<String?>("args", "") ?: ""
            })

            // gets the number of arguments when split by whitespace
            val argsLenMethod = Method("argslen", { env: Environment ->
                val args = env.getOrDefault<String?>("args", "") ?: ""
                if (args.isEmpty()) return@Method "0"
                var splitargs = env.getReified<Array<String>?>("splitargs")
                if (splitargs == null) {
                    splitargs = args.split("\\s+".toRegex()).toTypedArray()
                    env["splitargs"] = splitargs
                }
                splitargs.size.toString()
            })

            // gets the argument at the given index, split by whitespace
            val argMethod = Method("arg", complex = { env: Environment, input: Array<String> ->
                var splitargs = env.getReified<Array<String>?>("splitargs")
                if (splitargs == null) {
                    val args = env.getOrDefault<String?>("args", "") ?: ""
                    splitargs = args.split("\\s+".toRegex()).toTypedArray()
                    env["splitargs"] = splitargs ?: emptyArray<Array<String>>()
                }
                val splitArgsHolder = splitargs ?: emptyArray()
                try {
                    return@Method splitArgsHolder[Integer.parseInt(input[0]) % splitArgsHolder.size]
                } catch (ex: NumberFormatException) {
                    return@Method ""
                }
            })
            return listOf(argsMethod, argsLenMethod, argMethod)
        }
    }
}