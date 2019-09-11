package com.jagrosh.jagtag

/**
 * An object for parsing JagTag code
 *
 * @author John Grosh (jagrosh)
 */
class Parser
/**
 * Constructor for a JagTag parser. It is not recommended that you call
 * this; instead consider using JagTagParserBuilder#build() after adding
 * all the applicable methods
 *
 * @param methods    - a collection of methods for the parser
 * @param iterations - the maximum number of iterations the parser will run
 * @param maxLength  - the maximum internal length of the parse string
 * @param maxOutput  - the maximum output of the parsed result
 */(methods: Collection<Method>, private var iterations: Long, private var maxLength: Int, private var maxOutput: Int) {
    private var methods = HashMap<String, Method>()
    private var environment = Environment()

    init {
        methods.forEach { method: Method -> this.methods[method.name] = method }
    }

    /**
     * Inserts objects to be used while parsing
     *
     * @param key   - the name of the object
     * @param value - the value of the object
     * @return the parser after the object has been added
     */
    @Synchronized
    fun put(key: String, value: Any): Parser {
        environment[key] = value
        return this
    }

    /**
     * Clears all the objects from the parser
     *
     * @return the parser after the objects have been cleared
     */
    @Synchronized
    fun clear(): Parser {
        environment.clear()
        return this
    }

    /**
     * Parses a String of JagTag code, utilizing the object that have been added
     *
     * @param input
     * @return the parsed String
     */
    @Synchronized
    suspend fun parse(input: String): String {
        var output = filterEscapes(input)
        var count = 0
        var lastoutput = ""
        while (lastoutput != output && count < iterations && output.length <= maxLength) {
            lastoutput = output
            val i1 = output.indexOf("}")
            val i2 = if (i1 == -1) -1 else output.lastIndexOf("{", i1)
            if (i1 != -1 && i2 != -1)//otherwise, we're done
            {
                val contents = output.substring(i2 + 1, i1)
                var result: String? = null
                val split = contents.indexOf(":")
                if (split == -1) {
                    val method = methods[contents.trim { it <= ' ' }]
                    if (method != null) try {
                        result = method.parseSimple(environment)
                    } catch (ex: ParseException) {
                        return ex.message ?: "no error message"
                    }
                } else {
                    val name = contents.substring(0, split)
                    val params = contents.substring(split + 1)
                    val method = methods[name.trim { it <= ' ' }]
                    if (method != null) try {
                        result = method.parseComplex(environment, defilterAll(params))
                    } catch (ex: ParseException) {
                        return ex.message ?: "no error message"
                    }
                }
                if (result == null) result = "{$contents}"
                output = output.substring(0, i2) + filterAll(result) + output.substring(i1 + 1)
            }
            count++
        }
        output = defilterAll(output)
        if (output.length > maxOutput) output = output.substring(0, maxOutput)
        return output
    }

    private fun filterEscapes(input: String): String {
        return input.replace("\\{", "\u0012").replace("\\|", "\u0013").replace("\\}", "\u0014")
    }

    private fun defilterEscapes(input: String): String {
        return input.replace("\u0012", "\\{").replace("\u0013", "\\|").replace("\u0014", "\\}")
    }

    private fun defilterAll(input: String): String {
        return defilterEscapes(input).replace("\u0015", "{").replace("\u0016", "}")
    }

    private fun filterAll(input: String): String {
        return filterEscapes(input).replace("{", "\u0015").replace("}", "\u0016")
    }
}