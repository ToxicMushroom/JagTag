package com.jagrosh.jagtag

/**
 * Represents a single JagTag method. Every method can have a simple computation
 * and a complex computation.
 *
 *
 * Simple Method:
 * {example}
 * Only uses the existing environment and no input
 *
 *
 * Complex Method:
 * {example:input}
 * Uses both the environment as well as the input
 *
 *
 * Method must support a simple method, a complex method, or both.
 *
 * @author John Grosh (jagrosh)
 */
class Method(
    val name: String,
    val simple: ParseFunction? = null,
    val complex: ParseBiFunction? = null,
    val splitter: Array<String>? = null
) {

    /**
     * Uses the simple method to parse from the Environment
     *
     * @param env the Environment
     * @return the parsed value
     * @throws ParseException if the function throws a ParseException
     */
    @Throws(ParseException::class)
    fun parseSimple(env: Environment): String? {
        if (simple == null) return null
        return simple.invoke(env)
    }

    /**
     * Uses the complex method to parse from the Environment and input
     *
     * @param env   the Environment
     * @param input the input, not yet split
     * @return the parsed value
     * @throws ParseException if the function throws a ParseException
     */
    @Throws(ParseException::class)
    fun parseComplex(env: Environment, input: String): String? {
        var input1 = input
        if (complex == null) return null
        val input2: Array<String>
        when {
            splitter == null -> input2 = arrayOf(input1)
            splitter.isEmpty() -> input2 = input1.split("\\|").toTypedArray()
            else -> {
                input2 = Array(splitter.size + 1) { "" }
                for (i in 0 until input2.size - 1) {
                    val index = input1.indexOf(splitter[i])
                    if (index == -1) return "<invalid $name statement>"
                    input2[i] = input1.substring(0, index)
                    input1 = input1.substring(index + splitter[i].length)
                }
                input2[input2.size - 1] = input1
            }
        }
        return complex.invoke(env, input2)
    }
}
