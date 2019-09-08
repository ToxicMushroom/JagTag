package com.jagrosh.jagtag.libraries

import com.jagrosh.jagtag.Environment
import com.jagrosh.jagtag.Method
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object Strings {
    fun getMethods(): Collection<Method> {
        return listOf(

                // makes all letters lowercase
                Method("lower", complex = { _: Environment, input: Array<String> ->
                    input[0].toLowerCase()
                }),


                // makes all letters uppercase
                Method("upper", complex = { _: Environment, input: Array<String> ->
                    input[0].toUpperCase()
                }),


                // returns the length of the provided string
                Method("length", complex = { _: Environment, input: Array<String> ->
                    input[0].length.toString()
                }),


                // encodes the text to UTF-8 (url standard)
                Method("url", complex = { _: Environment, input: Array<String> ->
                    URLEncoder.encode(input[0], StandardCharsets.UTF_8)
                }),


                // replaces some text with other text
                Method("replace", complex = { _: Environment, input: Array<String> ->
                    input[2].replace(input[0], input[1])
                }, splitter = arrayOf("|with:", "|in:")),

                // replaces some text with other text based on a regular expression
                // supports capture groups
                Method("replaceregex", complex = { _: Environment, input: Array<String> ->
                    try {
                        return@Method input[2].replace(input[0].toRegex(), input[1])
                    } catch (ex: Exception) {
                        return@Method input[2]
                    }
                }, splitter = arrayOf("|with:", "|in:")),


                // takes the substring of the provided string
                // {substring:start:end|string}
                // if start or end are not provided (blank), it goes to the
                // beginning or end of the string, respectively
                Method("substring", complex = { _: Environment, input: Array<String> ->
                    val str = input[2]
                    var start: Int
                    var end: Int
                    start = try {
                        Integer.parseInt(input[0])
                    } catch (e: NumberFormatException) {
                        0
                    }
                    end = try {
                        Integer.parseInt(input[1])
                    } catch (e: NumberFormatException) {
                        str.length
                    }
                    if (start < 0) start += str.length
                    if (end < 0) end += str.length
                    if (end <= start || end <= 0 || start >= str.length) return@Method ""
                    if (end > str.length) end = str.length
                    if (start < 0) start = 0
                    input[2].substring(start, end)
                }, splitter = arrayOf("|", "|")),

                // removes all extraneous newlines and spacing
                // this enables a user to "pretty-print" their text
                // and it would still result in a clean output string
                Method("oneline", complex = { _: Environment, input: Array<String> ->
                    input[0].replace("\\s+".toRegex(), " ").trim { it <= ' ' }
                }),

                // returns a hash of the given input
                Method("hash", complex = { _: Environment, input: Array<String> ->
                    input[0].hashCode().toString()
                })

        )
    }
}