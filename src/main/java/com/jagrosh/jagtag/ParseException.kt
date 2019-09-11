package com.jagrosh.jagtag

/**
 * A JagTag Parsing Exception. Functions identically to a regular exception; the
 * typing is to increase clarity
 *
 * @author John Grosh (jagrosh)
 */
class ParseException : Throwable {
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(message: String?) : super(message)
}
