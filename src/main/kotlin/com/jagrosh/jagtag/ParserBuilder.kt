package com.jagrosh.jagtag

import java.util.*

/**
 * A Builder for a Parser for easier method-adding
 *
 * @author John Grosh (jagrosh)
 */
class ParserBuilder {

    private val methods: MutableList<Method>
    private var iterations: Long = 200
    private var maxLength = 4000
    private var maxOutput = 1995

    /**
     * Add a single Method
     *
     * @param method - the method to add
     * @return the builder after the method has been added
     */
    fun addMethod(method: Method): ParserBuilder {
        methods.add(method)
        return this
    }

    /**
     * Add a Collection of Methods
     *
     * @param methods - the collection to add
     * @return the builder after the methods have been added
     */
    fun addMethods(methods: Collection<Method>): ParserBuilder {
        this.methods.addAll(methods)
        return this
    }

    /**
     * Sets the maximum iterations for the parser
     *
     * @param iterations - the max iterations
     * @return the builder after the max iterations have been set
     */
    fun setMaxIterations(iterations: Long): ParserBuilder {
        this.iterations = iterations
        return this
    }

    /**
     * Sets the maximum internal length of the parser
     *
     * @param length - the max internal length
     * @return the builder after this max has been set
     */
    fun setMaxLength(length: Int): ParserBuilder {
        maxLength = length
        return this
    }

    /**
     * Sets the maximum output length for the parser
     *
     * @param length - the max output length
     * @return the builder after the max has been set
     */
    fun setMaxOutput(length: Int): ParserBuilder {
        maxOutput = length
        return this
    }

    /**
     * Builds a new Parser based on the current builder
     *
     * @return a new Parser
     */
    fun build(): Parser {
        return Parser(methods, iterations, maxLength, maxOutput)
    }

    /**
     * Construct the default builder with no methods
     */

    init {
        methods = LinkedList()
    }
}
