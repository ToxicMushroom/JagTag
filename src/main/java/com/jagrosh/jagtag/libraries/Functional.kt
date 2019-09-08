package com.jagrosh.jagtag.libraries

import com.jagrosh.jagtag.Environment
import com.jagrosh.jagtag.Method
import java.lang.Double.parseDouble
import java.math.BigInteger
import kotlin.math.*

class Functional {
    companion object {
        fun getMethods(): Collection<Method> {
            return listOf(
                    // equivalent to a comment; gets removed at runtime
                    Method("note", complex = { _: Environment, _: Array<String> -> "" }),

                    // chooses randomly between options
                    Method("choose", complex = { _: Environment, input: Array<String> ->
                        if (input.isEmpty()) {
                            ""
                        } else {
                            input[(Math.random() * input.size).toInt()]
                        }
                    }, splitter = emptyArray()),

                    // picks a random number in the provided range
                    Method("range", complex = { _: Environment, input: Array<String> ->
                        try {
                            var first = parseDouble(input[0].trim { it <= ' ' }).toLong()
                            var second = parseDouble(input[1].trim { it <= ' ' }).toLong()
                            if (second < first) {
                                val tmp = second
                                second = first
                                first = tmp
                            }
                            return@Method (first + (Math.random() * (second - first)).toLong()).toString()
                        } catch (ex: NumberFormatException) {
                            return@Method input[0] + "|" + input[1]
                        }
                    }, splitter = arrayOf("|")),

                    // performs a conditional
                    Method("if", complex = { _: Environment, input: Array<String> ->
                        val inp = input[0]
                        return@Method when {
                            input[0].equals("true", ignoreCase = true) -> input[1]
                            input[0].equals("false", ignoreCase = true) -> input[2]
                            evaluateStatement(inp) -> input[1]
                            else -> input[2]
                        }
                    }, splitter = arrayOf("|then:", "|else:")),

                    // performs basic mathematical function
                    Method("math", complex = { _: Environment, input: Array<String> ->
                        val inp = input[0]
                        evaluateMath(inp)
                    }),

                    // returns the absolute value of the provided number
                    Method("abs", complex = { _: Environment, input: Array<String> ->
                        try {
                            return@Method abs(Integer.parseInt(input[0])).toString()
                        } catch (ignored: NumberFormatException) {
                        }
                        try {
                            return@Method abs(parseDouble(input[0])).toString()
                        } catch (ignored: NumberFormatException) {
                        }
                        input[0]
                    }),

                    // rounds a number down
                    Method("floor", complex = { _: Environment, input: Array<String> ->
                        try {
                            return@Method floor(parseDouble(input[0])).toLong().toString()
                        } catch (e: NumberFormatException) {
                            return@Method input[0]
                        }
                    }),

                    // rounds a number up
                    Method("ceil", complex = { _: Environment, input: Array<String> ->
                        try {
                            return@Method ceil(parseDouble(input[0])).toLong().toString()
                        } catch (e: NumberFormatException) {
                            return@Method input[0]
                        }
                    }),

                    // rounds a number up if the decimal part is .5 or greater, down otherwise
                    Method("round", complex = { _: Environment, input: Array<String> ->
                        try {
                            return@Method parseDouble(input[0]).roundToLong().toString()
                        } catch (e: NumberFormatException) {
                            return@Method input[0]
                        }
                    }),

                    // takes the sine of radians
                    Method("sin", complex = { _: Environment, input: Array<String> ->
                        try {
                            return@Method sin(parseDouble(input[0])).toString()
                        } catch (e: NumberFormatException) {
                            return@Method input[0]
                        }
                    }),

                    // takes the cosine of radians
                    Method("cos", complex = { _: Environment, input: Array<String> ->
                        try {
                            return@Method cos(parseDouble(input[0])).toString()
                        } catch (e: NumberFormatException) {
                            return@Method input[0]
                        }
                    }),

                    // takes the tangent of radians
                    Method("tan", complex = { _: Environment, input: Array<String> ->
                        try {
                            return@Method tan(parseDouble(input[0])).toString()
                        } catch (e: NumberFormatException) {
                            return@Method input[0]
                        }
                    }),

                    // converts a number to a different base
                    Method("base", complex = { _: Environment, input: Array<String> ->
                        try {
                            return@Method BigInteger(input[0], Integer.parseInt(input[1])).toString(Integer.parseInt(input[2]))
                        } catch (e: Exception) {
                            return@Method input[0]
                        }
                    }, splitter = emptyArray())
            )
        }

        private fun evaluateMath(statement: String): String {
            var index = statement.lastIndexOf("|+|")
            if (index == -1) index = statement.lastIndexOf("|-|")
            if (index == -1) index = statement.lastIndexOf("|*|")
            if (index == -1) index = statement.lastIndexOf("|%|")
            if (index == -1) index = statement.lastIndexOf("|/|")
            if (index == -1) index = statement.lastIndexOf("|^|")
            if (index == -1) return statement
            val first = evaluateMath(statement.substring(0, index)).trim { it <= ' ' }
            val second = evaluateMath(statement.substring(index + 3)).trim { it <= ' ' }
            val val1: Double
            val val2: Double
            try {
                val1 = parseDouble(first)
                val2 = parseDouble(second)
                when (statement.substring(index, index + 3)) {
                    "|+|" -> return "" + (val1 + val2)
                    "|-|" -> return "" + (val1 - val2)
                    "|*|" -> return "" + val1 * val2
                    "|%|" -> return "" + val1 % val2
                    "|/|" -> return "" + val1 / val2
                    "|^|" -> return "" + val1.pow(val2)
                }
            } catch (e: NumberFormatException) {
            }
            when (statement.substring(index, index + 3)) {
                "|+|" -> return first + second
                "|-|" -> {
                    val loc = first.indexOf(second)
                    return if (loc != -1) {
                        first.substring(0, loc) + (if (loc + second.length < first.length) {
                            first.substring(loc + second.length)
                        } else {
                            ""
                        })
                    } else {
                        "$first-$second"
                    }
                }
                "|*|" -> return "$first*$second"
                "|%|" -> return "$first%$second"
                "|/|" -> return "$first/$second"
                "|^|" -> return "$first^$second"
            }
            return statement
        }

        private fun evaluateStatement(statement: String): Boolean {
            var index = statement.indexOf("|=|")
            if (index == -1) index = statement.indexOf("|<|")
            if (index == -1) index = statement.indexOf("|>|")
            if (index == -1) index = statement.indexOf("|~|")
            if (index == -1) index = statement.indexOf("|?|")
            if (index == -1) return false
            val s1 = statement.substring(0, index).trim { it <= ' ' }
            val s2 = statement.substring(index + 3).trim { it <= ' ' }
            try {
                val i1 = parseDouble(s1)
                val i2 = parseDouble(s2)
                when (statement.substring(index, index + 3)) {
                    "|=|" -> return i1 == i2
                    "|~|" -> return (i1 * 100).toInt() == (i2 * 100).toInt()
                    "|>|" -> return i1 > i2
                    "|<|" -> return i1 < i2
                }
            } catch (e: NumberFormatException) {
            }
            when (statement.substring(index, index + 3)) {
                "|=|" -> return s1 == s2
                "|~|" -> return s1.equals(s2, ignoreCase = true)
                "|>|" -> return s1 > s2
                "|<|" -> return s1 < s2
                "|?|" -> return try {
                    s1.matches(s2.toRegex())
                } catch (e: Exception) {
                    false
                }
            }
            return false
        }
    }
}